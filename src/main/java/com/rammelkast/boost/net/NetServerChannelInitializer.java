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

import com.rammelkast.boost.api.Boost;
import com.rammelkast.boost.net.codec.MinecraftDecoder;
import com.rammelkast.boost.net.codec.MinecraftEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NetServerChannelInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		final NetServerConnection connection = new NetServerConnection(ch);
		ch.pipeline()
			.addLast("read_timeout", new ReadTimeoutHandler(Boost.getProxy().getMaxTimeout()))
			.addLast("packet_decoder", new MinecraftDecoder(connection))
			.addLast("packet_encoder", new MinecraftEncoder())
			.addLast("packet_handler", connection);
		
	}

}
