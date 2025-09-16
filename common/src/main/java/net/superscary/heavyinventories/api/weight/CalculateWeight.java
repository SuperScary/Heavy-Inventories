package net.superscary.heavyinventories.api.weight;

import net.minecraft.world.Container;
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
                weight += from(new ItemStack(itemLike, amount));
            }
        }

        return Functions.either(Format.format(weight), from(new ItemStack(itemLike)), weight > 0);
    }

    public static float from(Fluid fluid, int millibuckets, Level level) {
        return 0;
    }

    public static float from(Container inventory, Level level) {
        float weight = 0;
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            weight += from(inventory.getItem(i));
        }

        return Format.format(weight);
    }

    public static float from(Player player) {
        float weight = 0;
        var inventory = player.getInventory();

        // Inventory
        weight += (float) inventory.items.stream().mapToDouble(CalculateWeight::from).sum();
        // Armor
        weight += (float) inventory.armor.stream().mapToDouble(CalculateWeight::from).sum();
        // Offhand
        weight += (float) inventory.offhand.stream().mapToDouble(CalculateWeight::from).sum();
        return Format.format(weight);
    }

    /**
     * @param itemStack The item to get the weight for.
     * @return A {@link Weight} of the {@link ItemStack}.
     */
    private static float from(ItemStack itemStack) {
        return Format.format(WeightCache.get(itemStack.getItem(), () -> WeightOverride.get(itemStack.getItem()).getWeight()) * itemStack.getCount());
    }

}
