# BloodFading

Just a plugin for Minecraft (Bukkit), which creates a blood effect on
the edges of the player's screen without mods when he takes damage.
Supports all server versions from 1.8 to 1.18+.

## Demo

![Demo](demo.gif)

## How it work?

The plugin sends each damaged player a fake packet (via ProtocolLib)
that he exceeded the warning distance of the barrier world in which he
is. Fake packet distance is calculated relative to the player's
distance from the center of the barrier, so no matter where the player
is in the world the effect should be the same (However, in relative
proximity to the barrier the blood effect may be slightly slower than
in the center of the barrier). The plugin doesn't really change the
size of the barrier or its center, it only sends the player
information about the warning distance.

**ATTENTION: The effect on the edges of the screen does not appear on
fast graphics settings. This is a limitation of the client of
Minecraft, which can not be bypassed.**

## Configuration

Starting with version 0.4 you can configure the behavior of the
plugin. An example of the default configuration:

```
# Selects the fading mode. Accepts the following values:
#  default (Default) - The fading occurs equal to the amount of time set by the variable interval.
#  health - Fading occurs depending on the interval and the value of the remaining health of the player.
#           The lower the health of the player, the longer and more pronounced the effect.
#  damage - Fading occurs depending on the interval and the damage received by the player.
#           The more damage the player received, the longer and more pronounced the effect.
mode: default

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

# Messages to be output by the bloodfading command
messages:
  onEnable: "&cYou have enabled the first-person blood effect."
  onDisable: "&cYou have disabled the first-person blood effect."
  noPermission: "&aYou don't have permission for this"
```

If you change the `interval`, I recommend changing it along with the
`coefficent` to make the fading look smooth to the player.

## Commands

For players who want to stay on Fancy graphics settings, but do not
want to see the effect on the edges of the screen there is a command
``/bloodfading`` (alias ``/bf``) to disable the effect. The command
only works for players who have the ``bloodfading.toggle`` permission.

The names of players who wish to disable the effect will be stored in
the ``data.yml`` in the plugin directory.

## Requirements

- Java 8+
- Requires the latest ProtocolLib to support the latest versions of Minecraft.

.. vim:set textwidth=70:
