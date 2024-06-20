package superscary.heavyinventories.util;

import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
import superscary.heavyinventories.data.util.Mod;
import superscary.heavyinventories.data.Weights;

import java.util.ArrayList;

public class ModFinder {

    public static ArrayList<Mod> findAllMods () {
        ArrayList<Mod> mods = new ArrayList<>();
        for (IModInfo info : ModList.get().getMods()) {
            String modid = info.getModId();
            mods.add(new Mod(modid, new Weights(modid), ItemFinder.findModItems(modid)));
        }
        return mods;
    }

}
