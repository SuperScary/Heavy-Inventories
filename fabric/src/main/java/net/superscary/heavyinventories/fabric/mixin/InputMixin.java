package net.superscary.heavyinventories.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.superscary.heavyinventories.fabric.callbacks.MovementInputUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class InputMixin {

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void hi$input(CallbackInfo ci) {
        var client = Minecraft.getInstance();
        var player = client.player;
        if (player != null) {
            MovementInputUpdateEvent.EVENT.invoker().onMovementInputUpdate(player, player.input);
        }
    }

}
