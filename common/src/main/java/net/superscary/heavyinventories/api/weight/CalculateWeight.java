package net.superscary.heavyinventories.api.weight;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.superscary.heavyinventories.api.format.Format;
import net.superscary.heavyinventories.api.resource.IResourceList;
import net.superscary.heavyinventories.api.util.Functions;

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

        return Functions.either(Format.format(weight), from(new ItemStack(itemLike), level), weight > 0);
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
                    weight += from(itemStack, level) * amount;
                }
            }
        }

        return Format.format(weight);
    }

    public static float from(Player player, Level level) {
        return from(player.getInventory(), level);
    }

    /**
     * @param itemStack The item to get the weight for.
     * @param level The level to get the weight from.
     * @return A {@link Weight} of the {@link ItemStack}.
     */
    private static float from(ItemStack itemStack, Level level) {
        return Format.format(WeightOverride.get(itemStack).getWeight());
    }

}
