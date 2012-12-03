package org.dchan.orm;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

/**
 */
public abstract class Persistent_Model {
	private final Log log;

	/**
	 * Gets the Logger in the Constructor
	 */
	public Persistent_Model() {
		log = LogFactory.getLog(this.getClass());
	}

	private final SessionFactory sessionFactory = getSessionFactory();

	/**
	 * This method depedenc on the HibernateHelper to obtain the SessionFactory
	 * Model
	
	 * @return Session Factory */
	protected SessionFactory getSessionFactory() {
		try {
			return HbHelperSingle.getInstance().getSessionFactory();
		} catch (Exception e) {
			log.error("Could not locate SessionFactory", e);
			throw new IllegalStateException("Could not locate SessionFactory");
		}
	}

	public void persist() {
		log.debug("persisting "+this.getClass()+" instance");
		try {
			sessionFactory.getCurrentSession().persist(this);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty() {
		log.debug("attaching dirty "+this.getClass()+"  instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(this);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean() {
		log.debug("attaching clean "+this.getClass()+"  instance");
		try {
			sessionFactory.getCurrentSession().lock(this, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete() {
		log.debug("deleting "+this.getClass()+"  instance");
		try {
			sessionFactory.getCurrentSession().delete(this);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Method merge.
	 * @return Persistent_Model
	 */
	public Persistent_Model merge() {
		log.debug("merging "+this.getClass()+"  instance");
		try {
			Persistent_Model result = (Persistent_Model) sessionFactory.getCurrentSession()
					.merge(this);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/**
	 * Method findById.
	 * @param id java.lang.Integer
	 * @return Persistent_Model
	 */
	public Persistent_Model findById(java.lang.Integer id) {
		log.debug("getting "+this.getClass()+"  instance with id: " + id);
		try {
			Persistent_Model instance = (Persistent_Model) sessionFactory.getCurrentSession()
					.get("app.model."+this.getClass()+"", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Method findByExample.
	 * @param instance Persistent_Model
	 * @return List<Persistent_Model>
	 */
	public List<Persistent_Model> findByExample(Persistent_Model instance) {
		log.debug("finding "+instance.getClass().getName()+"  instance by example");
		try {
			List<Persistent_Model> results = sessionFactory.getCurrentSession()
					.createCriteria(instance.getClass().getName())
					.add(HbHelperSingle.getInstance().createExample(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
