package net.superscary.heavyinventories.fabric.hooks;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record TooltipData(List<Component> tooltip, ItemStack stack) implements TooltipComponent {
}
