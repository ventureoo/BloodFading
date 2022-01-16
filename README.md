# BloodFading

Just a plugin for Minecraft (Bukkit), which creates a blood effect on the edges of the player's screen without mods when he takes damage. Supports all server versions from 1.8 to 1.18+. 

## Demo

![Demo](demo.gif)

## How it work?

The plugin sends each damaged player a fake packet (via ProtocolLib) that he exceeded the warning distance of the barrier world in which he is. Fake packet distance is calculated relative to the player's distance from the center of the barrier, so no matter where the player is in the world the effect should be the same (However, in relative proximity to the barrier the blood effect may be slightly slower than in the center of the barrier). The plugin doesn't really change the size of the barrier or its center, it only sends the player information about the warning distance.

**ATTENTION: The effect on the edges of the screen does not appear on fast graphics settings. This is a limitation of the client of Minecraft, which can not be bypassed.**

## Requirements

Can work on versions of Java 8+ and requires the latest ProtocolLib to support the latest versions of Minecraft.
