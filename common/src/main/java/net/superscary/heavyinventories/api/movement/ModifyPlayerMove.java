package net.superscary.heavyinventories.api.movement;

import net.minecraft.client.player.Input;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.player.PlayerHolder;

public class ModifyPlayerMove {

    private static final float SUREFOOTED_OVERENCUMBERED_SPEED = 0.1f; // 10% when overencumbered
    private static final float MIN_FLOOR_WITH_SUREFOOTED = 0.25f; // 25% when encumbered
    private static final float ENCUMBRANCE_CURVE_K = 0.5f;

    public static void hook(Player player, Input input) {
        if (player.isCreative()) return;

        var holder = PlayerHolder.getOrCreate(player);
        boolean over = holder.isOverEncumbered();

        float enc01 = Mth.clamp(holder.getEncumberedPercentage() / 100f, 0f, 1f);

        // Surefooted floor per level (1.0f means none)
        float mult = getMultiplier(holder, over, enc01);

        input.forwardImpulse *= mult;
        input.leftImpulse    *= mult;

        System.out.println(input.forwardImpulse + " " + input.leftImpulse);
    }

    private static float getMultiplier(PlayerHolder holder, boolean over, float enc01) {
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
        return mult;
    }

}
