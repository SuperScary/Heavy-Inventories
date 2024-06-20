package superscary.heavyinventories.data;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import superscary.heavyinventories.config.CommonConfig;
import superscary.heavyinventories.codec.ModCodecs;
import superscary.heavyinventories.data.player.enc.EncumbranceManager;
import superscary.heavyinventories.data.player.ModDataAttachments;
import superscary.heavyinventories.data.player.PlayerWeight;
import superscary.heavyinventories.data.player.enc.EncumbranceRegistry;
import superscary.heavyinventories.data.weight.ItemWeight;
import superscary.heavyinventories.data.weight.WeightGenerator;

public final class WeightCalculator {

    /**
     * Updates players weight and handles encumbrance setting
     * @param player {@link Player}
     */
    public static void updatePlayerWeight (Player player) {
        PlayerWeight weight = player.setData(ModDataAttachments.PLAYER_WEIGHT.get(), new PlayerWeight(measurePlayerWeight(player), CommonConfig.playerStartingWeight));
        EncumbranceManager manager = EncumbranceRegistry.getOrMake(player, weight);
        manager.delegate();
    }

    public static double measurePlayerWeight (Player player) {
        Inventory inventory = player.getInventory();
        double calc = 0;
        for (var item : inventory.items) {
            var itemWeight = item.get(ModCodecs.ITEM_WEIGHT_COMPONENT);
            calc += getWeight(itemWeight, item);
        }
        return calc;
    }

    public static ItemWeight makeItemWeighable (ItemStack stack) {
        ItemWeight weight = new ItemWeight(getModid(stack), getRegName(stack), getName(stack), WeightGenerator.unbound());
        stack.set(ModCodecs.ITEM_WEIGHT_COMPONENT, weight);
        stack.update(ModCodecs.ITEM_WEIGHT_COMPONENT, weight, w -> w);
        return weight;
    }

    private static String getModid (ItemStack stack) {
        return stack.getItem().toString().split(":")[0];
    }

    private static String getRegName (ItemStack stack) {
        return stack.getItem().toString().split(":")[1];
    }

    private static String getName (ItemStack stack) {
        return stack.getItem().getDescription().getString();
    }

    public static double getWeight (ItemWeight weight, ItemStack stack) {
        return getWeight(weight, stack.getItem()) * stack.getCount();
    }

    public static double getWeight (ItemWeight weight, Item item) {
        return weight != null ? weight.weight() : WeightGenerator.generateForItem(item);
    }

}
