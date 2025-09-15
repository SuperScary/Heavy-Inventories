package net.superscary.heavyinventories.forge.hooks;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.superscary.heavyinventories.tooltips.Tooltip;

public class ModHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        Tooltip.addTooltips(event.getToolTip(), event.getItemStack());
    }

}
