package net.superscary.heavyinventories.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.api.util.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class LivingEntityJumpFromGroundMixin {

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void heavyinventories$preventJump(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (!(self instanceof Player player)) return;
        var holder = PlayerHolder.getOrCreate(player);

        if (holder.isEncumbered() || holder.isOverEncumbered()) {
            player.sendSystemMessage(Component.translatable("chat.heavyinventories.no_jump",
                    Component.translatable(Functions.either("chat.heavyinventories.over_encumbered", "chat.heavyinventories.encumbered", holder.isOverEncumbered()))));
            ci.cancel();
        }
    }

}
