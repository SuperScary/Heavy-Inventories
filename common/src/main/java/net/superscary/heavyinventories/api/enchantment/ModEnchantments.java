package net.superscary.heavyinventories.api.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.superscary.heavyinventories.HeavyInventories;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> BRACING = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(HeavyInventories.MOD_ID, "bracing"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var items = context.lookup(Registries.ITEM);

        register(context, BRACING, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                5,
                10,
                Enchantment.dynamicCost(12, 7),
                Enchantment.dynamicCost(25, 7),
                2,
                EquipmentSlotGroup.CHEST))
                .withEffect(EnchantmentEffectComponents.TICK, new BracingEnchantmentEffect()));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }

}
