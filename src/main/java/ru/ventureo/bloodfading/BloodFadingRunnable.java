/*
 * Copyright (C) 2022 Vasiliy Stelmachenok
 *
 * This file is part of BloodFading.
 *
 * BloodFading is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BloodFading is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BloodFading. If not, see <https://www.gnu.org/licenses/>.
 */
package ru.ventureo.bloodfading;

import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import ru.ventureo.bloodfading.config.PluginConfiguration;
import ru.ventureo.bloodfading.packets.PacketSender;

import java.util.Iterator;
import java.util.Map;

public class BloodFadingRunnable implements Runnable {

    private final Map<Player, Integer> players;
    private final PacketSender sender;
    private final PluginConfiguration config;

    public BloodFadingRunnable(Map<Player, Integer> players, PacketSender sender, PluginConfiguration config) {
        this.players = players;
        this.sender = sender;
        this.config = config;
    }

    @Override
    public void run() {
        for (Map.Entry<Player, Integer> entry: players.entrySet()) {
            Player player = entry.getKey();
            WorldBorder border = player.getWorld().getWorldBorder();
            int minDistance = (int) (border.getSize() / 2 - player.getLocation().distance(border.getCenter()));
            Integer distance = entry.getValue();
            sender.fading(player, distance);
            distance = (int) (distance * config.getCoefficient());
            entry.setValue(distance);

            if (minDistance >= distance || player.isDead()) {
                players.remove(player);
                sender.fading(player, border.getWarningDistance());
            }
        }
    }
}
