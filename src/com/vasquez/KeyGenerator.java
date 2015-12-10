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

public class KeyGenerator {
	public KeyGenerator()
	{
		serialNumber = new int[bluetoothAddressMaxSections];
	}
	
	public KeyGenerator(String Isn)
	{
		this();
		PopulateSerialNumber(Isn);
	}
	
	private void PopulateSerialNumber(String Isn)
	{
		String[] splitted = Isn.split(",");
		
		for(int i = 0; i < bluetoothAddressMaxSections; i++)
		{
			serialNumber[i] = Integer.valueOf(String.valueOf(splitted[i]), 16);
		}
	}
	
	public String FindKey()
	{
		int value1 = 0; // 0
		int value2 = 0;
	    int value3 = 0; // 2
	    int value4 = 0;
	    int value5 = 0; // 4
	    int checksum = 0; //value 6 aka checksum
	    
	    System.out.println("Calculating key for " + getIntegersToHexAsString(serialNumber[0], serialNumber[1], serialNumber[2], serialNumber[3], serialNumber[4], serialNumber[5]) + " ...\n");
		System.out.println("Please be patient. This can take a long time.\n");

		for (checksum = 0; checksum < 255; checksum++) {
			for (value5 = 0; value5 < 255; value5++) {
				for (value4 = 0; value4 < 255; value4++) {
					for (value3 = 0; value3 < 255; value3++) {
						for (value2 = 0; value2 < 255; value2++) {
							for (value1 = 0; value1 < 255; value1++) {
								int potential = value1 + value2 + value3 + value4 + value5;
								int checksumed = potential & 0xff;
								
								//We got a potential checksum match
								if (checksumed == checksum) {
									//Now see if it qualifies the other requirements [0, 2, 4] = 255
									
									int test = doesValueMatchCorrectlyWithAnyInDeviceSerial(value1);
									if (test == -1) continue;
									
									test = doesValueMatchCorrectlyWithAnyInDeviceSerial(value3);
									if (test == -1) continue;
									
									test = doesValueMatchCorrectlyWithAnyInDeviceSerial(value5);
									if (test == -1) continue;
									
									return getIntegersToHexAsString(value1,value2,value3,value4,value5,checksum);
								}
							}
						}
					}
				}
			}
		}
		
		// No Key Found
		return null;
	}
		
	// If this value plus the sum of any serial number add up to 255, then we are good to go.
	private int doesValueMatchCorrectlyWithAnyInDeviceSerial(int value)
	{
	    for(int i = 0; i < bluetoothAddressMaxSections; i++)
	    {
	        if(value + serialNumber[i] == 255) {
	            return value;
	        }
	    }
	    return -1;
	}

	private String getIntegersToHexAsString(int value1, int value2, int value3, int value4, int value5, int checksum)
	{
	    String solutionAsHex = "";
	    String seperator = ":";
	    
	    solutionAsHex = String.format("%02x", value1);
	    solutionAsHex = solutionAsHex + seperator + String.format("%02x", value2);
	    solutionAsHex = solutionAsHex + seperator + String.format("%02x", value3);
	    solutionAsHex = solutionAsHex + seperator + String.format("%02x", value4);
	    solutionAsHex = solutionAsHex + seperator + String.format("%02x", value5);
	    solutionAsHex = solutionAsHex + seperator + String.format("%02x", checksum);
	    
	    return solutionAsHex.toUpperCase();
	}
	
	private int[] serialNumber;
	private final int bluetoothAddressMaxSections = 6;
}
