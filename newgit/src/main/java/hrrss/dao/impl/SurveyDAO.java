package hrrss.dao.impl;

import java.util.List;

import hrrss.model.ISurvey;
import hrrss.model.impl.Survey;

import org.hibernate.Query;

/**
 * represents DAO for the table "survey", extends AbstractDAO<Survey, which
 * includes CRUD methods
 * 
 * @author Irina
 * 
 */
public class SurveyDAO extends AbstractDAO<Survey> {

	/**
	 * sets parameter for the query "loadSurveyByQ", returns list of the surveys
	 * 
	 * @param id
	 *            - questions ID
	 * @return
	 */
	public List<ISurvey> loadAllSurveysByQ(Long id) {
		Query query = getCurrentSession().getNamedQuery("loadSurveyByQ");
		query.setParameter("id", id);

		return query.list();
	}

	/**
	 * sets parameter for the query "loadSurveyByEmployer", returns list of the
	 * questions
	 * 
	 * @param id
	 *            - employers ID
	 * @return
	 */
	public List<ISurvey> loadAllServeysByEmploer(Long id) {
		Query query = getCurrentSession().getNamedQuery("loadSurveyByEmployer");
		query.setParameter("id", id);

		return query.list();
	}

	/**
	 * sets parameter for the query "loadSurveyNewAnswer", returns list of the
	 * questions
	 * 
	 * @param id
	 *            - survey ID
	 * @return
	 */
	public List<ISurvey> loadServeysNewAnswer(Long id) {
		Query query = getCurrentSession().getNamedQuery("loadSurveyNewAnswer");
		query.setParameter("id", id);

		return query.list();
	}

	/**
	 * sets parameter for the query "loadSurveyById", returns list of the
	 * questions
	 * 
	 * @param id
	 *            - survey ID
	 * @return
	 */
	public ISurvey loadSurveyById(Long id) {
		return (ISurvey) getCurrentSession().getNamedQuery("loadSurveyById")
				.setParameter("id", id).uniqueResult();
	}

}
