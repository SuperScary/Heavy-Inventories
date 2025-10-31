package net.superscary.heavyinventories.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PlayerCraftCallback {

    Event<Crafted> EVENT = EventFactory.createArrayBacked(
            Crafted.class,
            listeners -> (player, result) -> {
                for (var l : listeners) l.onCrafted(player, result);
            }
    );

    @FunctionalInterface
    interface Crafted {
        void onCrafted(Player player, ItemStack result);
    }

}
