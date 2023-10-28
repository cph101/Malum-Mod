package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.rites.BlockAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchAqueousRiteType extends MalumRiteType {
    public EldritchAqueousRiteType() {
        super("greater_aqueous_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {

            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getNearbyBlocks(totemBase, PointedDripstoneBlock.class, getRiteEffectRadius() * 4).forEach(p -> {
                    if (level.random.nextFloat() < 0.1f) {
                        for (int i = 0; i < 4 + level.random.nextInt(2); i++) {
                            level.getBlockState(p).randomTick((ServerLevel) level, p, level.random);
                        }
                        ParticleEffectTypeRegistry.DRIPPING_SMOKE.createPositionedEffect(level, new PositionEffectData(p), new ColorEffectData(AQUEOUS_SPIRIT.getPrimaryColor()));
                    }
                });
            }

            @Override
            public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Set<Block> filters, BlockState state, BlockPos pos) {
                return super.canAffectBlock(totemBase, filters, state, pos) && PointedDripstoneBlock.isStalactiteStartPos(state, totemBase.getLevel(), pos);
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, Zombie.class).filter(z -> !(z instanceof Drowned)).forEach(e -> {
                    if (!e.isUnderWaterConverting()) {
                        e.startUnderWaterConversion(100);

                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(AQUEOUS_SPIRIT.getPrimaryColor()));
                    }
                });
            }
        };
    }
}
