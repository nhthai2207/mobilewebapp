package com.mobileweb.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface GenericDao<E, K> {

	void add(E entity);

	void update(E entity);

	void remove(E entity);

	E find(K key);

	List<E> list();

	void delete(K key);

	List<E> list(String whereCondition);

	E getMaxObject(Criterion expression, String filed);

	<T> List<T> find(Collection<? extends Serializable> ids);

	List<E> exeQuery(String hql);

}
