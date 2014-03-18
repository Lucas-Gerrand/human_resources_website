package hrrss.service;

import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.ICV;

public interface IApplicantService {

    public void save(IApplicant entity);
    
    public List<IApplicant> loadHiredDateByJobID(Long id);
    

}
