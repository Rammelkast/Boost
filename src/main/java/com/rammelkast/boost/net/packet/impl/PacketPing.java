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

import com.rammelkast.boost.net.NetServerConnection;
import com.rammelkast.boost.net.PacketDataWrapper;
import com.rammelkast.boost.net.packet.MinecraftPacket;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPing extends MinecraftPacket {

	private long timestamp;
	
	@Override
	public byte getId() {
		return 0x01;
	}

	@Override
	public int getContentSize() {
		return getLongSize();
	}

	@Override
	public void read(PacketDataWrapper wrapper) throws IOException {
		this.timestamp = wrapper.readLong();
	}

	@Override
	public void write(PacketDataWrapper wrapper) throws IOException {
		wrapper.writeLong(this.timestamp);
	}

	@Override
	public void onReceive(NetServerConnection connection) {
		connection.sendPacket(this);
	}
}
