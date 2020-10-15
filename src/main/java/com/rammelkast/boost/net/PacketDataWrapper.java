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
package com.rammelkast.boost.net;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PacketDataWrapper {

	@Getter
	private final ByteBuf buffer;

	public int readableBytes() {
		return this.buffer.readableBytes();
	}

	public void markReaderIndex() {
		this.buffer.markReaderIndex();
	}

	public void resetReaderIndex() {
		this.buffer.resetReaderIndex();
	}

	public byte readByte() {
		return this.buffer.readByte();
	}

	public int readVarInt() {
		int out = 0;
		int bytes = 0;
		byte in;
		while (true) {
			in = this.buffer.readByte();

			out |= (in & 0x7F) << (bytes++ * 7);

			if (bytes > 5) {
				throw new RuntimeException("VarInt too big");
			}

			if ((in & 0x80) != 0x80) {
				break;
			}
		}
		return out;
	}

	public void discardSomeReadBytes() {
		this.buffer.discardSomeReadBytes();
	}

	public void readBytes(byte[] compressedPacket) {
		this.buffer.readBytes(compressedPacket);
	}

	public void writeVarInt(int value) {
		int part;
		while (true) {
			part = value & 0x7F;

			value >>>= 7;
			if (value != 0) {
				part |= 0x80;
			}

			this.buffer.writeByte(part);

			if (value == 0) {
				break;
			}
		}
	}

	public void writeBytes(ByteBuf temporarilyBuf) {
		this.buffer.writeBytes(temporarilyBuf);
	}

	public void writeBytes(ByteBuf buffer, int i, int dataSize) {
		this.buffer.writeBytes(buffer, i, dataSize);
	}

	public void writeBytes(byte[] uncompressedPacket) {
		this.buffer.writeBytes(uncompressedPacket);
	}

	public String readString() {
		int len = this.readVarInt();

		byte[] b = new byte[len];
		this.readBytes(b);

		try {
			return new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Error("Could not decode from UTF-8", e);
		}
	}

	public void writeString(String s) {
		byte[] b = null;
		try {
			b = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Error("Could not encode to UTF-8", e);
		}
		writeVarInt(b.length);
		this.writeBytes(b);
	}

	public short readShort() {
		return this.buffer.readShort();
	}

	public void writeShort(int value) {
		this.buffer.writeShort(value);
	}

	public void writeBytes(byte[] publicKeyBytes, int i, int length) {
		this.buffer.writeBytes(publicKeyBytes, i, length);
	}

	public float readFloat() {
		float f = this.buffer.readFloat();
		if (Float.isNaN(f)) {
			return 0F;
		}
		return f;
	}

	public long readLong() {
		return this.buffer.readLong();
	}

	public int writerIndex() {
		return this.buffer.writerIndex();
	}

	public void ensureWritable(int i) {
		this.buffer.ensureWritable(i);
	}

	private void readerIndex(int index) {
		this.buffer.readerIndex(index);
	}

	private int readerIndex() {
		return this.buffer.readerIndex();
	}

	public boolean readBoolean() {
		return this.buffer.readBoolean();
	}

	public int readUnsignedByte() {
		return this.buffer.readUnsignedByte();
	}

	public double readDouble() {
		double value = this.buffer.readDouble();
		if (Double.isNaN(value)) {
			return 0.0D;
		}
		return value;
	}

	public void readBytes(byte[] data, int i, int length) {
		this.buffer.readBytes(data, i, length);
	}

	public void writeByte(int value) {
		this.buffer.writeByte(value);
	}

	public void writeLong(long l) {
		this.buffer.writeLong(l);
	}

	public void writeInt(int i) {
		this.buffer.writeInt(i);
	}

	public void writeBoolean(boolean bool) {
		this.buffer.writeBoolean(bool);
	}

	public void writeFloat(float value) {
		this.buffer.writeFloat(value);
	}

	public void writeUUID(UUID uuid) {
		this.writeLong(uuid.getMostSignificantBits());
		this.writeLong(uuid.getLeastSignificantBits());
	}

	public void writeDouble(double value) {
		this.buffer.writeDouble(value);
	}

}
