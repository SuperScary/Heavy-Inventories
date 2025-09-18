package net.superscary.heavyinventories.api.weight;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.superscary.heavyinventories.api.format.Format;
import net.superscary.heavyinventories.api.resource.IResourceList;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class CalculateWeight {

    private static final ConcurrentHashMap<ItemLike, Float> RECURSIVE_CACHE = new ConcurrentHashMap<>();

    private CalculateWeight() {
    }

    public static float from(ItemLike itemLike, Level level) {
        return from(itemLike, level, new HashSet<>(), 0);
    }

    private static float from(ItemLike itemLike, Level level, Set<ItemLike> visited, int depth) {
        if (depth > 10) {
            return from(new ItemStack(itemLike));
        }

        if (visited.contains(itemLike)) {
            return from(new ItemStack(itemLike));
        }

        Float cachedWeight = RECURSIVE_CACHE.get(itemLike);
        if (cachedWeight != null) {
            return Format.format(cachedWeight);
        }

        float weight = calculateRecursiveWeight(itemLike, level, visited, depth);

        if (weight == 0) weight = 0.1f;

        RECURSIVE_CACHE.put(itemLike, weight);
        
        return Format.format(weight);
    }
    
    private static float calculateRecursiveWeight(ItemLike itemLike, Level level, Set<ItemLike> visited, int depth) {
        visited.add(itemLike);
        
        float weight = 0;
        IResourceList resources = IResourceList.getResourceList(itemLike, level);

        if (!resources.getResources().isEmpty()) {
            var firstRecipe = resources.getResources().iterator().next();
            
            for (var entry : firstRecipe.entrySet()) {
                ItemLike ingredient = entry.getKey();
                int amount = entry.getValue();

                float ingredientWeight = from(ingredient, level, visited, depth + 1);
                weight += ingredientWeight * amount;
            }
        } else {
            weight = from(new ItemStack(itemLike));
        }

        visited.remove(itemLike);
        return weight;
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
     * @return A weight of the {@link ItemStack}.
     */
    private static float from(ItemStack itemStack) {
        return from(itemStack, itemStack.getCount());
    }

    private static float from(ItemStack itemStack, int count) {
        return Format.format(WeightCache.get(itemStack.getItem(), () -> WeightOverride.get(itemStack.getItem())) * count);
    }

    /**
     * Clear the recursive weight cache. Should be called when weights are updated or datapacks are reloaded.
     */
    public static void clearRecursiveCache() {
        RECURSIVE_CACHE.clear();
    }

}
