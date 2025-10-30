package net.superscary.heavyinventories.api.resource;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * This lists and returns all crafting resources for an item.
 * Used to break down crafting recipes to calculate the weight of an item.
 * <p>
 * I cannot say for certain that this covers all possible recipes, but it should cover most.
 * Namely, brewing recipes, those are not in a datapack, so it is near impossible to get them without
 * a mod loader. That would have to be done on the mod loaders specific side.
 * <p>
 * This is not really that efficient, but it should be fine for most cases. We should look at checking after each run of
 * adding a recipe to the list if it contains anything. If it does, we can just return the list, rather than continuing
 * to iterate through and still returning the first item recipe found. The resulting speed may be negligible, since it's
 * all being cached anyway. We are not covering {@link RecipeType#STONECUTTING} since it is not really a crafting recipe.
 * We are also not covering {@link RecipeType#CAMPFIRE_COOKING} since there is no point if we are already covering
 * {@link RecipeType#SMELTING}.
 *
 * @author SuperScary
 * @since 4.0.0
 * @see RecipeType RecipeType for more information on recipe types.
 * @see net.minecraft.world.item.alchemy.PotionBrewing PotionBrewing for more information on brewing recipes.
 *
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
        resources.addAll(getCraftingTableList(itemLike, level).getResources());
        resources.addAll(getSmeltingList(itemLike, level).getResources());
        resources.addAll(getBlastingList(itemLike, level).getResources());
        resources.addAll(getSmokingList(itemLike, level).getResources());
        resources.addAll(getSmithingList(itemLike, level).getResources());

        return () -> resources;
    }

    /**
     * Gets a list of crafting resources made for the specified item using the crafting table.
     * @param itemLike The item to get the crafting resources for.
     * @param level The level to get the crafting resources from.
     * @return {@link IResourceList} The crafting resources.
     */
    static IResourceList getCraftingTableList(ItemLike itemLike, Level level) {
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

    /**
     * Gets a list of crafting resources made for the specified item using the furnace.
     * @param itemLike The item to get the crafting resources for.
     * @param level The level to get the crafting resources from.
     * @return {@link IResourceList} The crafting resources.
     */
    static IResourceList getSmeltingList(ItemLike itemLike, Level level) {
        final Collection<HashMap<ItemLike, Integer>> resources = new ArrayList<>();
        var access = level.registryAccess();

        List<RecipeHolder<SmeltingRecipe>> matches = level.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING)
                .stream().filter(r -> {
                    ItemStack out = r.value().getResultItem(access);
                    return out.is(itemLike.asItem());
                }).toList();

        for (RecipeHolder<SmeltingRecipe> match : matches) {
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

    /**
     * Gets a list of crafting resources made for the specified item using the blast furnace.
     * @param itemLike The item to get the crafting resources for.
     * @param level The level to get the crafting resources from.
     * @return {@link IResourceList} The crafting resources.
     */
    static IResourceList getBlastingList(ItemLike itemLike, Level level) {
        final Collection<HashMap<ItemLike, Integer>> resources = new ArrayList<>();
        var access = level.registryAccess();

        List<RecipeHolder<BlastingRecipe>> matches = level.getRecipeManager().getAllRecipesFor(RecipeType.BLASTING)
                .stream().filter(r -> {
                    ItemStack out = r.value().getResultItem(access);
                    return out.is(itemLike.asItem());
                }).toList();

        for (RecipeHolder<BlastingRecipe> match : matches) {
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

    /**
     * Gets a list of crafting resources made for the specified item using the smoker.
     * @param itemLike The item to get the crafting resources for.
     * @param level The level to get the crafting resources from.
     * @return {@link IResourceList} The crafting resources.
     */
    static IResourceList getSmokingList(ItemLike itemLike, Level level) {
        final Collection<HashMap<ItemLike, Integer>> resources = new ArrayList<>();
        var access = level.registryAccess();

        List<RecipeHolder<SmokingRecipe>> matches = level.getRecipeManager().getAllRecipesFor(RecipeType.SMOKING)
                .stream().filter(r -> {
                    ItemStack out = r.value().getResultItem(access);
                    return out.is(itemLike.asItem());
                }).toList();

        for (RecipeHolder<SmokingRecipe> match : matches) {
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

    /**
     * Gets a list of crafting resources made for the specified item using the smithing table.
     * @param itemLike The item to get the crafting resources for.
     * @param level The level to get the crafting resources from.
     * @return {@link IResourceList} The crafting resources.
     */
    static IResourceList getSmithingList(ItemLike itemLike, Level level) {
        final Collection<HashMap<ItemLike, Integer>> resources = new ArrayList<>();
        var access = level.registryAccess();

        List<RecipeHolder<SmithingRecipe>> matches = level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING)
                .stream().filter(r -> {
                    ItemStack out = r.value().getResultItem(access);
                    return out.is(itemLike.asItem());
                }).toList();

        for (RecipeHolder<SmithingRecipe> match : matches) {
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
