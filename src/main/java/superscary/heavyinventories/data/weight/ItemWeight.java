package superscary.heavyinventories.data.weight;

/**
 * Item weight definitions
 * @param modid String of the mod registering the item
 * @param registryName String form of the item registry name, ex. "cobblestone"
 * @param readableName String form of the item name, ex "Cobblestone"
 * @param weight the {@link Double} value that is returned after generation
 */
public record ItemWeight(String modid, String registryName, String readableName, double weight) {
}
