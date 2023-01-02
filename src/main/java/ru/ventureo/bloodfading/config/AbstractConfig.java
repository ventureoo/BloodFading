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
package ru.ventureo.bloodfading.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AbstractConfig {
    private final FileConfiguration config;
    private final File file;
    private final Plugin plugin;

    public AbstractConfig(Plugin plugin, String name) {
        this.plugin = plugin;
        this.config = new YamlConfiguration();
        this.file = new File(plugin.getDataFolder(), name);
    }

    protected FileConfiguration getConfig() {
        return this.config;
    }

    private void create() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(file.getName(), false);
        }
    }

    public void load() {
        create();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning("Can't load the plugin configuration: " + e.getMessage());
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Can't save the plugin configuration: " + e.getMessage());
        }
    }
}
