package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.CarryOverNBTResult;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.ISpiritInfusionResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.IntNBT;

import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.*;
import static com.kittykitcatcat.malum.init.ModItems.*;
import static net.minecraft.item.Items.*;
import static net.minecraft.item.Items.AIR;

public class SpiritInfusionRecipe
{
    Item catalyst;
    List<Item> items = new ArrayList<>();
    int infusionTime;
    SpiritData data;

    ItemStack outputStack;
    ISpiritInfusionResult infusionResult;

    public SpiritInfusionRecipe(Item input0, Item input1, Item input2, Item input3, Item catalyst,  Item input4, Item input5, Item input6, Item input7, ItemStack outputStack, int infusionTime, SpiritData data)
    {
        items.add(input1);
        items.add(input2);
        items.add(input4);
        items.add(input7);
        items.add(input6);
        items.add(input5);
        items.add(input3);
        items.add(input0);
        this.catalyst = catalyst;
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.data = data;
    }

    public SpiritInfusionRecipe(Item input0, Item input1, Item input2, Item input3,Item catalyst,  Item input4, Item input5, Item input6, Item input7, ItemStack outputStack, ISpiritInfusionResult infusionResult, int infusionTime, SpiritData data)
    {
        items.add(input1);
        items.add(input2);
        items.add(input4);
        items.add(input7);
        items.add(input6);
        items.add(input5);
        items.add(input3);
        items.add(input0);
        this.catalyst = catalyst;

        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.data = data;
        this.infusionResult = infusionResult;
    }

    public ISpiritInfusionResult getInfusionResult()
    {
        return infusionResult;
    }

    public int getInfusionTime()
    {
        return infusionTime;
    }

    public Item getCatalyst()
    {
        return catalyst;
    }

    public ItemStack getOutputStack()
    {
        return outputStack;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public SpiritData getData()
    {
        return data;
    }

    public static void initRecipes()
    {
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                transmissive_ingot, runic_ash, transmissive_ingot,
                AIR, spirited_steel_ingot, AIR,
                runic_ash, transmissive_ingot, runic_ash,
                stackWithCount(royal_steel_ingot, 6),
                160,
                new SpiritData("minecraft:zombie", 0.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                spirited_steel_ingot, spirit_stone, spirited_steel_ingot,
                spirit_stone, stygian_pearl, spirit_stone,
                spirited_steel_ingot, spirit_stone, spirited_steel_ingot,
                new ItemStack(arcane_apparatus),
                320,
                new SpiritData("minecraft:guardian", 1.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                arcane_apparatus, enchanted_quartz, arcane_apparatus,
                vacant_gemstone, cursed_nebulous, enchanted_quartz,
                arcane_apparatus, vacant_gemstone, arcane_apparatus,
                new ItemStack(stellar_apparatus),
                320,
                new SpiritData("minecraft:wither", 0.25f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                spirit_planks, spirit_planks, spirit_planks,
                spirit_planks, evil_leather, spirit_planks,
                spirit_planks, spirit_planks, spirit_planks,
                stackWithNBT(new ItemStack(curio_material), "power", IntNBT.valueOf(0)),
                320,
                new SpiritData("minecraft:pillager", 0.25f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                spirited_steel_nugget, spirited_steel_ingot, spirited_steel_nugget,
                spirited_steel_ingot, evil_leather, spirited_steel_ingot,
                spirited_steel_nugget, spirited_steel_ingot, spirited_steel_nugget,
                stackWithNBT(new ItemStack(curio_material), "power", IntNBT.valueOf(1)),
                480,
                new SpiritData("minecraft:vindicator", 0.5f)
        ));

        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                PRISMARINE_SHARD, spirited_steel_ingot, PRISMARINE_CRYSTALS,
                spirited_steel_ingot, evil_leather, spirited_steel_ingot,
                PRISMARINE_CRYSTALS, spirited_steel_ingot, PRISMARINE_SHARD,
                stackWithNBT(new ItemStack(curio_material), "power", IntNBT.valueOf(2)),
                640,
                new SpiritData("minecraft:ravager", 1f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                ENDER_EYE, spirited_steel_ingot, ENDER_PEARL,
                spirited_steel_ingot, evil_leather, spirited_steel_ingot,
                ENDER_PEARL, spirited_steel_ingot, ENDER_EYE,
                stackWithNBT(new ItemStack(curio_material), "power", IntNBT.valueOf(3)),
                800,
                new SpiritData("minecraft:evoker", 2f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                spirited_steel_ingot, dark_spirit_stone, spirited_steel_ingot,
                dark_spirit_stone, stellar_apparatus, dark_spirit_stone,
                spirited_steel_ingot, dark_spirit_stone, spirited_steel_ingot,
                stackWithNBT(new ItemStack(curio_material), "power", IntNBT.valueOf(4)),
                960,
                new SpiritData("minecraft:wither", 1f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                vacant_gemstone, SHULKER_SHELL, vacant_gemstone,
                SHULKER_SHELL, curio_material, SHULKER_SHELL,
                AIR, SHULKER_SHELL, AIR,
                new ItemStack(shulker_on_heal_curio),
                new CarryOverNBTResult(),
                640,
                new SpiritData("minecraft:shulker", 5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                PHANTOM_MEMBRANE, arcane_apparatus, PHANTOM_MEMBRANE,
                FEATHER, curio_material, FEATHER,
                FEATHER, AIR, FEATHER,
                new ItemStack(phantom_wings_curio),
                new CarryOverNBTResult(),
                640,
                new SpiritData("minecraft:phantom", 2.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                FLINT, vacant_gemstone, FLINT,
                STICK, curio_material, STICK,
                FEATHER, AIR, FEATHER,
                new ItemStack(crossbow_reload_curio),
                new CarryOverNBTResult(),
                640,
                new SpiritData("minecraft:pillager", 2.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, IRON_INGOT, IRON_BLOCK,
                AIR, IRON_BLOCK, IRON_INGOT,
                STICK, AIR, AIR,
                new ItemStack(ultimate_weapon),
                160,
                new SpiritData("minecraft:iron_golem", 1f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, vacant_gemstone, AIR,
                spirited_steel_ingot, vacant_gemstone, spirited_steel_ingot,
                spirit_stone, spirited_steel_ingot, spirit_stone,
                new ItemStack(block_transmutation_tool),
                640,
                new SpiritData("minecraft:guardian", 3f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, stygian_pearl, AIR,
                spirited_steel_ingot, ENDER_CHEST, spirited_steel_ingot,
                AIR, spirited_steel_ingot, AIR,
                new ItemStack(ender_artifact),
                640,
                new SpiritData("minecraft:enderman", 3f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                GUNPOWDER, AIR, SPIDER_EYE,
                AIR, LEATHER, AIR,
                ROTTEN_FLESH, AIR, BONE,
                stackWithCount(LEATHER, 9),
                80,
                new SpiritData("minecraft:cow", 0.5f)
        ));
    }
}