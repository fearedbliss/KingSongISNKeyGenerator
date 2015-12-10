//  Copyright 2015 Jonathan Vasquez <jvasquez1011@gmail.com>
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

package com.vasquez;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("King Song 4.0 ISN Key Generator - 1.0.1");
		System.out.println("By Jonathan Vasquez (fearedbliss) <jvasquez1011@gmail.com>");
		System.out.println("Released under the Apache License 2.0\n");

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