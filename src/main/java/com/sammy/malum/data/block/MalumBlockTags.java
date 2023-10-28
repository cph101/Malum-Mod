package com.sammy.malum.data.block;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockTagsProvider;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;

public class MalumBlockTags extends LodestoneBlockTagsProvider {


    public MalumBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MalumMod.MALUM, existingFileHelper);
    }

    @SuppressWarnings("unchecked")

    protected void addTags() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        tag(BlockTagRegistry.RITE_IMMUNE).addTags(BlockTagRegistry.TAINTED_ROCK, BlockTagRegistry.TWISTED_ROCK);

        tag(BlockTagRegistry.ENDLESS_FLAME);
        tag(BlockTagRegistry.GREATER_AERIAL_WHITELIST);

        addTagsFromBlockProperties(blocks.stream().map(RegistryObject::get).collect(Collectors.toList()));
    }

    @Override
    public String getName() {
        return "Malum Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        addTags();
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}
