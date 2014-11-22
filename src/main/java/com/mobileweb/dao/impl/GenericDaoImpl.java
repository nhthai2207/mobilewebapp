package com.mobileweb.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mobileweb.dao.GenericDao;
import com.mobileweb.utils.ServiceUtils;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {

	// private SessionFactory sessionFactory;
	protected Class<? extends E> daoType;
	private SessionFactory sessionFactory = null;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session currentSession() {
		return getSessionFactory().openSession();
	}

	@Override
	public void add(E entity) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();
	}

	@Override
	public void update(E entity) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(entity);
		session.getTransaction().commit();
	}

	@Override
	public void remove(E entity) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

	@Override
	public void delete(K key) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(session.get(daoType, key));
		session.getTransaction().commit();
	}

	@Override
	public E find(K key) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings({ "unchecked" })
		E object = (E) session.get(daoType, key);
		session.getTransaction().commit();
		return object;
	}

	@Override
	public List<E> list() {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings({ "unchecked" })
		List<E> object = session.createCriteria(daoType).list();
		session.getTransaction().commit();
		return object;
	}

	@Override
	public List<E> list(String whereCondition) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		String hql = "";
		if (ServiceUtils.isEmptyString(whereCondition)) {
			hql = String.format("from %s", daoType.getName());
		} else {
			hql = String.format("from %s where %s", daoType.getName(), whereCondition);
		}
		Query query = session.createQuery(hql);
		@SuppressWarnings({ "unchecked" })
		List<E> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	@Override
	public List exeQuery(String hql) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		@SuppressWarnings({ "unchecked" })
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(Collection<? extends Serializable> ids) {
		Session session = getSessionFactory().openSession();
		String idPropertyName = session.getSessionFactory().getClassMetadata(daoType).getIdentifierPropertyName();
		Criteria criteria = session.createCriteria(daoType).add(Restrictions.in(idPropertyName, ids));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getMaxObject(Criterion expression, String filed) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(daoType);
		criteria.add(expression);
		criteria.addOrder(Order.desc(filed)).setMaxResults(1);
		List<E> list = criteria.list();
		session.getTransaction().commit();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}
