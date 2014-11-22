package com.mobileweb.dao.impl;

import org.springframework.stereotype.Repository;

import com.mobileweb.model.SessionUser;

@Repository("sessionDao")
public class SessionDaoImpl extends GenericDaoImpl<SessionUser, String> {
}
