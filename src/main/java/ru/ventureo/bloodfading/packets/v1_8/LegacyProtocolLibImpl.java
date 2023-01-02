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
package ru.ventureo.bloodfading.packets.v1_8;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;
import ru.ventureo.bloodfading.packets.PacketSender;

import java.lang.reflect.InvocationTargetException;

import static com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction.SET_WARNING_BLOCKS;

public class LegacyProtocolLibImpl implements PacketSender {
    private final ProtocolManager protocolManager;

    public LegacyProtocolLibImpl(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    @Override
    public void fading(Player player, int distance) {
        PacketContainer fakeDistance = new PacketContainer(PacketType.Play.Server.WORLD_BORDER);
        fakeDistance.getWorldBorderActions().write(0, SET_WARNING_BLOCKS);
        fakeDistance.getIntegers().write(2, distance);
        try {
            protocolManager.sendServerPacket(player, fakeDistance);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + fakeDistance, e);
        }
    }
}
