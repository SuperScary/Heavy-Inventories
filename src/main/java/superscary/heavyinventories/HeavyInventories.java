package superscary.heavyinventories;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface HeavyInventories {

    String MODID = "heavyinventories";
    String NAME = "Heavy Inventories";

    static HeavyInventories instance () {
        return ModBase.INSTANCE;
    }

    static ResourceLocation getResource (String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    static ResourceLocation getMinecraftResource (String name) {
        return ResourceLocation.withDefaultNamespace(name);
    }

    @Nullable
    Level getClientLevel ();

    @Nullable
    MinecraftServer getCurrentServer ();

}
