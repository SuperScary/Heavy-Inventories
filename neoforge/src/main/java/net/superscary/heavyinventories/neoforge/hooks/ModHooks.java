package net.superscary.heavyinventories.neoforge.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
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
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.command.ModCommands;
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

    public static void hookPlayerMove(MovementInputUpdateEvent event) {
        var holder = PlayerHolder.getOrCreate(event.getEntity());
        if (holder.isEncumbered()) {
            event.getInput().forwardImpulse = event.getInput().forwardImpulse * 0.25f;
            event.getInput().leftImpulse = event.getInput().leftImpulse * 0.25f;
        } else if (holder.isOverEncumbered()) {
            event.getInput().forwardImpulse = 0;
            event.getInput().leftImpulse = 0;
        }
    }

    public static void hookPlayerEquip(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        PlayerEvents.onEquipItem(player);
        PlayerEvents.onUnequipItem(player);
    }

    public static void hookCommands(RegisterCommandsEvent event) {
        ModCommands.registerCommands(event.getDispatcher());
    }

    public static void hookGui(RenderGuiEvent.Post event) {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui || mc.screen != null) return;

        var g = event.getGuiGraphics();
        int sw = mc.getWindow().getGuiScaledWidth();
        int sh = mc.getWindow().getGuiScaledHeight();
        int margin = 4;
        int lineH = mc.font.lineHeight;

        var holder = PlayerHolder.getOrCreate(mc.player);
        float cur = holder.getWeight();
        float max = holder.getMaxWeight();
        float pct = holder.getEncumberedPercentage(); // 0..100

        // Bottom line (numbers)
        String main = String.format("%.1f/%.1f %s (%.1f%%)", cur, max, ClientConfig.WEIGHT_MEASURE.get(), pct);

        // Status line
        Component statusComp = null;
        int statusColor = 0xFFFFFF;
        if (holder.isOverEncumbered()) {
            statusComp = Component.translatable("chat.heavyinventories.over_encumbered");
            statusColor = 0xFF5555; // red
        } else if (holder.isEncumbered()) {
            statusComp = Component.translatable("chat.heavyinventories.encumbered");
            statusColor = 0xFFFF55; // yellow
        }

        int mainW = mc.font.width(main);
        int statusW = statusComp == null ? 0 : mc.font.width(statusComp);

        int totalLines = statusComp == null ? 1 : 2;
        int blockH = totalLines * lineH;
        int yTop = sh - blockH - margin;

        // Draw status
        if (statusComp != null) {
            int xStatus = sw - statusW - margin;
            g.drawString(mc.font, statusComp, xStatus, yTop, statusColor, true);
            yTop += lineH;
        }

        // Draw main
        int mainColor = pct >= 100 ? 0xFF5555 : pct >= 90 ? 0xFFAA00 : pct >= 75 ? 0xFFFF55 : 0xFFFFFF;
        int xMain = sw - mainW - margin;
        g.drawString(mc.font, main, xMain, yTop, mainColor, true);
    }

}
