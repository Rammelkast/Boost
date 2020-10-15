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
package com.rammelkast.boost.api.util;

import java.util.Arrays;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * @author ProtocolSupport
 * @see https://github.com/ProtocolSupport/ProtocolSupport/blob/90d18049e42cb266c0db13c6cf8d8441b9db57a7/src/protocolsupport/protocol/utils/ProtocolVersionsHelper.java#L143
 */
public class ProtocolVersionsHelper {

	public static final ProtocolVersion LATEST_PC = ProtocolVersion.getLatest(ProtocolType.PC);

	public static final ProtocolVersion[] UP_1_6 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] UP_1_8 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] UP_1_9 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] UP_1_10 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_10);

	public static final ProtocolVersion[] UP_1_11 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_11);

	public static final ProtocolVersion[] UP_1_12 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] UP_1_13 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_13);

	public static final ProtocolVersion[] UP_1_14 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_14);

	public static final ProtocolVersion[] UP_1_15 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_15);

	public static final ProtocolVersion[] UP_1_16 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_16);

	public static final ProtocolVersion[] UP_1_16_2 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_16_2);

	public static final ProtocolVersion[] DOWN_1_4_7 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_4_7);

	public static final ProtocolVersion[] DOWN_1_5_2 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_5_2);

	public static final ProtocolVersion[] DOWN_1_6_4 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_6_4);

	public static final ProtocolVersion[] DOWN_1_7_5 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_6_4);

	public static final ProtocolVersion[] DOWN_1_7_10 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_10);

	public static final ProtocolVersion[] DOWN_1_8 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] DOWN_1_9 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] DOWN_1_9_4 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_9_4);

	public static final ProtocolVersion[] DOWN_1_10 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_10);

	public static final ProtocolVersion[] DOWN_1_11 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_11);

	public static final ProtocolVersion[] DOWN_1_11_1 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_11_1);

	public static final ProtocolVersion[] DOWN_1_12_2 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] DOWN_1_13 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_13);

	public static final ProtocolVersion[] DOWN_1_13_2 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] DOWN_1_14_4 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_14_4);

	public static final ProtocolVersion[] DOWN_1_15_2 = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_15_2);

	public static final ProtocolVersion[] ALL_PC = ProtocolVersion.getAllBetween(ProtocolVersion.getOldest(ProtocolType.PC), LATEST_PC);

	public static final ProtocolVersion[] ALL_1_15 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_15, ProtocolVersion.MINECRAFT_1_15_2);

	public static final ProtocolVersion[] ALL_1_14 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_14, ProtocolVersion.MINECRAFT_1_14_4);

	public static final ProtocolVersion[] ALL_1_13 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] ALL_1_12 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] ALL_1_9 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] ALL_1_7 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10);

	public static final ProtocolVersion[] ALL_1_6 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_6_4);

	public static final ProtocolVersion[] RANGE__1_16__1_16_1 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_16, ProtocolVersion.MINECRAFT_1_16_1);

	public static final ProtocolVersion[] RANGE__1_14__1_15_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_14, ProtocolVersion.MINECRAFT_1_15_2);

	public static final ProtocolVersion[] RANGE__1_14_1__1_14_4 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_14_1, ProtocolVersion.MINECRAFT_1_14_4);

	public static final ProtocolVersion[] RANGE__1_12__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_11_1__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_11_1__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_11__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_11__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_9__1_10 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_10);

	public static final ProtocolVersion[] RANGE__1_9__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_9__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_8__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_8__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_7_10__1_15_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_15_2);

	public static final ProtocolVersion[] RANGE__1_7_5__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_10__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_10__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_6__1_8 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] RANGE__1_6__1_7 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] RANGE__1_7_5__1_8 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] RANGE__1_8__1_9 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] RANGE__1_6__1_10 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_10);

	protected static final Int2ObjectOpenHashMap<ProtocolVersion> byOldProtocolId = new Int2ObjectOpenHashMap<>();
	protected static final Int2ObjectOpenHashMap<ProtocolVersion> byNewProtocolId = new Int2ObjectOpenHashMap<>();


	static {
		Arrays.stream(ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_6_4)).forEach(version -> byOldProtocolId.put(version.getId(), version));
		Arrays.stream(ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_7_5)).forEach(version -> byNewProtocolId.put(version.getId(), version));
	}

	public static ProtocolVersion getOldProtocolVersion(int protocolid) {
		ProtocolVersion version = byOldProtocolId.get(protocolid);
		return version != null ? version : ProtocolVersion.MINECRAFT_LEGACY;
	}

	public static ProtocolVersion getNewProtocolVersion(int protocolid) {
		ProtocolVersion version = byNewProtocolId.get(protocolid);
		return version != null ? version : ProtocolVersion.MINECRAFT_FUTURE;
	}

}