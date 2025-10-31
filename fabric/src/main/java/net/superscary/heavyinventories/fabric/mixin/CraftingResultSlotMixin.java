package net.superscary.heavyinventories.fabric.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.superscary.heavyinventories.fabric.callbacks.PlayerCraftCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
abstract class CraftingResultSlotMixin {

    @Shadow @Final private ResultContainer resultSlots;

    @Inject(method = "removed", at = @At("TAIL"))
    private void hi$afterCraft(Player player, CallbackInfo ci) {
        if (!player.level().isClientSide() && player instanceof ServerPlayer sp) {
            PlayerCraftCallback.EVENT.invoker().onCrafted(sp, this.resultSlots.getItem(0));
        }
    }
}
