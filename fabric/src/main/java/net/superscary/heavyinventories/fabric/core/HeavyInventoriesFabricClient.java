package net.superscary.heavyinventories.fabric.core;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.superscary.heavyinventories.api.config.ConfigScreens;
import net.superscary.heavyinventories.fabric.config.ModClientConfig;
import net.superscary.heavyinventories.tooltips.Tooltip;

public class HeavyInventoriesFabricClient extends HeavyInventoriesFabricBase {

    public HeavyInventoriesFabricClient() {
        super();

        registerTooltipCallback();
        ConfigScreens.register(new ModClientConfig());
    }

    private void registerTooltipCallback() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> Tooltip.addTooltips(lines, stack));
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
