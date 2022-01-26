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

import org.bukkit.plugin.Plugin;
import ru.ventureo.bloodfading.FadingType;

public class PluginConfiguration extends ConfigurationManager {
    private final Plugin plugin;

    public PluginConfiguration(Plugin plugin, String name) {
        super(plugin, name);
        this.plugin = plugin;
    }

    public double getCoefficient() {
        double coefficient = super.getConfig().getDouble("coefficient", 0.95);
        if (coefficient >= 1) {
            coefficient = 0.95;
            plugin.getLogger().warning("You selected the wrong coefficient value, which is greater than or equal to one." +
                    "The coefficient is set to the default value of 0.95.");
        }
        return coefficient;
    }

    public FadingType getMode() {
        FadingType mode;
        String type = super.getConfig().getString("mode", "default");
        switch (type) {
            case "health":
                mode = FadingType.HEALTH;
                break;
            case "damage":
                mode = FadingType.DAMAGE;
                break;
            default:
                mode = FadingType.DEFAULT;
        }
        return mode;
    }

    public int getInterval() {
        return super.getConfig().getInt("interval", 6);
    }
}
