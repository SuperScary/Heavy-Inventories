package net.superscary.heavyinventories.neoforge.hooks;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.weight.CalculateWeight;

public class TooltipHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        var item = event.getItemStack();
        float weight = CalculateWeight.from(item.getItem(), HeavyInventories.getInstance().getClientLevel());
        event.getToolTip().add(Component.translatable("tooltip.heavyinventories.item_weight", weight));
        if (event.getItemStack().getCount() > 1) event.getToolTip().add(Component.translatable("tooltip.heavyinventories.item_stack_weight", weight * item.getCount()));

        if (Screen.hasShiftDown()) {
            event.getToolTip().add(Component.translatable("tooltip.heavyinventories.item_stack_weight", weight * item.getMaxStackSize()));
        }
    }

}
