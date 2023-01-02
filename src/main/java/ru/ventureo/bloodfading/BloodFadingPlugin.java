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
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import ru.ventureo.bloodfading.commands.BloodFadingToggleCommand;
import ru.ventureo.bloodfading.config.PlayersData;
import ru.ventureo.bloodfading.config.PluginConfiguration;
import ru.ventureo.bloodfading.listeners.BloodFadingListener;
import ru.ventureo.bloodfading.scheduler.BloodFadingRunnable;
import ru.ventureo.bloodfading.packets.PacketSender;
import ru.ventureo.bloodfading.packets.v1_17.ProtocolLibImpl;
import ru.ventureo.bloodfading.packets.v1_8.LegacyProtocolLibImpl;

public class BloodFadingPlugin extends JavaPlugin {
    private final Map<Player, Integer> players = new ConcurrentHashMap<>();
    private final PluginConfiguration configuration = new PluginConfiguration(this);
    private final PlayersData data = new PlayersData(this);
    private PacketSender packetSender;

    @Override
    public void onEnable() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        if (protocolManager == null) {
            getLogger().warning("ProtocolLib is unavailable, stopping...");
            setEnabled(false);
        }

        setPackerSender(protocolManager);
        configuration.load();
        data.load();

        getCommand("bloodfading").setExecutor(new BloodFadingToggleCommand(this));
        getServer().getPluginManager().registerEvents(new BloodFadingListener(this), this);
        getServer().getScheduler().runTaskTimer(this, new BloodFadingRunnable(this), 0L, 1L);
    }

    @Override
    public void onDisable() {
        data.save();
    }

    public Map<Player, Integer> getPlayers() {
        return players;
    }

    public PluginConfiguration getConfiguration() {
        return configuration;
    }

    public PlayersData getPlayersData() {
        return data;
    }

    private void setPackerSender(ProtocolManager manager) {
        String version = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        double subversion = Double.parseDouble(version.replace("v1_", "").replaceAll("_R", "."));

        if (subversion >= 17.0) {
            packetSender = new ProtocolLibImpl(manager);
        } else if (subversion >= 8.0) {
            packetSender = new LegacyProtocolLibImpl(manager);
        } else {
            throw new UnsupportedOperationException("Your Minecraft version is unsupported by this plugin");
        }
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }
}
