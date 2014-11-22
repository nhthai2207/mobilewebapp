package com.mobileweb.dao.impl;

import org.springframework.stereotype.Repository;

import com.mobileweb.model.User;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Integer> {

}
