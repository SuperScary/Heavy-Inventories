package net.superscary.heavyinventories.neoforge.hooks;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.superscary.heavyinventories.tooltips.Tooltip;

public class ModHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        Tooltip.addTooltips(event.getToolTip(), event.getItemStack());
    }

}
