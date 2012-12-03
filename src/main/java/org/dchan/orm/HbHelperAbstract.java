package org.dchan.orm;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.proxy.HibernateProxy;

/**
 * This is the backbone of persistence in the Application.
 * {@link HbHelperAbstract} provides the following features
 * 
 * <ul>
 * <li>Can get the SessionFactory for advance functions outside of the helper</li>
 * <li>A commit method to persist the session to the database</li>
 * <li>Method to begin a new transaction</li>
 * <li>Creation of queries and allowing query cache</li>
 * <li>Getter for the current transaction of the factory</li>
 * <li>A global get method for all entities</li>
 * <li>Creation of an example for creation</li>
 * <li>Clear the cache of the whole application</li>
 * <li>Global merge for detached object</li>
 * <li>Global evict method</li>
 * </ul>
 * 
 * Important point to note here is that {@link HbHelperAbstract} before was
 * created in a Static basis. But in now it's extended to two subclasses
 * 
 * @author Dchan(Dulitha Rasanga Wijewantha)
 * 
 * @version 2.0
 */
public abstract class HbHelperAbstract {

	static SessionFactory factory;

	/**
	 * Getter for the SessionFactory
	 * 
	 * 
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return factory;
	}

	/**
	 * This method is to be called when the Session Factory needs to be
	 * refreshed
	 */
	public abstract void startFactory();

	/**
	 * This method handles the exceptional cases
	 */
	public RuntimeException commit(boolean hook) {
		try {
			getTranscation().commit();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			getTranscation().rollback();
			if (hook) {
			}
			throw e;
		}
	}

	public RuntimeException commit() {
		return commit(true);
	}

	/**
	 * This method creates a new Transactions to work with the database but take
	 * note that this method doesn't affect the database. Only when the
	 * transaction is committed the database is affected
	 */
	public void beginTranscation() {
		getSessionFactory().getCurrentSession().beginTransaction();
	}

	/**
	 * This method creates a Query object from a string and begins a transaction
	 * to work with the database. This also set the querie's cache mode to true
	 * 
	 * @param s
	 *            - The HQL query string
	 * 
	 * @return the Query object
	 */
	public Query createQuery(String s) {
		beginTranscation();
		Query query = getCurrentSession().createQuery(s);
		query.setCacheable(true);
		return query;
	}

	/**
	 * A global get method to get anytype of object from the database.
	 * 
	 * @param c
	 *            - The class of the object
	 * @param id
	 *            - The identity value of the object
	 * 
	 * @return - the requested Object
	 */
	public Object get(Class c, Integer id) {
		beginTranscation();
		return getCurrentSession().get(c, id);

	}

	/**
	 * Getter for the current Session
	 * 
	 * 
	 * @return Session
	 */
	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * Getter for the current Transaction
	 * 
	 * 
	 * @return Transaction
	 */
	public Transaction getTranscation() {
		return getCurrentSession().getTransaction();
	}

	/**
	 * This method creates an example from the object sent and gives an example
	 * object to be used for Criteria Queries
	 * 
	 * @param instance
	 *            - The Object that needs to be created the example from
	 * 
	 * @return - The example object
	 */
	public Example createExample(Object instance) {
		return Example.create(instance).ignoreCase()
				.enableLike(MatchMode.START);
	}

	/**
	 * Used to evict a cache region if specified... Useful for applications for
	 * long run
	 * 
	 * @param cacheRegion
	 */
	public void clearQuery(String cacheRegion) {
		beginTranscation();
		getSessionFactory().evictQueries(cacheRegion);
		commit();
	}

	/**
	 * This method is used to clear the Cache of the Hibernate Session factory
	 */
	public void clearCache() {
		beginTranscation();
		getCurrentSession().clear();
		getSessionFactory().evictQueries();
		try {
			Map<String, ClassMetadata> classesMetadata = getSessionFactory()
					.getAllClassMetadata();
			for (String entityName : classesMetadata.keySet()) {
				getSessionFactory().evictEntity(entityName);
			}
		} catch (Exception e) {
		}
		commit();
	}

	/**
	 * Used to evict an object our of Hibernate
	 * 
	 * @param obj
	 */
	public void evict(Object obj) {
		getCurrentSession().evict(obj);
	}

	/**
	 * When there is multiple sessions involved in Hibernate an object of
	 * previous session needs to be merged with the object in the current
	 * session.
	 * 
	 * 
	 * 
	 * @param obj
	 *            Object
	 * @return Merged object
	 */
	public Object merge(Object obj) {
		return getCurrentSession().merge(obj);
	}

	/**
	 * This method encapsulates the NamedQueries in Hibernate. We can send in
	 * the Parameter list with key, value list and this method would
	 * automatically map them to the named query
	 * 
	 * 
	 * 
	 * 
	 * @param string
	 *            String
	 * @param parameterList
	 *            Object[]
	 * @return Query instance
	 */
	public Query getNameQuery(String string, Object... parameterList) {
		beginTranscation();
		Query namedQuery = getCurrentSession().getNamedQuery(string);
		for (int i = 0; i < parameterList.length; i++) {
			Object key = parameterList[i];
			Object value = parameterList[++i];
			if (value instanceof String) {
				namedQuery.setString(key.toString(), value.toString());
			} else if (value instanceof Short) {
				namedQuery.setShort(key.toString(), (Short) value);
			} else if (value instanceof Byte) {
				namedQuery.setByte(key.toString(), (Byte) value);
			} else if (value instanceof Persistent_Model) {
				namedQuery.setEntity(key.toString(), value);
			} else if (value instanceof Date) {
				namedQuery.setDate(key.toString(), (Date) value);
			} else if (value instanceof Integer) {
				namedQuery.setInteger(key.toString(), (Integer) value);
			}
		}
		return namedQuery;
	}

	/**
	 * Transaction unmanaged Save bulk method to save a set of objects
	 * 
	 * @param set
	 */
	public void saveBulk(Set set) {
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Persistent_Model object = (Persistent_Model) iterator.next();
			object.attachDirty();
		}
	}

	public <T> T initializeAndUnproxy(T entity) {
		if (entity == null) {
			throw new NullPointerException(
					"Entity passed for initialization is null");
		}
		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			entity = (T) ((HibernateProxy) entity)
					.getHibernateLazyInitializer().getImplementation();
		}
		return entity;
	}

	public void executeProcedure(String procedureName, Object... parameters) {
		StringBuffer query = new StringBuffer("CALL " + procedureName + "(");
		for (int i = 0; i < parameters.length; i++) {
			String string = parameters[i].toString();
			++i;
			query.append(":" + string + ",");
		}
		query.deleteCharAt(query.length() - 1);
		query.append(")");
		SQLQuery createSQLQuery = getCurrentSession().createSQLQuery(
				query.toString());
		for (int i = 0; i < parameters.length; i++) {
			Object object = parameters[i];
			if (object instanceof String) {
				createSQLQuery.setString(object.toString(),
						parameters[++i].toString());
			} else if (object instanceof Integer) {
				createSQLQuery.setInteger(object.toString(),
						Integer.valueOf(parameters[++i].toString()));
			}
		}
		createSQLQuery.executeUpdate();
	}

	/**
	 * Re-read the state of the given instance from the underlying database
	 * 
	 * @param obj
	 */
	public void refresh(Object obj) {
		getCurrentSession().refresh(obj);
	}
}
