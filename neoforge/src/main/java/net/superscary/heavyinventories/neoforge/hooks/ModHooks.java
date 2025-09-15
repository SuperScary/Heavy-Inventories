package net.superscary.heavyinventories.neoforge.hooks;

import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.tooltips.Tooltip;

public class ModHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        Tooltip.addTooltips(event.getToolTip(), event.getItemStack());
    }

    public static void hookPlayer(PlayerTickEvent.Pre event) {
        var holder = PlayerHolder.getOrCreate(event.getEntity());
        holder.update();
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

}
