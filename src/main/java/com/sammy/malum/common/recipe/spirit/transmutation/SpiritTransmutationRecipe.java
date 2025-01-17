package com.sammy.malum.common.recipe.spirit.transmutation;

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

public class SpiritTransmutationRecipe extends LodestoneInWorldRecipe<SingleRecipeInput> {
    public static final String NAME = "spirit_transmutation";

    public final Ingredient ingredient;

    public final ItemStack output;

    public SpiritTransmutationRecipe(Ingredient ingredient, ItemStack output) {
        super(RecipeSerializerRegistry.SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get(), output);
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }

    public static class Serializer implements RecipeSerializer<SpiritTransmutationRecipe> {

        public static final MapCodec<SpiritTransmutationRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                ItemStack.CODEC.fieldOf("output").forGetter((recipe) -> recipe.output)
        ).apply(obj, SpiritTransmutationRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SpiritTransmutationRecipe> STREAM_CODEC =
                ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

        @Override
        public MapCodec<SpiritTransmutationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpiritTransmutationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    @SuppressWarnings("unchecked")
    public static SpiritTransmutationRecipe getRecipe(Level level, ItemStack stack) {
        return level.getRecipeManager().getRecipeFor(RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get(), new SingleRecipeInput(stack), level).get().value();
    }
}
