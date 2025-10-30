package net.superscary.heavyinventories.api.weight;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.superscary.heavyinventories.api.files.DataType;
import net.superscary.heavyinventories.api.files.ReadFile;
import net.superscary.heavyinventories.api.files.WriteFile;

import java.util.List;

/**
 * If the default weight for an item is overridden in the .json file, with convention to its "modid.json",
 * we will load that weight from {@link ReadFile} here instead of using the default weight.
 */
public final class WeightOverride {

    private WeightOverride() {}

    public static float get(ItemLike itemLike) {
        return get(new ItemStack(itemLike, 1));
    }

    public static float get(ItemStack itemStack) {
        return ReadFile.get(itemStack.getItem(), DataType.WEIGHT) * itemStack.getCount();
    }

    public static void put(ItemLike itemLike, float weight) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(itemLike.asItem());
        WriteFile.writeToFile(id.getNamespace(), id.getPath(), DataType.WEIGHT, weight);
    }

    /**
     * For writing dumps
     * @see net.superscary.heavyinventories.command.ModCommands
     */
    public static void putDumpFile(List<Item> items, List<Block> blocks, Level level) {
        for (Item item : items) {
            put(item, CalculateWeight.from(item, level));
        }

        for (Block block : blocks) {
            put(block, CalculateWeight.from(block, level));
        }
    }

}
