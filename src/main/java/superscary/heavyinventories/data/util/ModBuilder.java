package superscary.heavyinventories.data.util;

import superscary.heavyinventories.util.ModFinder;

import java.util.ArrayList;

public class ModBuilder {

    public static void build () {
        ArrayList<Mod> modList = ModFinder.findAllMods();
        for (var mod : modList) {
            if (mod.itemWeights().isEmpty() || DisallowedMods.contains(mod.modid())) return;
            mod.weights().write(mod.itemWeights());
        }
    }

}
