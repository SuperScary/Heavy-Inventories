# Class Structure

## Launch classes
| Class Name            | Type      | Function                                                                                  |
|-----------------------|-----------|-------------------------------------------------------------------------------------------|
| HeavyInventories.java | Interface | Contains base logic for all classes to use.                                               |
| ModBootstrap.java     | Class     | Differentiates the modded environment between client and server.                          |
| ModClient.java        | Class     | The client sided logic of the mod.                                                        |                               
| ModServer.java        | Class     | The server sided logic of the mod.                                                        |
| ModBase.java          | Class     | The common sided logic of the mod. Both ModClient and Mod Server inherit from this class. |

The ModBootstrap class is seen by NeoForge as the main class file since it contains the @Mod annotation. The sole function of 
this class is to delegate whether the mod is in a client environment or server environment and uses a switch to load
either the Client or Server. It is done this way so there is no interpolating conflicts between sides.

```java
package superscary.heavyinventories;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(HeavyInventories.MODID)
public class ModBootstrap {

    public ModBootstrap (IEventBus modEventBus, ModContainer modContainer) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new ModClient(modEventBus, modContainer);
            case DEDICATED_SERVER -> new ModServer(modEventBus, modContainer);
        }
    }

}
```

## Data Flow
ModBootstrap is the first to be called by the mod launcher, which it then differentiates between a modded client or server.
The client and server java files inherit from the abstract class ModBase which creates the common logic needed to create
data files.

 ```mermaid
    flowchart LR;
        ModBootstrap.java--> |Client| ModClient.java;
        ModBootstrap.java--> |Server| ModServer.java;
        ModClient.java-->Client_Logic[Client Logic];
        ModServer.java<-->ModBase.java;
        ModServer.java-->Server_Logic[Server Logic];
        ModClient.java<-->ModBase.java;
        ModBase.java-->Common_Logic[Common Logic];
 ```
