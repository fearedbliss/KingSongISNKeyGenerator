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

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("King Song 4.0 ISN Key Generator - 1.0.3");
		System.out.println("Jonathan Vasquez (fearedbliss) <jon@xyinn.org>");
		System.out.println("2-BSD\n");

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