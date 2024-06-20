package superscary.heavyinventories.events;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import superscary.heavyinventories.HeavyInventories;
import superscary.heavyinventories.codec.ModCodecs;
import superscary.heavyinventories.data.WeightCalculator;
import superscary.heavyinventories.data.weight.ItemWeight;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = HeavyInventories.MODID)
public class ClientEvents {

    @SubscribeEvent
    public static void tooltip (ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        ItemWeight itemWeight = stack.get(ModCodecs.ITEM_WEIGHT_COMPONENT);
        event.getToolTip().add(Component.translatable("chat.heavyinventories.tooltip", WeightCalculator.getWeight(itemWeight, stack)).withStyle(ChatFormatting.DARK_GRAY));
    }

    @SubscribeEvent
    public static void inventoryChange (ItemEntityPickupEvent.Post event) {
        WeightCalculator.updatePlayerWeight(event.getPlayer());
    }

    @SubscribeEvent
    public static void tossEvent (ItemTossEvent event) {
        WeightCalculator.updatePlayerWeight(event.getPlayer());
    }

}
