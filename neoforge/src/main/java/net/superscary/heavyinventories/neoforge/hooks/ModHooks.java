package net.superscary.heavyinventories.neoforge.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.superscary.heavyinventories.api.events.PlayerEvents;
import net.superscary.heavyinventories.api.movement.ModifyPlayerMove;
import net.superscary.heavyinventories.command.ModCommands;
import net.superscary.heavyinventories.gui.GraphicsRenderer;
import net.superscary.heavyinventories.neoforge.config.ClientConfig;
import net.superscary.heavyinventories.tooltips.Tooltip;

public class ModHooks {

    public static void hookTooltip(ItemTooltipEvent event) {
        Tooltip.addTooltips(event.getToolTip(), event.getItemStack());
    }

    public static void hookPlayer(PlayerTickEvent.Pre event) {
        PlayerEvents.onPlayerTick(event.getEntity());
    }

    public static void hookPlayerClone(PlayerEvent.Clone event) {
        PlayerEvents.clone(event.getOriginal(), event.getEntity());
    }

    public static void hookPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEvents.playerChangedDimension(event.getEntity());
    }

    public static void hookPlayerPickupItem(ItemEntityPickupEvent.Post event) {
        PlayerEvents.onPickupItem(event.getPlayer());
    }

    public static void hookOnCraft(PlayerEvent.ItemCraftedEvent event) {
        PlayerEvents.onCraft(event.getEntity());
    }

    public static void hookOnSmelt(PlayerEvent.ItemSmeltedEvent event) {
        PlayerEvents.onSmelt(event.getEntity());
    }

    public static void hookPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerEvents.logout(event.getEntity());
    }

    public static void hookPlayerMove(MovementInputUpdateEvent event) {
        ModifyPlayerMove.hook(event.getEntity(), event.getInput());
    }

    public static void hookPlayerEquip(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if(!event.getSlot().isArmor()) return;

        //PlayerEvents.onEquipItem(player);
        PlayerEvents.onUnequipItem(player);
    }

    public static void hookCommands(RegisterCommandsEvent event) {
        ModCommands.registerCommands(event.getDispatcher());
    }

    public static void hookGui(RenderGuiEvent.Post event) {
        if (!ClientConfig.SHOW_WEIGHT_ON_SCREEN.get()) return;
        GraphicsRenderer.renderGui(event.getGuiGraphics(), ClientConfig.WEIGHT_MEASURE.get(), Minecraft.getInstance());
    }

}
