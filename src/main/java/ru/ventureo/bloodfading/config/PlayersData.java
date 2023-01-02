/*
 * Copyright (C) 2023 Vasiliy Stelmachenok
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
package ru.ventureo.bloodfading.config;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayersData extends AbstractConfig {
    private final static String KEY = "playersDisabled";
    private List<String> toggled;

    public PlayersData(Plugin plugin) {
        super(plugin, "data.yml");
    }

    public boolean togglePlayer(Player player) {
        return isDisabled(player) ? toggled.remove(player.getName())
                : !toggled.add(player.getName());
    }

    public boolean isDisabled(Player player) {
        return toggled.contains(player.getName());
    }

    @Override
    public void save() {
        getConfig().set(KEY, toggled);
        super.save();
    }

    @Override
    public void load() {
        super.load();
        this.toggled = getConfig().getStringList(KEY);
    }
}
