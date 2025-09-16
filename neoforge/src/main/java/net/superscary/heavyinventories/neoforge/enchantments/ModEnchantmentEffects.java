package net.superscary.heavyinventories.neoforge.enchantments;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.enchantment.BracingEnchantmentEffect;

public class ModEnchantmentEffects {

    public static void register(IEventBus modEventBus) {
        var registry = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, HeavyInventories.MOD_ID);

        registry.register("bracing", () -> BracingEnchantmentEffect.CODEC);

        registry.register(modEventBus);
    }

}
