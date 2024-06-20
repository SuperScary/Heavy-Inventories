# Item Weights

Item weights are stored as java records. Each record is built at the end of the Loading Phase to capture all known items 
through the ModFinder.java class. 

```java
/**
 * Item weight definitions
 * @param modid String of the mod registering the item
 * @param registryName String form of the item registry name, ex. "cobblestone"
 * @param readableName String form of the item name, ex "Cobblestone"
 * @param weight the {@link Double} value that is returned after generation
 */
public record ItemWeight(String modid, String registryName, String readableName, double weight) {
    
}
```

| Type   | Name         | Purpose                                       |
|--------|--------------|-----------------------------------------------|
| String | modid        | The modid of the mod that owns the item.      |
| String | registryName | The registry name of the item.                |
| String | readableName | The localized name of the item, server based. |
| Double | weight       | The weight of the item as a double.           |

The values from each ItemWeight record are what are initially written to the [modid].json file.
