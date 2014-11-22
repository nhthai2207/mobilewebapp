package com.mobileweb.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mobileweb.model.User;
import com.mobileweb.utils.HQLUtils;
import com.mobileweb.utils.HQLUtils.Operand;
import com.mobileweb.utils.ServiceUtils;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Integer> {

	public User getUserByRegistKey(String key) {
		String keys[] = { "confirmKey" };
		String value[] = { ServiceUtils.addDoubleQuote(key) };
		Operand operand[] = { Operand.EQ };
		Arrays.asList(keys);
		String whereCondition = HQLUtils.buildQuery(Arrays.asList(keys), Arrays.asList(value), Arrays.asList(operand), HQLUtils.Operand.AND);
		List<User> list = this.list(whereCondition);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
