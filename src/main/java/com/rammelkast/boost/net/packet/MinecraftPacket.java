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

import java.io.IOException;

import com.rammelkast.boost.net.NetServerConnection;
import com.rammelkast.boost.net.PacketDataWrapper;

public abstract class MinecraftPacket {

	/**
	 * The packet id in VarInt form
	 * @return the packet id
	 */
	public abstract byte getId();
	
	/**
	 * The size of the packets contents
	 * @return the size of the contents
	 */
	public abstract int getContentSize();

	/**
	 * Reads the packet contents from the wrapper
	 * @param wrapper The wrapper that has the packet contents
	 * @throws IOException
	 */
	public abstract void read(PacketDataWrapper wrapper) throws IOException;

	/**
	 * Writes the packet contents to the wrapper
	 * @param wrapper The wrapper to write to
	 * @throws IOException
	 */
	public abstract void write(PacketDataWrapper wrapper) throws IOException;
	
	/**
	 * Called when the packet has been fully read
	 * @param connection The connection that received the packet
	 */
	public void onReceive(NetServerConnection connection) {}
	
	/**
	 * Called when the packet has been sent
	 * @param connection The connection that sent the packet
	 */
	public void onSend(NetServerConnection connection) {}
	
	public static int getStringSize(String string) {
		int total;
		try {
			total = 0;
			total += getVarIntSize(string.length());
			total += string.getBytes("UTF-8").length;
		} catch (Exception exception) {
			total = -1;
		}
		return total;
	}

	public static int getVarIntSize(int value) {
		int total = 0;
		while (true) {
			value >>>= 7;
			total++;
			if (value == 0) {
				break;
			}
		}
		return total;
	}

	public static int getFloatSize() {
		return 4;
	}

	public static int getIntSize() {
		return 4;
	}

	public static int getShortSize() {
		return 2;
	}

	public static int getLongSize() {
		return 8;
	}

	public static int getDoubleSize() {
		return 8;
	}

	public static int getUUIDSize() {
		return 16;
	}
	
	public void writeToWrapper(PacketDataWrapper wrapper) throws IOException {
		int writerIndex = wrapper.writerIndex();
		int id = getId();
		int packetSize = getContentSize() + getVarIntSize(id);
		int expectedSize = getVarIntSize(packetSize);
		int totalSize = expectedSize + packetSize;
		wrapper.ensureWritable(totalSize);

		wrapper.writeVarInt(packetSize);
		wrapper.writeVarInt(id);
		write(wrapper);

		int newIndex = wrapper.writerIndex();
		if (writerIndex + totalSize != newIndex) {
			throw new RuntimeException("!!! Invalid send packet !!! " + "\nExcepted size: " + totalSize + " " + "\nReal size:" + (wrapper.writerIndex() - writerIndex) + " " + "\nPacket: " + this.toString());
		}
	}
	
}
