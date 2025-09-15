package net.superscary.heavyinventories.api.weight;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.superscary.heavyinventories.api.resource.IResourceList;

public final class CalculateWeight {

    private CalculateWeight() {
    }

    public static float from(ItemLike itemLike, Level level) {
        float weight = 0;
        IResourceList resources = IResourceList.getResourceList(itemLike, level);
        for (var item : resources.getResources()) {
            for (var amount : item.values()) {
                weight += from(new ItemStack(itemLike, amount), level);
            }
        }

        return weight > 0 ? weight : 1;
    }

    public static float from(Fluid fluid, int millibuckets, Level level) {
        return 0;
    }

    public static float from(Inventory inventory, Level level) {
        float weight = 0;
        for (ItemStack itemStack : inventory.items) {
            IResourceList resources = IResourceList.getResourceList(itemStack.getItem(), level);
            for (var item : resources.getResources()) {
                for (var amount : item.values()) {
                    if (itemStack.getCount() >= amount) {
                        weight += from(itemStack, level);
                    }
                }
            }
        }

        return weight;
    }

    public static float from(Player player, Level level) {
        return from(player.getInventory(), level);
    }

    /**
     * TODO: Static return 1 for now for testing if this implementation works. We should use {@link WeightOverride} or a default min value.
     * @param itemStack The item to get the weight for.
     * @param level The level to get the weight from.
     * @return The weight of the {@link ItemStack}.
     */
    private static float from(ItemStack itemStack, Level level) {
        int amount = itemStack.getCount();
        return (float) (0.1 * amount);
    }

}
