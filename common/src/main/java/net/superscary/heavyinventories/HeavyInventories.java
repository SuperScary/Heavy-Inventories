package net.superscary.heavyinventories;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public interface HeavyInventories {

	String MOD_ID = "heavyinventories";
	String MOD_NAME = "Heavy Inventories";
	Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    static HeavyInventories getInstance() {
        return ModBase.INSTANCE;
    }

    Collection<ServerPlayer> getPlayers();

    Level getClientLevel();

    MinecraftServer getCurrentServer();
}