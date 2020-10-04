package com.pms.payroll.util;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
public class Constants {
	public static String USER = "user";
	public static int ADMIN = 1;
	public static String SESSION = "session";
	public static Map<String, Integer> getAllowanceMap(){
		Map<String, Integer> allowanceMap = new HashMap();
		allowanceMap.put("A", 25000);
		allowanceMap.put("B", 20000);
		allowanceMap.put("C", 15000);
		allowanceMap.put("D", 10000);
		return allowanceMap;
	}
	
}
