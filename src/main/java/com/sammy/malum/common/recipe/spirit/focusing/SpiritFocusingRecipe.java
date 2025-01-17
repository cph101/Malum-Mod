package com.sammy.malum.common.recipe.spirit.focusing;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.*;

import java.util.*;

public class SpiritFocusingRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {

    public static final String NAME = "spirit_focusing";

    public final int time;
    public final int durabilityCost;

    public final Ingredient ingredient;
    public final ItemStack output;
    public final List<SpiritIngredient> spirits;

    public SpiritFocusingRecipe(int time, int durabilityCost, Ingredient ingredient, ItemStack output, List<SpiritIngredient> spirits) {
        super(RecipeSerializerRegistry.FOCUSING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_FOCUSING.get());
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.ingredient = ingredient;
        this.output = output;
        this.spirits = spirits;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(new SizedIngredient(ingredient, 1), spirits);
    }

    public static class Serializer implements RecipeSerializer<SpiritFocusingRecipe> {

        public static final MapCodec<SpiritFocusingRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
                Codec.INT.fieldOf("time").forGetter((recipe) -> recipe.time),
                Codec.INT.fieldOf("durabilityCost").forGetter((recipe) -> recipe.durabilityCost),
                Ingredient.CODEC.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                ItemStack.CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter((recipe) -> recipe.spirits)
        ).apply(obj, SpiritFocusingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SpiritFocusingRecipe> STREAM_CODEC =
                ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

        @Override
        public MapCodec<SpiritFocusingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpiritFocusingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
