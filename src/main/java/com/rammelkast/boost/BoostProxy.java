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
package com.rammelkast.boost;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rammelkast.boost.api.Proxy;
import com.rammelkast.boost.api.util.ProtocolVersion;
import com.rammelkast.boost.net.NetProxyServer;

import lombok.Getter;

public class BoostProxy implements Proxy {

	private static final Logger LOGGER = LogManager.getLogger("Boost");
	
	// TODO config for these values
	@Getter
	private int port = 25565;
	@Getter
	private int slots = 100;
	@Getter
	private String motd = "Powered by Boost (https://github.com/Rammelkast/Boost)";
	@Getter
	private int maxTimeout = 10;
	
	// Used to determine if the proxy is currently running
	private volatile boolean running = false;
	
	@Getter
	private NetProxyServer proxyServer;
	
	@Override
	public void start() {
		if (this.running) {
			throw new IllegalStateException("Cannot call start() because the proxy is already running");
		}
		
		// Set running to true before doing anything
		// Prevents double start() calls causing issues
		this.running = true;
		
		this.proxyServer = new NetProxyServer(this.port);
		this.proxyServer.run();
	}
	
	@Override
	public void stop() {
		if (!this.running) {
			throw new IllegalStateException("Cannot call stop() because the proxy is not running");
		}
		
		// Wait with setting running to false until after everything has shut down
		this.running = false;
	}

	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	@Override
	public ProtocolVersion[] getSupportedVersions() {
		return ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_16, ProtocolVersion.MINECRAFT_1_16_3);
	}

	@Override
	public int getBuild() {
		// TODO read
		return 1;
	}

}
