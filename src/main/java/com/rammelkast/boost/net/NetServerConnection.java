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

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Queue;

import com.rammelkast.boost.api.Boost;
import com.rammelkast.boost.api.util.ProtocolVersion;
import com.rammelkast.boost.net.handler.ConnectionHandler;
import com.rammelkast.boost.net.handler.impl.DefaultConnectionHandler;
import com.rammelkast.boost.net.packet.MinecraftPacket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.util.ReferenceCountUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class NetServerConnection extends ChannelInboundHandlerAdapter {

	@Getter
	private final Channel channel;
	private final Queue<MinecraftPacket> packetQueue = new LinkedList<MinecraftPacket>();
	
	public ProtocolVersion clientProtocol;
	
	@Getter
	@Setter
	private ConnectionHandler handler = new DefaultConnectionHandler();
	// The name of the client
	// Before login this is the IP address
	// After login this is the username
	@Getter
	private String clientName;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Ensure we have a connection handler
		assert (this.handler != null);

		// Set first clientName
		this.clientName = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().toString();

		// Let the connection handler do the rest
		this.handler.onConnect();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// Ensure we have a connection handler
		assert (this.handler != null);

		// Let the connection handler do the rest
		this.handler.onDisconnect();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// Ensure we meet required conditions
		assert (this.handler != null && msg instanceof MinecraftPacket);

		try {
			MinecraftPacket packet = (MinecraftPacket) msg;
			packet.onReceive(this);
			// TODO handle packet
		} finally {
			ReferenceCountUtil.release(msg);
			this.flushPackets();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// We don't care about this when the channel is inactive
		if (!this.channel.isActive()) {
			return;
		}

		if (cause instanceof ReadTimeoutException) {
			// Client timed out
			// TODO debug, later on stop sending this before login
			Boost.getLogger().warn("{} timed out", clientName);
		} else {
			// More severe issues
			Boost.getLogger().error(cause);
		}

		ctx.close();
	}

	public void sendPacket(MinecraftPacket... packets) {
		if (!this.channel.isActive()) {
			return;
		}
		synchronized (packets) {
			if (!this.channel.isActive()) {
				return;
			}
			for (MinecraftPacket packet : packets) {
				this.packetQueue.offer(packet);
			}
		}
		this.flushPackets();
	}
	
	public void flushPackets() {
		synchronized (this.packetQueue) {
			MinecraftPacket packet;
			while ((packet = this.packetQueue.poll()) != null) {
				this.channel.write(packet);
				packet.onSend(this);
			}
			this.channel.flush();
		}
	}
}
