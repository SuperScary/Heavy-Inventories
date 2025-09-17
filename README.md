# Heavy Inventories

Heavy Inventories adds a realistic inventory weight system to Minecraft. Inspired by RPG games like TES, this mod adds a
new layer of immersion to the game. Each item now has a weight associated with it, with effects on the players mobility,
health, and stamina.

***

## How it works

Heavy Inventories use to generate a weight file for each item in the game, namespaced by the owning mods modid. This is
no
longer the case. Each weight is calculated based on its crafting recipe, with a minimum weight of 0.1 (float).

### Overriding weights

Weights for each item can be overridden by adding a [modid].json file in the weights folder located at the root of your
installation.

For example, to override cobblestone's weight from 0.1, create a minecraft.json file in the weights folder with the
following contents:

```json
{
  "cobblestone": {
    "weight": 10.0
  }
}
```
This can also be done via in-game commands. While holding the item you want to change the weight of, run command:

> /heavyinventories set weight <number>
> 
> The number can be an integer (10, 20, 30, etc) or a decimal (10.0, 20.0, 30.0, etc).

## New features coming in Heavy Inventories 4
We have removed much of the old logic of the old mod and have completely rewritten it into a modern system. This should
help with performance and stability.

### Whats changed?

#### **Pumping Iron**
Pumping iron use to be a mechanism that would allow a player to increase their carry weight be either finding a barbell or
using an anvil. This has been removed completely.

#### **Automatic weight file generation**
The weight file generation has been removed completely. This saves on memory and CPU usage, as well as boasts performance
by not requiring the mod to check the file system for every item weight that is not stored in the cache.

#### **Weight caching mechanism**
The weight caching system as been reworked to the point where it is basically no longer the same system. The caching system
works dynamically with the RAM for a seamless experience. Both item weights and Player weights are cached and updated
dynamically.

#### **Density**
The density system has come to fruition. This system mainly affects entity items, and their ability to float in water / fall speed.
> Please note that this system is still in development and may not work as expected.

#### **Enchantments**
Heavy Inventories added enchantments to be more inline with the existing content of the game to give a more immersive and 
seamless experience.

- **Bracing**: when applied to a chestplate, the player can increase their max carry weight by up to 100% their base.
- **Reinforced**: when applied to leggings, the player can increase their max carry weight by up to 25% of their base.
- **Surefooted**: when applied to boots, the slowness effect is reduced by up to 40% while encumbered.

#### **Potion effects**
The effects of Heavy Inventories can be mitigated by using certain potions.

- **Strength**: when applied to a player (while in effect), the upper limit of the players carry weight is changed to:
  - 100%-110% for normal encumbrance.
  - 115%-125% for over encumbrance.

#### GUI System
The GUI system has moved on from the original bar-type rendering to an on-screen text based system. Ideas are welcome for
improvements to the GUI.

#### **Mod loaders**
Heavy Inventories moved from being specific to Forge, to a more agnostic model, supporting NeoForge, Fabric, and Forge.

***

## Contributing
We are always looking for new contributors to help us improve this mod, especially with fresh new ideas and translations!
The source code is available on [GitHub](https://github.com/SuperScary/Heavy-Inventories).

## Mod Integration
Heavy Inventories does not specifically integrate with any other mods. We have done our best to ensure that Heavy Inventories
will work with other mods, but we cannot guarantee that it will work perfectly.