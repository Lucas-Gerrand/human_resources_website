package hrrss.service.impl;

import hrrss.dao.impl.JobDescriptionDAO;
import hrrss.model.IJobDescription;
import hrrss.model.impl.JobDescription;
import hrrss.service.IJobDescriptionService;

import java.util.List;

public class JobDescriptionService implements IJobDescriptionService {

    private JobDescriptionDAO dao;

    public JobDescriptionDAO getDao() {
	return dao;
    }

    public void setDao(JobDescriptionDAO dao) {
	this.dao = dao;
    }

    public IJobDescription find(Long id) {
	return dao.findOne(id);
    }

    public void save(IJobDescription entity) {
	dao.save((JobDescription) entity);

    }

    public void update(IJobDescription entity) {
	dao.update((JobDescription) entity);

    }
    

    public void delete(IJobDescription entity) {
	dao.delete((JobDescription) entity);
    }

    @Override
    public List<IJobDescription> loadAllJobDescriptionByPerson(String username) {
	return (List<IJobDescription>) dao
		.loadAllJobDescriptionByEmployer(username);
    }
    
    
    public List<IJobDescription> loadAllJobDescriptionByEmployerId(Long id) {
	return (List<IJobDescription>) dao.loadAllJobDescriptionById(id);
    }
    
    public List<IJobDescription> getJobById(Long id) {
    	return dao.getJobById(id);
       }
    
    public List<IJobDescription> loadAllJobDescription() {
    	return (List<IJobDescription>) dao.loadAllJobDescription();
    }
}