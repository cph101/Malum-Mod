package com.sammy.malum.common.item.cosmetic.skins.risk_of_rain;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import static com.sammy.malum.MalumMod.malumPath;

public class ExecutionerArmorSkin extends ArmorSkin {
    public ExecutionerArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/risk_of_rain/executioner.png"), ModelRegistry.EXECUTIONER);
    }
}
