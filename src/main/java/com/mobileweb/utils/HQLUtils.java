package com.mobileweb.utils;

import java.util.List;

public class HQLUtils {
	public static enum Operand {
		GE(" >= "), EQ(" = "), LE(" <= "), AND(" AND "), OR(" OR "), IN(" IN ");
		public String value;

		Operand(String value) {
			this.value = value;
		}
	}

	public static String buildQuery(List<String> keys, List<String> value, List<Operand> operand, Operand between) {
		String ret = "";
		for (int i = 0; i < value.size(); i++) {
			if (!ServiceUtils.isEmptyString(value.get(i))) {
				ret = String.format("%s %s %s %s %s", ret, between.value, keys.get(i), operand.get(i).value, value.get(i));
			}
		}		
		if (ret.length() > 5) {
			ret = ret.substring(5);
		}
		return ret;

	}
}
