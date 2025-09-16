package net.superscary.heavyinventories.api.weight;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.superscary.heavyinventories.api.files.DataType;
import net.superscary.heavyinventories.api.files.ReadFile;
import net.superscary.heavyinventories.api.files.WriteFile;

/**
 * If the default weight for an item is overridden in the .json file, with convention to its "modid.json",
 * we will load that weight from {@link ReadFile} here instead of using the default weight.
 */
public final class WeightOverride {

    private WeightOverride() {}

    public static Weight get(ItemLike itemLike) {
        return get(new ItemStack(itemLike, 1));
    }

    public static Weight get(ItemStack itemStack) {
        return () -> ReadFile.get(itemStack.getItem(), DataType.WEIGHT) * itemStack.getCount();
    }

    public static void put(ItemLike itemLike, Weight weight) {
        put(itemLike, weight.getWeight());
    }

    public static void put(ItemLike itemLike, float weight) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(itemLike.asItem());
        WriteFile.writeToFile(Minecraft.getInstance().gameDirectory.getPath() + "/weights/" + id.getNamespace() + ".json", id.getPath(), DataType.WEIGHT, weight);
    }

}
