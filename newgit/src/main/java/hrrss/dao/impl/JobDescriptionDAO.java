package hrrss.dao.impl;

import hrrss.model.IJobDescription;
import hrrss.model.impl.JobDescription;

import java.util.List;

import org.hibernate.Query;

public class JobDescriptionDAO extends AbstractDAO<JobDescription> {

    @SuppressWarnings("unchecked")
    public List<IJobDescription> loadAllJobDescriptionByEmployer(String username) {

	Query query = getCurrentSession().getNamedQuery(
		"loadAllJobDescriptionByUsername");
	query.setParameter("username", username);

	return query.list();
    }
    
    @SuppressWarnings("unchecked")
    public  List<IJobDescription> getJobById(Long id) {

	Query query = getCurrentSession().getNamedQuery("getJobById");
	query.setParameter("id", id);

	return query.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<IJobDescription> loadAllJobDescriptionById(Long id) {

	Query query = getCurrentSession().getNamedQuery("loadAllJobDescriptionByEmployerId");
	query.setParameter("id", id);

	return query.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<IJobDescription> loadAllJobDescription() {
    	Query query = getCurrentSession().getNamedQuery("loadAllJobDescription");
    	
    	return query.list();
    }
    
}
