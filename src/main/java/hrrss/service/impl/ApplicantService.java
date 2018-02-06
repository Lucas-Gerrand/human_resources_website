package hrrss.service.impl;

import java.util.List;

import hrrss.dao.impl.ApplicantDAO;
import hrrss.model.IApplicant;
import hrrss.model.IMessaging;
import hrrss.model.impl.Applicant;
import hrrss.service.IApplicantService;

// @Component("personService")
public class ApplicantService implements IApplicantService {

    private ApplicantDAO dao;

    public ApplicantDAO getDao() {
	return dao;
    }

    public void setDao(ApplicantDAO dao) {
	this.dao = dao;
    }

    @Override
    public void save(IApplicant entity) {
	dao.save((Applicant) entity);
    }

    public void update(IApplicant entity) {
	dao.update((Applicant) entity);
    }

	@Override
	public List<IApplicant> loadHiredDateByJobID(Long id) {
		return (List<IApplicant>) dao.loadHiredDateByJobID(id);
	}
}