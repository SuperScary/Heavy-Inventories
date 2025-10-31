package net.superscary.heavyinventories.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public interface MovementInputUpdateEvent {

    Event<MovementInputUpdateEvent> EVENT = EventFactory.createArrayBacked(MovementInputUpdateEvent.class,
            listeners -> (player, input) -> {
                for (var l : listeners) l.onMovementInputUpdate(player, input);
            });

    void onMovementInputUpdate(Player player, Input input);

}
