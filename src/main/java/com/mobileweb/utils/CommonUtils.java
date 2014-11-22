package com.mobileweb.utils;

public class CommonUtils {
	public static boolean isEmptyString(String s) {
		if (null == s || "".equals(s.trim()))
			return true;
		return false;
	}
}
