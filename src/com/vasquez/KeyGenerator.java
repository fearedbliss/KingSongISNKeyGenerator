/*
 * Copyright 2015-2017 Jonathan Vasquez <jon@xyinn.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
