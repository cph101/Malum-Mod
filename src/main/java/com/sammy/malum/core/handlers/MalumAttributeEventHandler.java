package com.sammy.malum.core.handlers;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.registry.common.tag.*;

public class MalumAttributeEventHandler {
    public static void processAttributes(LivingDamageEvent.Pre event) {
        if (event.isCanceled() || event.getOriginalDamage() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            float amount = event.getOriginalDamage();
            ItemStack stack = MalumScytheItem.getScytheItemStack(source, attacker);
            if (stack.isEmpty()) {
                return;
            }
            if (source.is(DamageTypes.THORNS)) {
                return;
            }
            if (!event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
                AttributeInstance scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                if (scytheProficiency != null && scytheProficiency.getValue() > 0) {
                    event.setNewDamage((float) (amount + scytheProficiency.getValue() * 0.5f));
                }
            }
        }
    }
}