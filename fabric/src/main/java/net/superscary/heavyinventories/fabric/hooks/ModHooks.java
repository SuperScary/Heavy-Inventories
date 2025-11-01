package net.superscary.heavyinventories.fabric.hooks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.events.PlayerEvents;
import net.superscary.heavyinventories.api.movement.ModifyPlayerMove;
import net.superscary.heavyinventories.command.ModCommands;
import net.superscary.heavyinventories.config.ConfigOptions;
import net.superscary.heavyinventories.fabric.callbacks.MovementInputUpdateEvent;
import net.superscary.heavyinventories.fabric.callbacks.PlayerCraftCallback;
import net.superscary.heavyinventories.fabric.callbacks.PlayerPickupItemCallback;
import net.superscary.heavyinventories.gui.GraphicsRenderer;

public class ModHooks {

    public static void registerHooks() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) PlayerEvents.onPlayerTick(client.player);
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof Player player) {
                PlayerEvents.clone(player, player);
            }
        });

        HudRenderCallback.EVENT.register((graphics, tracker) -> GraphicsRenderer.renderGui(graphics, ConfigOptions.WEIGHT_MEASURE, Minecraft.getInstance()));

        PlayerPickupItemCallback.EVENT.register((livingEntity, slot, stack) -> PlayerEvents.onPickupItem(livingEntity.player));

        MovementInputUpdateEvent.EVENT.register((ModifyPlayerMove::hook));

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> PlayerEvents.logout(handler.player));

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, oldWorld, newWorld) -> PlayerEvents.playerChangedDimension(player));

        PlayerCraftCallback.EVENT.register((player, stack) -> PlayerEvents.onCraft(player));

        registerCommands();
    }

    protected static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> ModCommands.registerCommands(commandDispatcher));
    }

}
