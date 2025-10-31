package net.superscary.heavyinventories.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public interface PlayerPickupItemCallback {

    Event<PlayerPickupItemCallback> EVENT = EventFactory.createArrayBacked(PlayerPickupItemCallback.class,
            (listeners) -> (player, entity, amount) -> {
                for (PlayerPickupItemCallback event : listeners) {
                    event.interact(player, entity, amount);
                }
            }
    );

    void interact(Inventory playerPickingUpItems, int slot, ItemStack entityBeingPickedUp);
}
