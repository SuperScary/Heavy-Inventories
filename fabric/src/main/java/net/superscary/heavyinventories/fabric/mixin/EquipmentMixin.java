package net.superscary.heavyinventories.fabric.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.superscary.heavyinventories.api.events.PlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EquipmentMixin {

    @Inject(method = "onEquipItem", at = @At("TAIL"))
    private void hi$onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {

        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player)) return;
        if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) return;
        if (oldStack.equals(newStack)) return;

        // UNEQUIP: had something -> now empty
        if (!oldStack.isEmpty() && newStack.isEmpty()) {
            PlayerEvents.onUnequipItem(player);
            return;
        }
        if (!oldStack.isEmpty() && !newStack.isEmpty()) {
            PlayerEvents.onUnequipItem(player);
        }
    }

}
