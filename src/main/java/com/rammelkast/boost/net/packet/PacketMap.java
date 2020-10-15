/*
 * Copyright (c) 2020 Marco Moesman ("Rammelkast")
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.rammelkast.boost.net.packet;

import java.util.HashMap;
import java.util.Map;

import com.rammelkast.boost.net.ConnectionState;
import com.rammelkast.boost.net.packet.impl.PacketHandshake;
import com.rammelkast.boost.net.packet.impl.PacketPing;
import com.rammelkast.boost.net.packet.impl.PacketStatus;

public class PacketMap {

	// Status packets
	private static final Map<Integer, Class<? extends MinecraftPacket>> statusMap = new HashMap<Integer, Class<? extends MinecraftPacket>>();
	// Login packets
	private static final Map<Integer, Class<? extends MinecraftPacket>> loginMap = new HashMap<Integer, Class<? extends MinecraftPacket>>();

	static {
		// Register packets
		statusMap.put(0x00, PacketStatus.class);
		statusMap.put(0x01, PacketPing.class);
	}

	/**
	 * Gets a packet class by id for the given connection state
	 * @param state The current connection state
	 * @param packetId The id of the packet
	 * @return a class which extends {@link MinecraftPacket}
	 */
	private static Class<? extends MinecraftPacket> getPacketById(ConnectionState state, int packetId) {
		switch (state) {
		case NEW: {
			if (packetId != 0x00) {
				throw new IllegalArgumentException("Illegal packet Id (" + Integer.toHexString(packetId) + ") for state NEW");
			}
			return PacketHandshake.class;
		}
		case STATUS: {
			return statusMap.get(packetId);
		}
		case LOGIN: {
			return loginMap.get(packetId);
		}
		default: {
			throw new IllegalArgumentException("There is no packet map for state " + state.name());
		}
		}
	}
	
	/**
	 * Gets a {@link MinecraftPacket} for a given id and connection state
	 * @param state The current connection state
	 * @param packetId The id of the packet
	 * @return A matching {@link MinecraftPacket}
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static MinecraftPacket getPacket(ConnectionState state, int packetId) throws InstantiationException, IllegalAccessException {
		Class<? extends MinecraftPacket> clazz = getPacketById(state, packetId);
		// Check if the packet exists in the packet map
		if(clazz != null) {
			return clazz.newInstance();
		}
		return null;
	}
}
