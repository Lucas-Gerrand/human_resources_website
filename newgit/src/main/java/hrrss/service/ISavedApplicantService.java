package hrrss.service;

import java.util.List;
import hrrss.model.IEmployerSavedApplicants;

public interface ISavedApplicantService {

	public void save(IEmployerSavedApplicants entity);

    public void update(IEmployerSavedApplicants entity);

    public void delete(IEmployerSavedApplicants entity);
    
    public List<IEmployerSavedApplicants> loadAllSavedApplicantsByEmployerID(Long id);
    
    public List<IEmployerSavedApplicants> loadSavedApplicantByID(Long id, Long empid); 
    
}
