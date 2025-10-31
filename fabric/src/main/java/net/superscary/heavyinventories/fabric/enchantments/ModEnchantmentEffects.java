package net.superscary.heavyinventories.fabric.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.enchantment.BracingEnchantmentEffect;
import net.superscary.heavyinventories.api.enchantment.ReinforcedEnchantmentEffect;
import net.superscary.heavyinventories.api.enchantment.SurefootedEnchantmentEffect;

public class ModEnchantmentEffects {

    public static final MapCodec<BracingEnchantmentEffect> BRACING = register("bracing", BracingEnchantmentEffect.CODEC);
    public static final MapCodec<ReinforcedEnchantmentEffect> REINFORCED = register("reinforced", ReinforcedEnchantmentEffect.CODEC);
    public static final MapCodec<SurefootedEnchantmentEffect> SUREFOOTED = register("surefooted", SurefootedEnchantmentEffect.CODEC);

    private static <E extends EnchantmentEntityEffect> MapCodec<E> register(String id, MapCodec<E> codec) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ResourceLocation.fromNamespaceAndPath(HeavyInventories.MOD_ID, id), codec);
    }

    public static void register() {
        HeavyInventories.LOGGER.info("Initializing enchantment effects");
    }

}
