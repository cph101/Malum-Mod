package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.packets.particle.TotemParticlePacket;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.registry.content.SpiritRiteRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseTileEntity extends SimpleBlockEntity {

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;
    public boolean corrupted;

    public TotemBaseTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TOTEM_BASE_BLOCK_ENTITY.get(), pos, state);
        this.corrupted = ((TotemBaseBlock)state.getBlock()).corrupted;
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (active && rite == null)
        {
            return InteractionResult.FAIL;
        }
        if (active) {
            if (!level.isClientSide) {
                endRite();
            }
        } else {
            if (!level.isClientSide) {
                startRite();
            }
        }
        player.swing(InteractionHand.MAIN_HAND, true);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (rite != null) {
            compound.putString("rite", rite.identifier);
        }
        compound.putInt("spiritCount", spirits.size());
        for (int i = 0; i < spirits.size(); i++) {
            MalumSpiritType type = spirits.get(i);
            compound.putString("spirit_" + i, type.identifier);
        }
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("height", height);
        compound.putBoolean("corrupted", corrupted);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        rite = SpiritRiteRegistry.getRite(compound.getString("rite"));
        int size = compound.getInt("spiritCount");
        for (int i = 0; i < size; i++) {
            spirits.add(SpiritHelper.getSpiritType(compound.getString("spirit_" + i)));
        }
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        corrupted = compound.getBoolean("corrupted");
        super.load(compound);
    }

    @Override
    public void tick() {
        if (rite != null) {
            progress++;
            if (progress >= rite.interval(corrupted)) {
                rite.executeRite(level, worldPosition, corrupted);
                progress = 0;
                if (!level.isClientSide) {
                    BlockHelper.updateState(level, worldPosition);
                }
            }
        } else if (active) {
            if (!level.isClientSide) {
                progress--;
                if (progress <= 0) {
                    height++;
                    BlockPos polePos = worldPosition.above(height);
                    if (level.getBlockEntity(polePos) instanceof TotemPoleTileEntity) {
                        addPole(polePos);
                    } else {
                        MalumRiteType rite = SpiritRiteRegistry.getRite(spirits);
                        if (rite == null) {
                            endRite();
                        } else {
                            completeRite(rite);
                            setChanged();
                        }
                    }
                    progress = 20;
                    BlockHelper.updateState(level, worldPosition);
                }
            }
        }
    }

    public void disableOtherRites(MalumRiteType rite) {
        int range = rite.range(corrupted);
        ArrayList<BlockPos> totemBases = new ArrayList<>(BlockHelper.getBlocks(worldPosition, range, b -> level.getBlockEntity(b) instanceof TotemBaseTileEntity && !b.equals(worldPosition)));
        for (BlockPos basePos : totemBases) {
            TotemBaseTileEntity blockEntity = (TotemBaseTileEntity) level.getBlockEntity(basePos);
            if (rite.equals(blockEntity.rite)) {
                blockEntity.endRite();
            } else if (blockEntity.rite != null) {
                if (basePos.closerThan(worldPosition, 0.5f + range * 0.5f)) {
                    blockEntity.endRite();
                }
            }
        }
    }

    public void addPole(BlockPos polePos) {
        Direction poleFacing = level.getBlockState(polePos).getValue(HORIZONTAL_FACING);
        ArrayList<TotemPoleTileEntity> poles = getPoles();
        if (poles.isEmpty() || poles.stream().allMatch(p -> p.getBlockState().getValue(HORIZONTAL_FACING).equals(poleFacing) && p.corrupted == corrupted)) {
            TotemPoleTileEntity blockEntity = (TotemPoleTileEntity) level.getBlockEntity(polePos);
            if (blockEntity.type != null) {
                spirits.add(blockEntity.type);
                blockEntity.riteStarting(height);
            }
        }
    }

    public ArrayList<TotemPoleTileEntity> getPoles() {
        ArrayList<TotemPoleTileEntity> poles = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            if (level.getBlockEntity(worldPosition.above(i)) instanceof TotemPoleTileEntity) {
                poles.add((TotemPoleTileEntity) level.getBlockEntity(worldPosition.above(i)));
            }
        }
        return poles;
    }

    public void startRite() {
        active = true;
        progress = 0;
        BlockHelper.updateState(level, worldPosition);
    }

    public void completeRite(MalumRiteType rite) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGED, SoundSource.BLOCKS, 1, 1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY()+1, worldPosition.getZ()));
        getPoles().forEach(p -> p.riteComplete(height));
        progress = 0;
        if (rite.isInstant) {
            rite.riteEffect(level, worldPosition);
            resetTotem();
            return;
        }
        this.rite = rite;
        disableOtherRites(rite);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void endRite() {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE, SoundSource.BLOCKS, 1, 0.5f);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TotemParticlePacket(spirits.stream().map(s -> s.color).collect(Collectors.toCollection(ArrayList::new)), worldPosition.getX(), worldPosition.getY()+1, worldPosition.getZ()));
        resetTotem();
    }

    public void resetTotem() {
        getPoles().forEach(TotemPoleTileEntity::riteEnding);
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
        BlockHelper.updateState(level, worldPosition);
    }
}