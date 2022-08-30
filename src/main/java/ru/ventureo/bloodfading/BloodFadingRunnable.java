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

import java.util.Map;

import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class BloodFadingRunnable implements Runnable {
    private final BloodFadingPlugin plugin;

    public BloodFadingRunnable(BloodFadingPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Map.Entry<Player, Integer> entry : plugin.getPlayers().entrySet()) {
            Player player = entry.getKey();
            WorldBorder border = player.getWorld().getWorldBorder();
            int minDistance = (int) (border.getSize() / 2 - player.getLocation().distance(border.getCenter()));
            Integer distance = entry.getValue();
            plugin.getPacketSender().fading(player, distance);
            distance = (int) (distance * plugin.getConfiguration().getCoefficient());
            entry.setValue(distance);

            // Fixes a potential memory leak
            if (!player.isOnline()) plugin.getPlayers().remove(player);

            if (minDistance >= distance || player.isDead()) {
                plugin.getPlayers().remove(player);
                plugin.getPacketSender().fading(player, border.getWarningDistance());
            }
        }
    }
}
