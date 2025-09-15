package net.superscary.heavyinventories.tooltips;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.weight.CalculateWeight;

import java.util.List;

public class Tooltip {

    /**
     * Adds the weight tooltips to the given list of tooltips.
     * @param tooltip The list of tooltips to add to.
     * @param stack The {@link ItemStack} to get the weight for.
     * @return The list of tooltips with the weight tooltips added.
     */
    public static List<Component> addTooltips(List<Component> tooltip, ItemStack stack) {
        float weight = CalculateWeight.from(stack.getItem(), HeavyInventories.getInstance().getClientLevel());

        tooltip.add(Component.translatable("tooltip.heavyinventories.item_weight", weight));
        if (stack.getCount() > 1) tooltip.add(Component.translatable("tooltip.heavyinventories.item_stack_weight", weight * stack.getCount()));

        if (HeavyInventories.isShiftPressed() && stack.getCount() < stack.getMaxStackSize()) {
            tooltip.add(Component.translatable("tooltip.heavyinventories.item_stack_weight", weight * stack.getMaxStackSize()));
        }
        return tooltip;
    }

}
