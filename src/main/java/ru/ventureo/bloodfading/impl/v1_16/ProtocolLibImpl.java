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
package ru.ventureo.bloodfading.impl.v1_16;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;
import ru.ventureo.bloodfading.impl.PacketSender;

import java.lang.reflect.InvocationTargetException;

public class ProtocolLibImpl implements PacketSender {
    private final ProtocolManager protocolManager;

    public ProtocolLibImpl(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    @Override
    public void fading(Player player, int distance) {
        PacketContainer fakeDistance = new PacketContainer(PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE);
        fakeDistance.getIntegers().write(0, distance);
        try {
            protocolManager.sendServerPacket(player, fakeDistance);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + fakeDistance, e);
        }
    }
}
