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

package com.vasquez;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("King Song 4.0 ISN Key Generator - 1.0.4");
		System.out.println("Jonathan Vasquez (fearedbliss) <jon@xyinn.org>");
		System.out.println("GPLv3+\n");

		System.out.print("ISN # [Example: 11,22,33,44,55,66]: ");

		Scanner scanner = new Scanner(System.in);
		String Isn = scanner.next();

		System.out.println("");

		try {
			KeyGenerator keyGen = new KeyGenerator(Isn);
			String result = keyGen.FindKey();

			result = (result != null) ? result : "Not Found";
			System.out.println("Key    : " + result + "\n");

			if (result != null) {
				System.out.println("Enjoy and Ride Safely!");
			}
		} catch (Exception ex) {
			System.out.println("Error parsing the ISN number. Make sure you typed it correctly.");
		}

		scanner.close();
	}
}