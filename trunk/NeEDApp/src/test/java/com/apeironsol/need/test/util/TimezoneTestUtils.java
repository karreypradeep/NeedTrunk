package com.apeironsol.need.test.util;

import java.util.TimeZone;

public class TimezoneTestUtils {

	public static void main(String[] args) {
		
	
		
		for (String id : TimeZone.getAvailableIDs()) {
			if(id.indexOf("Asia") > -1) {
				System.out.println(id);
			}
			
		};

	}

}
