package superscary.heavyinventories.data.player.enc;

import net.minecraft.world.entity.player.Player;
import superscary.heavyinventories.data.player.PlayerWeight;

import java.util.ArrayList;

public class EncumbranceRegistry {

    private static final ArrayList<EncumbranceManager> managers = new ArrayList<>();

    public static EncumbranceManager getOrMake (Player player, PlayerWeight weight) {
        EncumbranceManager manager = new EncumbranceManager(player, weight);
        for (var man : managers) {
            if (man.equals(manager)) return man;
        }
        managers.add(manager);
        return manager;
    }

}
