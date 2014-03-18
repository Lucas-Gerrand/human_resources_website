package hrrss.service.impl;

import java.util.List;

import hrrss.dao.impl.SavedApplicantsDAO;
import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.impl.SavedApplicants;
import hrrss.service.ISavedApplicantService;

public class SavedApplicantService implements ISavedApplicantService {
	
	
    private SavedApplicantsDAO dao;

    public SavedApplicantsDAO getDao() {
	return dao;
    }

    public void setDao(SavedApplicantsDAO dao) {
	this.dao = dao;
    }
    
    public void update(IEmployerSavedApplicants entity) {
	dao.update((SavedApplicants) entity);
    }

    public void deleteById(Long id) {
	dao.deleteById(id);
    }
    
    @Override
	public void save(IEmployerSavedApplicants entity) {
		dao.save((SavedApplicants) entity);
		
	}

	@Override
	public void delete(IEmployerSavedApplicants entity) {
		dao.delete((SavedApplicants) entity);
		
	}

	@Override
	public List<IEmployerSavedApplicants> loadAllSavedApplicantsByEmployerID(
			Long id) {
		return (List<IEmployerSavedApplicants>) dao.loadAllSavedApplicantsByEmployerID(id);
		
	}
	
	@Override
	public List<IEmployerSavedApplicants> loadSavedApplicantByID(
			Long id, Long empid) {
		return (List<IEmployerSavedApplicants>) dao.loadAllApplicantSavedByID(id, empid);
		
	}

	
}