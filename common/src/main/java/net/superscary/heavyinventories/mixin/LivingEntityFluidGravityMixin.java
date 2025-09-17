package net.superscary.heavyinventories.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Increase the effective gravity used for fluid falling/sinking for encumbered players.
 * Encumbered: x1.5, Over-encumbered: x3.0
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityFluidGravityMixin {

    // protected Vec3 getFluidFallingAdjustedMovement(double gravity, boolean falling, Vec3 movement)
    // We modify the first parameter (double gravity) at HEAD.
    @ModifyVariable(
            method = "getFluidFallingAdjustedMovement",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0 // first param: gravity
    )
    private double heavyinventories$scaleFluidGravity(double gravity) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (!(self instanceof Player player)) return gravity;
        if (!(self.isInWaterOrBubble() || self.isInLava())) return gravity;

        float mult = PlayerHolder.getOrCreate(player).getFluidSinkGravityMultiplier();
        return gravity * mult;
    }
}
