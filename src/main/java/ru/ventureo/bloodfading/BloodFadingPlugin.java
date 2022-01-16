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

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ventureo.bloodfading.impl.PacketSender;
import ru.ventureo.bloodfading.impl.v1_16.ProtocolLibImpl;
import ru.ventureo.bloodfading.impl.v1_8.LegacyProtocolLibImpl;

public class BloodFadingPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        PacketSender packetSender = null;

        Server server = this.getServer();

        String version = server.getClass().getPackage().getName().replace(".", ",").split(",")[3];
        double subversion = Double.parseDouble(version.replace("v1_", "").replaceAll("_R", "."));

        if (protocolManager != null) {
            if (subversion > 16.0) {
                packetSender = new ProtocolLibImpl(protocolManager);
            } else {
                packetSender = new LegacyProtocolLibImpl(protocolManager);
            }
        } else {
            this.getLogger().warning("ProtocolLib is unavailable, stopping...");
            onDisable();
        }

        server.getPluginManager().registerEvents(new BloodFadingListener(), this);
        server.getScheduler().runTaskTimer(this, new BloodFadingRunnable(packetSender), 0L, 1L);
    }
}
