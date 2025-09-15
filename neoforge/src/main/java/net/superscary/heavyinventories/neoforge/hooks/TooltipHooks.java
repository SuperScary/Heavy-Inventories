package net.superscary.heavyinventories.neoforge.hooks;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.weight.CalculateWeight;

public class TooltipHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        var item = event.getItemStack();
        float weight = CalculateWeight.from(item.getItem(), HeavyInventories.getInstance().getClientLevel());
        event.getToolTip().add(Component.literal("" + weight));
    }

}
