package net.superscary.heavyinventories.api.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import org.jetbrains.annotations.NotNull;

public record SurefootedEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<SurefootedEnchantmentEffect> CODEC = MapCodec.unit(SurefootedEnchantmentEffect::new);

    private static final float ENCUMBERED_BASE = 0.25f; // base movement when encumbered (no boots)
    private static final float OVER_PER_LEVEL = 0.05f; // floor while over-enc: +5% per level (L4 = 0.20)
    private static final float ENC_PER_LEVEL = 0.10f; // floor while enc: +10% per level (L4 = 0.40)

    @Override
    public void apply(@NotNull ServerLevel serverLevel, int enchantmentLevel, @NotNull EnchantedItemInUse enchantedItemInUse, @NotNull Entity entity, @NotNull Vec3 vec3) {
        if (!(entity instanceof Player player)) return;

        var holder = PlayerHolder.getOrCreate(player);
        holder.applySureFooted(enchantmentLevel);
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
