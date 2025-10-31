package net.superscary.heavyinventories.fabric.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.superscary.heavyinventories.fabric.callbacks.PlayerPickupItemCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class PlayerInventoryMixin {

    @Inject(method = "addResource(ILnet/minecraft/world/item/ItemStack;)I", at = @At("TAIL"))
    private void hi$onItemPickup(int slot, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        PlayerPickupItemCallback.EVENT.invoker().interact((Inventory) (Object) this, slot, stack);
    }
}