package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.entity.boomerang.*;
import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

public class MalumStaveItem extends ModCombatItem implements IMalumEventResponderItem {

    public MalumStaveItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn + 3, attackSpeedIn - 3f, builderIn);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player player && !(event.getSource().getDirectEntity() instanceof HexProjectileEntity)) {
            spawnSweepParticles(player, ParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE.get());
            attacker.level().playSound(null, target.blockPosition(), SoundRegistry.STAVE_STRIKES.get(), attacker.getSoundSource(), 0.75f, Mth.nextFloat(attacker.level().random, 0.5F, 1F));
        }
    }

    public void spawnSweepParticles(Player player, SimpleParticleType type) {
        double d0 = (-Mth.sin(player.getYRot() * ((float) Math.PI / 180F)));
        double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (player.level() instanceof ServerLevel) {
            ((ServerLevel) player.level()).sendParticles(type, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }
}