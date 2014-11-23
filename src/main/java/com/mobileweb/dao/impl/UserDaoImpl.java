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

	// 0: Ok, 1: email exist , 2: username exist
	public int isUserExist(String email, String username) {
		String keys[] = { "email", "userName" };
		String value[] = { ServiceUtils.addDoubleQuote(email),  ServiceUtils.addDoubleQuote(username)};
		Operand operand[] = { Operand.EQ, Operand.EQ };
		Arrays.asList(keys);
		String whereCondition = HQLUtils.buildQuery(Arrays.asList(keys), Arrays.asList(value), Arrays.asList(operand), HQLUtils.Operand.OR);
		List<User> list = this.list(whereCondition);
		if (list != null && list.size() > 0) {
			User user = list.get(0);
			if(user.getUsername().equals(username)){
				return 2;
			}else{
				return 1;
			}				
		}
		return 0;
	}

	// 0: Login Ok, 1: Wrong username or password, 2: Still not active
	public int isValidLogin(String username, String password) {
		String keys[] = { "userName", "password" };
		String value[] = { ServiceUtils.addDoubleQuote(username),  ServiceUtils.addDoubleQuote(password)};
		Operand operand[] = { Operand.EQ, Operand.EQ };
		Arrays.asList(keys);
		String whereCondition = HQLUtils.buildQuery(Arrays.asList(keys), Arrays.asList(value), Arrays.asList(operand), HQLUtils.Operand.OR);
		List<User> list = this.list(whereCondition);
		if (list != null && list.size() > 0) {
			User user = list.get(0);
			if(user.getIsActive()){
				return 0;
			}else{
				return 2;
			}				
		}
		return 1;
	}

}
