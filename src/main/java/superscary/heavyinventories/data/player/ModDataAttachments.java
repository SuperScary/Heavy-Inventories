package superscary.heavyinventories.data.player;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import superscary.heavyinventories.HeavyInventories;
import superscary.heavyinventories.codec.ModCodecs;
import superscary.heavyinventories.data.player.enc.Encumbered;
import superscary.heavyinventories.data.player.enc.OverEncumbered;

import java.util.function.Supplier;

public class ModDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, HeavyInventories.MODID);

    public static final Supplier<AttachmentType<PlayerWeight>> PLAYER_WEIGHT = TYPES.register("player_weight",
            () -> AttachmentType.builder(() -> new PlayerWeight(0, 1200)).serialize(ModCodecs.PLAYER_WEIGHT_CODEC).build()
    );
    public static final Supplier<AttachmentType<Encumbered>> ENCUMBERED = TYPES.register("encumbered",
            () -> AttachmentType.builder(() -> new Encumbered(false)).serialize(ModCodecs.ENCUMBERED_CODEC).build()
    );
    public static final Supplier<AttachmentType<OverEncumbered>> OVER_ENCUMBERED = TYPES.register("over_encumbered",
            () -> AttachmentType.builder(() -> new OverEncumbered(false)).serialize(ModCodecs.OVER_ENCUMBERED_CODEC).build()
    );

}
