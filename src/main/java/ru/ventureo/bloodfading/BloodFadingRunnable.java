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
import ru.ventureo.bloodfading.impl.PacketSender;

import java.util.HashMap;
import java.util.Map;

public class BloodFadingRunnable implements Runnable {

    private final PacketSender sender;
    private double coefficient;

    public BloodFadingRunnable(PacketSender sender, double coefficient) {
        this.sender = sender;
        this.coefficient = coefficient;
    }

    protected static Map<Player, Integer> players = new HashMap<>();

    @Override
    public void run() {
        for(Map.Entry<Player, Integer> entry: players.entrySet()) {
            Player player = entry.getKey();
            WorldBorder border = player.getWorld().getWorldBorder();
            int distanceCenter = (int) player.getLocation().distance(border.getCenter());
            int borderSize = (int) border.getSize() / 2;
            int minDistance = borderSize - distanceCenter;
            Integer distance = entry.getValue();
            sender.fading(player, distance);
            distance = (int) (distance * coefficient);
            entry.setValue(distance);
            if (minDistance >= distance) {
                players.remove(entry.getKey());
                sender.fading(player, border.getWarningDistance());
            }
        }
    }
}
