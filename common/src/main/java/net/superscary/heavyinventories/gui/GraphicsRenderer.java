package net.superscary.heavyinventories.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.api.util.MeasuringSystem;
import net.superscary.heavyinventories.config.ConfigOptions;

public class GraphicsRenderer {

    public static void renderGui(GuiGraphics graphics, MeasuringSystem measurement, Minecraft instance) {
        if (!ConfigOptions.ENABLE_GUI_OVERLAY) return;
        if (instance.player == null || instance.player.isCreative() || instance.options.hideGui || instance.screen != null) return;

        int width = instance.getWindow().getGuiScaledWidth();
        int height = instance.getWindow().getGuiScaledHeight();
        int margin = 4;
        int lineH = instance.font.lineHeight;

        var holder = PlayerHolder.getOrCreate(instance.player);
        float cur = holder.getWeight();
        float max = holder.getMaxWeight();
        float pct = holder.getEncumberedPercentage(); // 0..100

        // Bottom line (numbers)
        String main = String.format("%.1f/%.1f %s (%.1f%%)", cur, max, measurement.getSub(), pct);

        // Status line
        Component statusComp = null;
        int statusColor = ConfigOptions.NORMAL_TEXT_COLOR;
        if (holder.isOverEncumbered()) {
            statusComp = Component.translatable("chat.heavyinventories.over_encumbered");
            statusColor = ConfigOptions.OVER_ENCUMBERED_TEXT_COLOR;
        } else if (holder.isEncumbered()) {
            statusComp = Component.translatable("chat.heavyinventories.encumbered");
            statusColor = ConfigOptions.ENCUMBERED_TEXT_COLOR;
        }

        int mainW = instance.font.width(main);
        int statusW = statusComp == null ? 0 : instance.font.width(statusComp);

        int totalLines = statusComp == null ? 1 : 2;
        int blockH = totalLines * lineH;
        int yTop = height - blockH - margin;

        // Draw status
        if (statusComp != null) {
            int xStatus = width - statusW - margin;
            graphics.drawString(instance.font, statusComp, xStatus, yTop, statusColor, true);
            yTop += lineH;
        }

        // Draw main
        int mainColor = pct >= 100 ? ConfigOptions.OVER_ENCUMBERED_TEXT_COLOR : pct >= 90 ? ConfigOptions.ENCUMBERED_TEXT_COLOR : pct >= 75 ? ConfigOptions.ENCUMBERED_TEXT_COLOR : ConfigOptions.NORMAL_TEXT_COLOR;
        int xMain = width - mainW - margin;
        graphics.drawString(instance.font, main, xMain, yTop, mainColor, true);
    }

}
