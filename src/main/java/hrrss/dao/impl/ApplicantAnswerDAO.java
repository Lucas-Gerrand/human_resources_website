package hrrss.dao.impl;

import java.util.List;

import hrrss.model.IApplicantAnswer;
import hrrss.model.impl.ApplicantAnswer;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * represents DAO for the table "applicantAnswer", extends
 * AbstractDAO<ApplicantAnswer", which includes CRUD methods
 * 
 * @author Irina
 * 
 */
@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class ApplicantAnswerDAO extends AbstractDAO<ApplicantAnswer> {
	@SuppressWarnings("unchecked")
	/**
	 * sets parameter for the query "loadApplicantAnswersByQuestion"
	 * 
	 * @param id - questions ID
	 * @param app - applicants ID 
	 * @return
	 */
	public List<IApplicantAnswer> loadAnswerByQuestion(long id, long app) {
		Query query = getCurrentSession().getNamedQuery(
				"loadApplicantAnswersByQuestion");
		query.setParameter("id", id);
		query.setParameter("app", app);
		return query.list();
	}

}
