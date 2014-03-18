package hrrss.dao.impl;

import java.util.List;

import hrrss.model.IQuastion;
import hrrss.model.impl.Quastion;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * represents DAO for the table "question", extends AbstractDAO<Question, which
 * includes CRUD methods
 * 
 * @author Irina
 * 
 */

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class QuastionDAO extends AbstractDAO<Quastion> {
	@SuppressWarnings("unchecked")
	/**
	 * sets parameter for the query "loadQuestionsBySurvey"
	 * return List of Surveys
	 * @param id - survey ID
	 * @return 
	 */
	public List<IQuastion> loadQuestionBySurvey(long id) {
		Query query = getCurrentSession()
				.getNamedQuery("loadQuestionsBySurvey");
		query.setParameter("id", id);

		return query.list();
	}
}
