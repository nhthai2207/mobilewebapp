package com.mobileweb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServiceUtils {
	public static SimpleDateFormat feFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	public static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean isEmptyString(String s) {
		return s == null || "".equals(s) || "".equals(s.trim()) || "null".equals(s.trim());
	}

	public static Date convertFETimeToDate(String feTime) {
		Date date = null;
		try {
			date = feFormat.parse(feTime);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	
	public static String convertFETime(String feTime) {
		String ret = "";
		Date date;
		try {
			date = feFormat.parse(feTime);
			ret = dbFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String convertDateToDBTime(Date date) {
		String ret = "";
		try {
			ret = dbFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String convertDateToFE(Date date) {
		String ret = "";
		try {
			ret = feFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	
	public static String addDoubleQuote(Object s) {
		if (s != null) {
			return String.format("'%s'", s.toString());
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(addDoubleQuote(1));
		System.out.println(addDoubleQuote("a"));

		// System.out.println(HQLUtils.Operand.AND.toString());
		Date time = Calendar.getInstance().getTime();
		System.out.println(time.toString());

		System.out.println(time.getMonth() + "  " + time.getYear());
		int year = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println(year + "  " + Calendar.getInstance().MONTH);
	}
}
