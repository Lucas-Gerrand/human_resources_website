package hrrss.dao.impl;

import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.ICV;
import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.IMessaging;
import hrrss.model.IReply;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.SavedApplicants;
import hrrss.model.impl.Reply;


import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class SavedApplicantsDAO extends AbstractDAO<SavedApplicants> {
	
	public List<IEmployerSavedApplicants> loadAllSavedApplicantsByEmployerID(Long id) {
    	Query query = getCurrentSession().getNamedQuery("loadAllSavedApplicantsByEmployerID");
    	query.setParameter("id", id);
    	return query.list();
        }
	
	public List<IEmployerSavedApplicants> loadAllApplicantSavedByID(Long id, Long empid) {
    	Query query = getCurrentSession().getNamedQuery("searchForApplicantsIn");
    	query.setParameter("id", id);
    	query.setParameter("empid", empid);
    	return query.list();
        }

}
