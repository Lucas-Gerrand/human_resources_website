package hrrss.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hrrss.model.ISubAnswer;
import hrrss.model.impl.SubAnswer;

/**
 * represents DAO for the table "subanswers", extends AbstractDAO<SubAnswer,
 * which includes CRUD methods
 * 
 * @author Irina
 * 
 */
@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class SubAnswerDAO extends AbstractDAO<SubAnswer> {

	/**
	 * sets parameter for the query "loadSubAnswersByQuestion", returns list of
	 * the sub answers
	 * 
	 * @param id
	 *            - Question ID (for closed question)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ISubAnswer> loadSubAnswersByQuestion(long id) {
		Query query = getCurrentSession().getNamedQuery(
				"loadSubAnswersByQuestion");
		query.setParameter("id", id);

		return query.list();
	}
}
