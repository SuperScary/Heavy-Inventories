package net.superscary.heavyinventories.neoforge.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
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

    private static final float SUREFOOTED_OVERENCUMBERED_SPEED = 0.1f; // 10% when overencumbered
    private static final float MIN_FLOOR_WITH_SUREFOOTED = 0.25f; // 25% when encumbered
    private static final float ENCUMBRANCE_CURVE_K = 0.5f;

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
        var player = event.getEntity();
        if (player.isCreative()) return;

        var holder = PlayerHolder.getOrCreate(player);
        boolean over = holder.isOverEncumbered();

        float enc01 = Mth.clamp(holder.getEncumberedPercentage() / 100f, 0f, 1f);

        // Surefooted floor per level (1.0f means none)
        float surefootedFloor = holder.getSureFootedMult();
        boolean hasSurefooted = surefootedFloor < 1.0f;

        float mult;
        if (over) {
            mult = hasSurefooted ? SUREFOOTED_OVERENCUMBERED_SPEED : 0f;
        } else {
            mult = (float) Math.pow(1f - enc01, ENCUMBRANCE_CURVE_K);

            if (hasSurefooted) {
                mult = Math.max(mult, Math.max(surefootedFloor, MIN_FLOOR_WITH_SUREFOOTED));
            }
        }

        mult = Mth.clamp(mult, 0f, 1f);

        var input = event.getInput();
        input.forwardImpulse *= mult;
        input.leftImpulse    *= mult;
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
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.isCreative() || mc.options.hideGui || mc.screen != null) return;

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
