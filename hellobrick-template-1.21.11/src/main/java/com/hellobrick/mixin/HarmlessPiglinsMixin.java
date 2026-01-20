package com.hellobrick.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class HarmlessPiglinsMixin {
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void hellobrick$cancelPiglinDamage(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getEntity();
        if (attacker instanceof Piglin || attacker instanceof PiglinBrute) {
            cir.setReturnValue(false);
            return;
        }

        Entity direct = source.getDirectEntity();
        if (direct instanceof Piglin || direct instanceof PiglinBrute) {
            cir.setReturnValue(false);
            return;
        }

        if (direct instanceof Projectile projectile) {
            Entity owner = projectile.getOwner();
            if (owner instanceof Piglin || owner instanceof PiglinBrute) {
                cir.setReturnValue(false);
                return;
            }
        }
    }
}
