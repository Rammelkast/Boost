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
package com.rammelkast.boost.net.codec;

import java.util.List;

import com.rammelkast.boost.api.util.ProtocolVersion;
import com.rammelkast.boost.api.util.ProtocolVersionsHelper;
import com.rammelkast.boost.net.ConnectionState;
import com.rammelkast.boost.net.NetServerConnection;
import com.rammelkast.boost.net.PacketDataWrapper;
import com.rammelkast.boost.net.handler.impl.DefaultConnectionHandler;
import com.rammelkast.boost.net.packet.MinecraftPacket;
import com.rammelkast.boost.net.packet.PacketMap;
import com.rammelkast.boost.net.packet.impl.PacketHandshake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MinecraftDecoder extends MessageToMessageDecoder<ByteBuf> {

	private final NetServerConnection connection;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		PacketDataWrapper wrapper = new PacketDataWrapper(msg);
		while (wrapper.readableBytes() > 0) {
			wrapper.markReaderIndex();
			int readableBytes = wrapper.readableBytes();
			int packetLength = wrapper.readVarInt();
			// Invalid packet size
			if (readableBytes < packetLength) {
				wrapper.resetReaderIndex();
				return;
			}

			int packetId = wrapper.readVarInt();
			try {
				MinecraftPacket packet = PacketMap.getPacket(getState(), packetId);
				if (packet == null) {
					throw new Exception("Unknown packet");
				}
				
				packet.read(wrapper);
				
				// If this is a handshake packet we process it immediately
				if (packet instanceof PacketHandshake) {
					PacketHandshake handshake = (PacketHandshake) packet;
					// This should always be true for this situation
					assert (this.connection.getHandler() instanceof DefaultConnectionHandler);
					// Prevent illegal states
					int nextState = handshake.getNextState();
					if (nextState < 1 || nextState > 2) {
						ctx.disconnect();
						return;
					}
					
					this.connection.clientProtocol = ProtocolVersionsHelper.getNewProtocolVersion(handshake.getProtocolId());
					((DefaultConnectionHandler) this.connection.getHandler()).setState(ConnectionState.values()[nextState]);
				}
				
				out.add(packet);
			} catch (Exception exception) {
				throw new RuntimeException("Failed to decode packet: \nId: 0x" + Integer.toHexString(packetId)
						+ ", State: " + getState().toString() + ", Size: " + packetLength + "\nError: "
						+ exception.getMessage(), exception);
			}
			wrapper.discardSomeReadBytes();
		}
	}

	private ConnectionState getState() {
		return this.connection.getHandler().getState();
	}

}
