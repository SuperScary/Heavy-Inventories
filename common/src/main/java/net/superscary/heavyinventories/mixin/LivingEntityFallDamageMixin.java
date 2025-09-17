package net.superscary.heavyinventories.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Scales the fall-damage multiplier for players based on encumbrance:
 * - Encumbered: x1.5
 * - Over-encumbered: x3.0
 * <p>
 * We modify the "damageMultiplier" parameter (2nd float) at the method head, so
 * vanilla still applies boots, effects, etc. on top of our scaled value.
 */

@Mixin(LivingEntity.class)
public abstract class LivingEntityFallDamageMixin {

    @ModifyVariable(method = "causeFallDamage", at = @At("HEAD"), argsOnly = true,  ordinal = 1)
    private float heavyinventories$scaleFallDamage(float damageMultiplier) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.level().isClientSide()) return damageMultiplier;
        if (!(self instanceof Player player)) return damageMultiplier;

        var holder = PlayerHolder.getOrCreate(player);
        if (holder.isOverEncumbered()) return damageMultiplier * 3.0f;
        else if (holder.isEncumbered()) return damageMultiplier * 1.5f;
        else return damageMultiplier;
    }
}
