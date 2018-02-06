package hrrss.dao.impl;

import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.IMessaging;
import hrrss.model.impl.Applicant;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class ApplicantDAO extends AbstractDAO<Applicant> {

    public List<IApplicant> loadHiredDateByJobID(Long id) {
	Query query = getCurrentSession().getNamedQuery("loadHiredDateByJobID");
	query.setParameter("id", id);

	return query.list();
    }
	//loadHiredDateByJobID
}
