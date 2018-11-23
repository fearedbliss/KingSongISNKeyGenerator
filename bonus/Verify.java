// Copyright (C) 2015-2018 Jonathan Vasquez <jon@xyinn.org>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see<https://www.gnu.org/licenses/>.

package Verify;

public class Verify {
	private static String MacAddress = "C4:BE:84:EF:1B:77";
	private static String answer = "88:58:10:00:10:00";

	private static int[] serialNumber;
	private static String[] serialString;

	public static void main(String[] args) {
		serialNumber = new int[6];
		serialString = new String[6];
		populateSerialData();

		System.out.println("Device Serial: " + getSerialString());
		System.out.println("Mac Address: " + MacAddress);
		System.out.println("Serial Number: " + getSerialNumber());

		boolean isValid = verify();
		if (isValid) {
			System.out.println("Valid Key!");
		} else {
			System.out.println("Invalid Key!");
		}
	}

	public static String getSerialNumber() {
		String result = "";

		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				result = String.valueOf(serialNumber[i]);
			} else {
				result = result + "-" + String.valueOf(serialNumber[i]);
			}
		}
		return result;
	}

	public static String getSerialString() {
		String result = "";

		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				result = serialString[i];
			} else {
				result = result + ":" + serialString[i];
			}
		}

		return result;
	}

	public static int[] populateSerialData() {
		String[] splitted = MacAddress.split(":");

		for (int i = 5; i >= 0; i--) {
			serialNumber[5 - i] = hexStringToInt(splitted[i]);
			serialString[5 - i] = splitted[i];
		}

		return serialNumber;
	}

	public static boolean verify() {
		// answer = authEditText
		String processedAnswer = answer.replace(":", "");
		System.out.println("Processing Key: " + answer);

		int end = 6;
		int integeredAnswer[] = new int[end]; // array that contains code as
												// integers
		// int i = 0; // ? seems like a counter

		boolean isValidSoFar = false; // flag1
		boolean isValid = false; // flag

		// converts and populates the answer into ints
		for (int i = 0; i < end; i++) {
			integeredAnswer[i] = hexStringToInt(processedAnswer.substring(
					i * 2, i * 2 + 2));
		}

		// L1
		isValid = isValidSoFar;

		// if the addition of the first 5 with 0x ff AND is not equal to the
		// last value, then fail
		int checksumBeforeAnd = integeredAnswer[0] + integeredAnswer[1]
				+ integeredAnswer[2] + integeredAnswer[3] + integeredAnswer[4];
		int checksum5 = integeredAnswer[5];
		int checksum = checksumBeforeAnd & 0xff;

		// System.out.println("Checksum Before And: " + checksumBeforeAnd);
		// System.out.println("Checksum value @ 5: " + checksum5);
		// System.out.println("Checksum: " + checksum);

		if (checksum != checksum5) {
			System.out.println("Checksum Failed.");
			return isValid;
		} else {
			System.out.println("Checksum Succeeded.");
		}

		// int k = 0; // maybe inner counter?
		// int counter = 0; // outer counter?

		// outer = i
		// inner = k
		for (int outer = 0; outer < 3; outer++) {
			// L5
			for (int inner = 0; inner < 6; inner++) {
				int j = outer;
				// L6
				// System.out.println("First Wave: IA[0] = " +
				// integeredAnswer[0] + " And Serial[" + inner + "] = " +
				// serialNumber[inner] + " Total: " + (integeredAnswer[0] +
				// serialNumber[inner]));
				if (integeredAnswer[0] + serialNumber[inner] == 255) {
					// System.out.println("IA[0] = " + integeredAnswer[0] +
					// " And Serial[" + inner + "] = " + serialNumber[inner] +
					// " Total: " + (integeredAnswer[0] + serialNumber[inner]));
					j = outer + 1;
					outer = j;
				}

				if (j >= 3) {
					// L13
					// System.out.println("Breaking j: " + j);
					break;
					// next part of the check
				}

				// System.out.println("First wave: j = " + j);
			}

			// Going to check the next wave
			// L8, L15
			for (int inner = 0; inner < 6; inner++) {
				int j = outer;
				// L6
				// System.out.println("Second Wave: IA[2] = " +
				// integeredAnswer[2] + " And Serial[" + inner + "] = " +
				// serialNumber[inner] + " Total: " + (integeredAnswer[2] +
				// serialNumber[inner]));
				if (integeredAnswer[2] + serialNumber[inner] == 255) {
					// System.out.println("IA[2] = " + integeredAnswer[2] +
					// " And Serial[" + inner + "] = " + serialNumber[inner] +
					// " Total: " + (integeredAnswer[2] + serialNumber[inner]));
					j = outer + 1;
					outer = j;
				}

				if (j >= 3) {
					// L13
					// System.out.println("Breaking j: " + j);
					break;
					// next part of the check
				}

				// System.out.println("Second wave: j = " + j);
			}

			// Last wave
			for (int inner = 0; inner < 6; inner++) {
				int j = outer;
				// L6
				// System.out.println("Second Wave: IA[4] = " +
				// integeredAnswer[2] + " And Serial[" + inner + "] = " +
				// serialNumber[inner] + " Total: " + (integeredAnswer[4] +
				// serialNumber[inner]));
				if (integeredAnswer[4] + serialNumber[inner] == 255) {
					// System.out.println("IA[4] = " + integeredAnswer[4] +
					// " And Serial[" + inner + "] = " + serialNumber[inner] +
					// " Total: " + (integeredAnswer[4] + serialNumber[inner]));
					j = outer + 1;
					outer = j;
				}

				if (j >= 3) {
					// L13
					// System.out.println("Breaking j: " + j);
					break;
					// next part of the check
				}

				// System.out.println("Second wave: j = " + j);
			}

			// L12
			if (outer < 3) {
				// System.out.println("We out with " + outer);
				break;
			}

			// L7
			isValid = true;
		}

		System.out.println("Verification Complete!");

		// _L4
		return isValid;
	}

	// Notes
	// if l1 true then L4
	// else L3

	// return false if the answer box is empty or null
	// ..

	// return false if the answer length is not = to 12 size
	// ..

	// _L16 sets flag to true

	public static int hexStringToInt(String s) {
		char minimumValue = '0';
		char maximumValue = '9';
		char minimumHex = 'a';
		char maximumHex = 'f';
		char minimumCapitalHex = 'A';
		char maximumCapitalHex = 'F';

		int finalValue = 0; // k

		// counter = j
		// l = salt?
		// s = "1B";
		for (int counter = 0; counter < s.length(); counter++) {
			int salt = 0;
			int newValue = 0;
			char current = s.charAt(counter);
			// System.out.println("Current Char: " + current);

			if (minimumValue > current || current > maximumValue) {
				// if in hex range [A - F]
				// System.out.println("Another game");

				if (minimumHex <= current && current <= maximumHex) {
					newValue = current - 87;
				} else {
					newValue = salt;
					if (minimumCapitalHex <= current) {
						newValue = salt;
						if (current <= maximumCapitalHex) {
							newValue = current - 55;
						}
					}
				}
			} else {
				// if in regular range [0-9]
				newValue = current - 48;
			}
			// System.out.println("New value: " + newValue);

			if (counter > 0) {
				salt = 1;
			} else {
				salt = 16;
			}

			finalValue += salt * newValue;

			// System.out.println("Final Value so far: " + finalValue);

		}

		return finalValue;
	}
}
