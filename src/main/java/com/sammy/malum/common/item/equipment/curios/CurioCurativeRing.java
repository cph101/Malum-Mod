package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.helpers.ItemHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;

public class CurioCurativeRing extends MalumCurioItem implements IMalumEventResponderItem
{
    public CurioCurativeRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }

    @Override
    public void pickupSpirit(LivingEntity attacker, ItemStack stack, boolean isNatural) {
        attacker.heal(attacker.getMaxHealth()*0.1f);
        EntityHelper.giveStackingEffect(MobEffects.REGENERATION, attacker, 100, 0);
    }
}