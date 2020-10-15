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
package com.rammelkast.boost.net.packet.impl;

import java.io.IOException;

import com.rammelkast.boost.net.ConnectionState;
import com.rammelkast.boost.net.NetServerConnection;
import com.rammelkast.boost.net.PacketDataWrapper;
import com.rammelkast.boost.net.handler.ConnectionHandler;
import com.rammelkast.boost.net.handler.impl.DefaultConnectionHandler;
import com.rammelkast.boost.net.packet.MinecraftPacket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PacketHandshake extends MinecraftPacket {

	private int protocolId;
	private String hostname;
	private short port;
	private int nextState;

	@Override
	public byte getId() {
		return 0x00;
	}

	@Override
	public int getContentSize() {
		return getVarIntSize(protocolId) + getStringSize(hostname) + getShortSize() + getVarIntSize(nextState);
	}

	@Override
	public void read(PacketDataWrapper wrapper) throws IOException {
		this.protocolId = wrapper.readVarInt();
		this.hostname = wrapper.readString();
		this.port = wrapper.readShort();
		this.nextState = wrapper.readVarInt();
	}

	@Override
	public void write(PacketDataWrapper wrapper) throws IOException {
		wrapper.writeVarInt(this.protocolId);
		wrapper.writeString(this.hostname);
		wrapper.writeShort(this.port);
		wrapper.writeVarInt(this.nextState);
	}

}
