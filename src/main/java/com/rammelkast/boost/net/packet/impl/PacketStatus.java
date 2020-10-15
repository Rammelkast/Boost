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
import java.util.Arrays;

import org.json.JSONObject;

import com.rammelkast.boost.api.Boost;
import com.rammelkast.boost.api.util.ProtocolVersion;
import com.rammelkast.boost.net.NetServerConnection;
import com.rammelkast.boost.net.PacketDataWrapper;
import com.rammelkast.boost.net.packet.MinecraftPacket;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketStatus extends MinecraftPacket {

	private String response;

	@Override
	public byte getId() {
		return 0x00;
	}

	@Override
	public int getContentSize() {
		return (response == null ? 0 : getStringSize(response));
	}

	@Override
	public void read(PacketDataWrapper wrapper) throws IOException {
		// Client -> Proxy
		if (wrapper.readableBytes() <= 0) {
			return;
		}

		// Server -> Proxy
		this.response = wrapper.readString();
	}

	@Override
	public void write(PacketDataWrapper wrapper) throws IOException {
		// Proxy -> Server
		if (this.response == null) {
			return;
		}

		// Proxy -> Client
		wrapper.writeString(this.response);
	}

	@Override
	public void onReceive(NetServerConnection connection) {
		// Client -> Proxy
		if (this.response == null) {
			ProtocolVersion protocol = connection.clientProtocol;
			if (!Arrays.asList(Boost.getProxy().getSupportedVersions()).contains(protocol)) {
				protocol = Boost.getProxy().getSupportedVersions()[0];
			}
			connection.sendPacket(
					new PacketStatus(
							new JSONObject()
							.put("version", new JSONObject().put("name", "Boost")
									.put("protocol", protocol.getId()))
							.put("players", new JSONObject().put("max", 69)
									.put("online", 0))
							.put("description", Boost.getProxy().getMotd())
			.toString()));
			return;
		}
		
		// Server -> Proxy
		// TODO implement
	}

}
