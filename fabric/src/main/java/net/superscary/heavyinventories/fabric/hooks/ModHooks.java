package net.superscary.heavyinventories.fabric.hooks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.events.PlayerEvents;

public class ModHooks {

    public static void registerHooks() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;
            PlayerEvents.onPlayerTick(client.player);
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof Player player) {
                PlayerEvents.clone(player, player);
            }
        });


    }

}
