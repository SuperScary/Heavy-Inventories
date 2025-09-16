package net.superscary.heavyinventories.neoforge.hooks;

import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.superscary.heavyinventories.api.events.PlayerEvents;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.command.ModCommands;
import net.superscary.heavyinventories.tooltips.Tooltip;

public class ModHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        Tooltip.addTooltips(event.getToolTip(), event.getItemStack());
    }

    public static void hookPlayer(PlayerTickEvent.Pre event) {
        PlayerEvents.onPlayerTick(event.getEntity());
    }

    public static void hookPlayerClone(PlayerEvent.Clone event) {
        PlayerEvents.clone(event.getOriginal(), event.getEntity());
    }

    public static void hookPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEvents.playerChangedDimension(event.getEntity());
    }

    public static void hookPlayerPickupItem(ItemEntityPickupEvent.Post event) {
        PlayerEvents.onPickupItem(event.getPlayer());
    }

    public static void hookOnCraft(PlayerEvent.ItemCraftedEvent event) {
        PlayerEvents.onCraft(event.getEntity());
    }

    public static void hookOnSmelt(PlayerEvent.ItemSmeltedEvent event) {
        PlayerEvents.onSmelt(event.getEntity());
    }

    public static void hookPlayerMove(MovementInputUpdateEvent event) {
        var holder = PlayerHolder.getOrCreate(event.getEntity());
        if (holder.isEncumbered()) {
            event.getInput().forwardImpulse = event.getInput().forwardImpulse * 0.25f;
            event.getInput().leftImpulse = event.getInput().leftImpulse * 0.25f;
        } else if (holder.isOverEncumbered()) {
            event.getInput().forwardImpulse = 0;
            event.getInput().leftImpulse = 0;
        }
    }

    public static void hookCommands(RegisterCommandsEvent event) {
        ModCommands.registerCommands(event.getDispatcher());
    }

}
