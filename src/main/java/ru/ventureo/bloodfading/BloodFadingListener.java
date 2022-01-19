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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

public class BloodFadingListener implements Listener {

    private Map<Player, Integer> players;
    private int interval;
    private FadingType mode;

    public BloodFadingListener(Map<Player, Integer> players, int interval, FadingType mode) {
        this.players = players;
        this.interval = interval;
        this.mode = mode;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            WorldBorder border = player.getWorld().getWorldBorder();
            int offset = (int) player.getLocation().distance(border.getCenter());
            int distance = (int) (border.getSize() / 2 - offset);
            int fakeDistance = distance * interval;

            if (mode == FadingType.DAMAGE) {
                fakeDistance = (int) (fakeDistance * event.getDamage());
            } else if (mode == FadingType.HEALTH) {
                // We use the deprecated API because we want to keep compatibility with older versions
                int health = (int) (player.getMaxHealth() - player.getHealth());
                health = (health > 0) ? health : 1;
                fakeDistance = fakeDistance * health;
            }

            players.put(player, Math.abs(fakeDistance));
        }
    }
}
