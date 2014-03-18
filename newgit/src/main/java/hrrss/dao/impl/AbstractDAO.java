package hrrss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// @Component("AbstractDAO")
@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class AbstractDAO<T extends Serializable> {

    private Class<T> clazz;

    // @Autowired
    private SessionFactory remoteServerSessionFactory;

    public SessionFactory getRemoteServerSessionFactory() {
	return remoteServerSessionFactory;
    }

    public void setRemoteServerSessionFactory(
	    SessionFactory remoteServerSessionFactory) {
	this.remoteServerSessionFactory = remoteServerSessionFactory;
    }

    public void setClazz(final Class<T> clazzToSet) {
	clazz = clazzToSet;
    }

    @SuppressWarnings("unchecked")
    public T findOne(final Long id) {
	return (T) getCurrentSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
	return getCurrentSession().createQuery("from " + clazz.getName())
		.list();
    }

    public void save(final T entity) {
	getCurrentSession().persist(entity);
    }

    public void update(final T entity) {
	getCurrentSession().merge(entity);
    }

    public void delete(final T entity) {
	getCurrentSession().delete(entity);
    }

    public void deleteById(final Long entityId) {
	final T entity = findOne(entityId);
	delete(entity);
    }

    protected Session getCurrentSession() {
	return remoteServerSessionFactory.getCurrentSession();
    }
}