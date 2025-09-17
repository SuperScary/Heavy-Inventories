package net.superscary.heavyinventories.neoforge.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.enchantment.BracingEnchantmentEffect;
import net.superscary.heavyinventories.api.enchantment.ReinforcedEnchantmentEffect;
import net.superscary.heavyinventories.api.enchantment.SurefootedEnchantmentEffect;

import java.util.function.Supplier;

public class ModEnchantmentEffects {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> REGISTRY = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, HeavyInventories.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> BRACING = REGISTRY.register("bracing", () -> BracingEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> REINFORCED = REGISTRY.register("reinforced", () -> ReinforcedEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SUREFOOTED = REGISTRY.register("surefooted", () -> SurefootedEnchantmentEffect.CODEC);

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }

}
