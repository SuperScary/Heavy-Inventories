package superscary.heavyinventories.data.util;

import superscary.heavyinventories.data.Weights;
import superscary.heavyinventories.data.weight.ItemWeight;

import java.util.ArrayList;

public record Mod (String modid, Weights weights, ArrayList<ItemWeight> itemWeights) {
}
