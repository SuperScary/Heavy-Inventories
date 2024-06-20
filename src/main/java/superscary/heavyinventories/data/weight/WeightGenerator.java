package superscary.heavyinventories.data.weight;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import superscary.heavyinventories.codec.ModCodecs;
import superscary.heavyinventories.config.CommonConfig;
import superscary.heavyinventories.data.WeightCalculator;

import java.util.Random;

/**
 * Reads / generates weights for items when building {@link ItemWeight}
 */
public final class WeightGenerator {

    private static final double DEFAULT_WEIGHT = CommonConfig.minimumItemWeight;

    public static double generateForItem (ItemStack stack) {
        if (Minecraft.getInstance().level == null) return unbound();
        if (stack.has(ModCodecs.ITEM_WEIGHT_COMPONENT)) {
            return stack.get(ModCodecs.ITEM_WEIGHT_COMPONENT).weight();
        } else {
            ItemWeight weight = WeightCalculator.makeItemWeighable(stack);
            return weight.weight();
        }
    }

    public static double generateForItem (Item item) {
        return generateForItem(new ItemStack(item));
    }

    /**
     * Used to make a set rule for weight generation
     * @return {@link Double}
     */
    public static double unbound () {
        return DEFAULT_WEIGHT;
    }

    public static int random () {
        Random random = new Random();
        return random.nextInt(1, 20);
    }

}
