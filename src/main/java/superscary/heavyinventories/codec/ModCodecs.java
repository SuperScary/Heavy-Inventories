package superscary.heavyinventories.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import superscary.heavyinventories.HeavyInventories;
import superscary.heavyinventories.data.player.enc.Encumbered;
import superscary.heavyinventories.data.player.enc.OverEncumbered;
import superscary.heavyinventories.data.player.PlayerWeight;
import superscary.heavyinventories.data.weight.ItemWeight;
import superscary.heavyinventories.data.weight.WeightGenerator;

@SuppressWarnings("unused")
public class ModCodecs {

    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, HeavyInventories.MODID);

    public static final Codec<ItemWeight> ITEM_WEIGHT_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING.fieldOf("modid").forGetter(ItemWeight::modid),
                Codec.STRING.fieldOf("regName").forGetter(ItemWeight::registryName),
                Codec.STRING.fieldOf("readableName").forGetter(ItemWeight::readableName),
                Codec.DOUBLE.optionalFieldOf("weight", WeightGenerator.unbound()).forGetter(ItemWeight::weight)
        ).apply(instance, ItemWeight::new)
    );
    public static final StreamCodec<ByteBuf, ItemWeight> ITEM_WEIGHT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ItemWeight::modid,
            ByteBufCodecs.STRING_UTF8, ItemWeight::registryName,
            ByteBufCodecs.STRING_UTF8, ItemWeight::readableName,
            ByteBufCodecs.DOUBLE, ItemWeight::weight,
            ItemWeight::new
    );
    public static final Codec<PlayerWeight> PLAYER_WEIGHT_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("current").forGetter(PlayerWeight::currentWeight),
                    Codec.DOUBLE.fieldOf("max").forGetter(PlayerWeight::maxWeight)
            ).apply(instance, PlayerWeight::new)
    );
    public static final StreamCodec<ByteBuf, PlayerWeight> PLAYER_WEIGHT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, PlayerWeight::currentWeight,
            ByteBufCodecs.DOUBLE, PlayerWeight::maxWeight,
            PlayerWeight::new
    );
    public static final Codec<Encumbered> ENCUMBERED_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("encumbered").forGetter(Encumbered::encumbered)
            ).apply(instance, Encumbered::new)
    );
    public static final StreamCodec<ByteBuf, Encumbered> ENCUMBERED_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, Encumbered::encumbered,
            Encumbered::new
    );
    public static final Codec<OverEncumbered> OVER_ENCUMBERED_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("over_encumbered").forGetter(OverEncumbered::overEncumbered)
            ).apply(instance, OverEncumbered::new)
    );
    public static final StreamCodec<ByteBuf, OverEncumbered> OVER_ENCUMBERED_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, OverEncumbered::overEncumbered,
            OverEncumbered::new
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemWeight>> ITEM_WEIGHT_COMPONENT = REGISTRY.register("item_weight",
            () -> DataComponentType.<ItemWeight>builder()
                    .persistent(ITEM_WEIGHT_CODEC)
                    .networkSynchronized(ITEM_WEIGHT_STREAM_CODEC)
                    .build()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<PlayerWeight>> PLAYER_WEIGHT_COMPONENT = REGISTRY.register("player_weight",
            () -> DataComponentType.<PlayerWeight>builder()
                    .persistent(PLAYER_WEIGHT_CODEC)
                    .networkSynchronized(PLAYER_WEIGHT_STREAM_CODEC)
                    .build()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Encumbered>> ENCUMBERED_COMPONENT = REGISTRY.register("encumbered",
            () -> DataComponentType.<Encumbered>builder()
                    .persistent(ENCUMBERED_CODEC)
                    .networkSynchronized(ENCUMBERED_STREAM_CODEC)
                    .build()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<OverEncumbered>> OVER_ENCUMBERED_COMPONENT = REGISTRY.register("over_encumbered",
            () -> DataComponentType.<OverEncumbered>builder()
                    .persistent(OVER_ENCUMBERED_CODEC)
                    .networkSynchronized(OVER_ENCUMBERED_STREAM_CODEC)
                    .build()
    );

}
