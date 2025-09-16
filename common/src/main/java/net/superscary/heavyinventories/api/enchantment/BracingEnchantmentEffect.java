package net.superscary.heavyinventories.api.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import org.jetbrains.annotations.NotNull;

public record BracingEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<BracingEnchantmentEffect> CODEC = MapCodec.unit(BracingEnchantmentEffect::new);

    private static final float PER_LEVEL_PCT = 0.10f; // +10% per level (tune)
    private static final float CAP_PCT       = 1.0f; // up to +100%

    @Override
    public void apply(@NotNull ServerLevel serverLevel, int enchantmentLevel, @NotNull EnchantedItemInUse enchantedItemInUse, @NotNull Entity entity, @NotNull Vec3 vec3) {
        if (!(entity instanceof Player player)) return;

        var holder = PlayerHolder.getOrCreate(player);
        holder.applyBracing(enchantmentLevel, PER_LEVEL_PCT, CAP_PCT);
    }

    @Override
    public void onDeactivated(EnchantedItemInUse item, Entity entity, Vec3 pos, int enchantmentLevel) {
        if (!(entity instanceof Player player)) return;
        var holder = PlayerHolder.getOrCreate(player);
        holder.clearBracing();
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
