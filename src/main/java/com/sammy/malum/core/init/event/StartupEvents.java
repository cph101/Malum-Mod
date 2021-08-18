package com.sammy.malum.core.init.event;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.mod_content.MalumArcaneAssemblyRecipes;
import com.sammy.malum.core.mod_content.MalumRites;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes;
import com.sammy.malum.core.mod_content.SpiritInfusionRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerModContents(FMLCommonSetupEvent event)
    {
        MalumSpiritAltarRecipes.init();
        MalumArcaneAssemblyRecipes.init();
        MalumRites.init();
    }
    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event)
    {
        event.enqueueWork(MalumFeatures::new);
    }

    @SubscribeEvent
    public static void registerCurios(InterModEnqueueEvent event)
    {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("necklace").size(1).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("belt").size(1).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("charm").size(1).cosmetic().build());
    }
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        Registry.register(Registry.RECIPE_TYPE, SpiritInfusionRecipe.NAME, SpiritInfusionRecipe.RECIPE_TYPE);
        event.getRegistry().register(SpiritInfusionRecipe.SERIALIZER.setRegistryName(SpiritInfusionRecipe.NAME));
    }
}