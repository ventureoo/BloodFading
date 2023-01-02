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
package ru.ventureo.bloodfading.listeners;

import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import ru.ventureo.bloodfading.BloodFadingPlugin;
import ru.ventureo.bloodfading.FadingType;

public class BloodFadingListener implements Listener {
    private final BloodFadingPlugin plugin;

    public BloodFadingListener(BloodFadingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (plugin.getPlayersData().isDisabled(player))
            return;

        WorldBorder border = player.getWorld().getWorldBorder();
        int fakeDistance = (int) (border.getSize() / 2 - player.getLocation().distance(border.getCenter()))
                * plugin.getConfiguration().getInterval();

        if (plugin.getConfiguration().getMode() == FadingType.DAMAGE) {
            fakeDistance = (int) (fakeDistance * event.getDamage());
        } else if (plugin.getConfiguration().getMode() == FadingType.HEALTH) {
            // We use the deprecated API because we want to keep compatibility with older
            // versions
            int health = (int) (player.getMaxHealth() - player.getHealth());
            health = (health > 0) ? health : 1;
            fakeDistance = fakeDistance * health;
        }

        plugin.getPlayers().put(player, Math.abs(fakeDistance));
    }
}
