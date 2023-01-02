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
package ru.ventureo.bloodfading.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ventureo.bloodfading.BloodFadingPlugin;

public class BloodFadingToggleCommand implements CommandExecutor {
    private final BloodFadingPlugin plugin;

    public BloodFadingToggleCommand(BloodFadingPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This commnad is working only for players");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("bloodfading.toggle")) {
            player.sendMessage(plugin.getConfiguration().messageNoPermission());
            return true;
        }

        if (plugin.getPlayersData().togglePlayer(player)) {
            player.sendMessage(plugin.getConfiguration().messageOnEnable());
        } else {
            player.sendMessage(plugin.getConfiguration().messageOnDisable());
        }
        return true;
    }
}
