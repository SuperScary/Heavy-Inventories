package net.superscary.heavyinventories.api.resource;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * This lists and returns all crafting resources for an item.
 * Used to break down crafting recipes to calculate the weight of an item.
 */
public interface IResourceList {

    /**
     * Gets the crafting resources for an item.
     * It is a collection in the case of multiple recipes for the same item.
     * Heavy Inventories will use the first recipe found.
     * <p>
     * ItemLike -> The item.
     * Integer -> The number of the specified item in the recipe.
     * @return The crafting resources.
     */
    Collection<HashMap<ItemLike, Integer>> getResources();

    /**
     * Gets a list of resources that can be used to get all items used to craft the specified item.
     * @param itemLike The item to get the crafting resources for.
     * @param level The level to get the crafting resources from.
     * @return {@link IResourceList} The crafting resources.
     */
    static IResourceList getResourceList(ItemLike itemLike, Level level) {
        final Collection<HashMap<ItemLike, Integer>> resources = new ArrayList<>();
        var access = level.registryAccess();

        List<RecipeHolder<CraftingRecipe>> matches = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)
                .stream().filter(r -> {
                    ItemStack out = r.value().getResultItem(access);
                    return out.is(itemLike.asItem());
                }).toList();

        for (RecipeHolder<CraftingRecipe> match : matches) {
            HashMap<ItemLike, Integer> map = new HashMap<>();
            for (Ingredient ingredient : match.value().getIngredients()) {
                for (ItemStack stack : ingredient.getItems()) {
                    ItemLike item = stack.getItem();
                    map.merge(item, 1, Integer::sum);
                }
            }

            resources.add(map);
        }

        return () -> resources;
    }

}
