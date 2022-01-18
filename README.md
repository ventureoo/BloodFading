# BloodFading

Just a plugin for Minecraft (Bukkit), which creates a blood effect on the edges of the player's screen without mods when he takes damage. Supports all server versions from 1.8 to 1.18+. 

## Demo

![Demo](demo.gif)

## How it work?

The plugin sends each damaged player a fake packet (via ProtocolLib) that he exceeded the warning distance of the barrier world in which he is. Fake packet distance is calculated relative to the player's distance from the center of the barrier, so no matter where the player is in the world the effect should be the same (However, in relative proximity to the barrier the blood effect may be slightly slower than in the center of the barrier). The plugin doesn't really change the size of the barrier or its center, it only sends the player information about the warning distance.

**ATTENTION: The effect on the edges of the screen does not appear on fast graphics settings. This is a limitation of the client of Minecraft, which can not be bypassed.**

## Configuration

Starting with version 0.4 you can configure the behavior of the plugin. An example of the default configuration:

```
# The fading time for the player.
# A value of 3 roughly corresponds to one second of real time.
#
# Default value: 6 (~2 seconds)
interval: 6
# The coefficient of speed with which the animation (fake distance) decreases at the edges.
# The smaller the coefficient, the faster the animation (fading).
# Accepts a value in decimal fraction from 0.1 to 0.9 inclusive.
# Values greater than or equal to one are not accepted.
#
# Default value: 0.95
coefficient: 0.95
```

If you change the `interval`, I recommend changing it along with the `coefficent` to make the fading look smooth to the player.

## Requirements

Can work on versions of Java 8+ and requires the latest ProtocolLib to support the latest versions of Minecraft.
