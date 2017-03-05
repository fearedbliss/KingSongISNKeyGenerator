/*
 * Copyright 2015-2017 Jonathan Vasquez <jon@xyinn.org>
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.vasquez;

public class KeyGenerator {
	public KeyGenerator() {
		serialNumber = new int[bluetoothAddressMaxSections];
		solution = new int[bluetoothAddressMaxSections];
	}

	public KeyGenerator(String isn) {
		this();
		PopulateSerialNumber(isn);
		this.isn = getIntegersToHexAsString(serialNumber);
	}

	private void PopulateSerialNumber(String isn) {
		String[] splitted = isn.split(",");

		for (int i = 0; i < bluetoothAddressMaxSections; i++) {
			serialNumber[i] = Integer.valueOf(String.valueOf(splitted[i]), 16);
		}
	}

	public String FindKey() {
		solution[0] = 255 - serialNumber[0];
		solution[2] = 255 - serialNumber[2];
		solution[4] = 255 - serialNumber[4];
		solution[5] = (solution[0] + solution[2] + solution[4]) & 0xFF;

		System.out.println("Device : " + isn);

		for (int i = 0; i < bluetoothAddressMaxSections - 1; i += 2) {
			if (!doesValueMatchCorrectlyWithAnyInDeviceSerial(solution[i])) {
				// No Key Found
				return null;
			}
		}

		return getIntegersToHexAsString(solution);
	}

	// If this value plus the sum of any serial number add up to 255, then we
	// are good to go.
	private boolean doesValueMatchCorrectlyWithAnyInDeviceSerial(int value) {
		for (int i = 0; i < bluetoothAddressMaxSections; i++) {
			if (value + serialNumber[i] == 255) {
				return true;
			}
		}
		return false;
	}

	private String getIntegersToHexAsString(int[] values) {
		StringBuilder solutionAsHex = new StringBuilder(values.length * 3);
		String seperator = ":";

		for (int i = 0; i < values.length; i++) {
			solutionAsHex.append(String.format("%02x", values[i]));
			if (i < values.length - 1) {
				solutionAsHex.append(seperator);
			}
		}

		return solutionAsHex.toString().toUpperCase();
	}

	private int[] serialNumber;
	private int[] solution;
	private final int bluetoothAddressMaxSections = 6;
	private String isn;
}
