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
package com.rammelkast.boost.api;

import org.apache.logging.log4j.Logger;

import com.rammelkast.boost.api.util.ProtocolVersion;

public interface Proxy {

	/**
	 * The proxy console logger
	 * @return the console logger
	 */
	public Logger getLogger();
	
	/**
	 * The port to which the proxy is bound
	 * Must be an available TCP port
	 * @return the port to which the proxy is bound
	 */
	public int getPort();
	
	/**
	 * The amount of connection slots
	 * This determines how many clients can connect
	 * @return the amount of connection slots
	 */
	public int getSlots();
	
	/**
	 * The "message of the day" to display after a status request
	 * @return the "message of the day"
	 */
	public String getMotd();
	
	/**
	 * Get all supported Minecraft versions for this build of Boost
	 * @return an array of supported Minecraft versions
	 */
	public ProtocolVersion[] getSupportedVersions();
	
	/**
	 * The maximum timeout in seconds before disconnecting a client
	 * @return the maximum timeout in seconds
	 */
	public int getMaxTimeout();
	
	/**
	 * Get the build version id for this build of Boost
	 * @return the build version id
	 */
	public int getBuild();
	
	/**
	 * Starts the proxy
	 */
	public void start();
	
	/**
	 * Shuts down the proxy
	 */
	public void stop();
	
}
