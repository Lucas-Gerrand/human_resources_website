package hrrss.dao.impl;

import java.util.List;

import org.hibernate.Query;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.impl.ApplicantToSurvey;

public class ApplicantToSurveyDAO extends AbstractDAO<ApplicantToSurvey> {

	public List<IApplicantToSurvey> loadAllSurveysByApplicant(Long id) {
		Query query = getCurrentSession().getNamedQuery(
				"loadSurveysByApplicant");
		query.setParameter("id", id);

		return query.list();
	}

	public List<IApplicantToSurvey> loadAllApplicantBySurvey(Long id) {
		Query query = getCurrentSession().getNamedQuery(
				"loadApplicantsBySurvey");
		query.setParameter("id", id);

		return query.list();
	}
	
	public List<IApplicantToSurvey> loadAppSurByApplicantAndSurvey(Long id, Long ids) {
		Query query = getCurrentSession().getNamedQuery(
				"loadApplicantSuvByApplicantSurvey");
		query.setParameter("id", id);
		query.setParameter("ids", ids);

		return query.list();
	}
	

	public List<IApplicantToSurvey> loadNewAnswerByEmployer(boolean newq,
			Long id) {
		Query query = getCurrentSession().getNamedQuery(
				"loadApplicantNewAnswersSurvey");
		query.setParameter("newq", newq);
		query.setParameter("id", id);

		return query.list();
	}

	public List<IApplicantToSurvey> loadAllAnswerByEmployer(Long id) {
		Query query = getCurrentSession().getNamedQuery(
				"loadApplicantAnswersSurveyByEmployer");
		query.setParameter("id", id);

		return query.list();
	}

	public List<IApplicantToSurvey> loadVisibleAnswerForEmployer(boolean vis) {
		Query query = getCurrentSession().getNamedQuery(
				"loadApplicantAnswersSurveyByEmployer");
		query.setParameter("vis", vis);

		return query.list();
	}
	
	public List<IApplicantToSurvey> loadNewSurveysByApplicant(Long id) {
		Query query = getCurrentSession().getNamedQuery(
				"loadNewSurveysByApplicant");
		query.setParameter("id", id);

		return query.list();
	}


	public void save(ApplicantToSurvey entity) {
		super.save(entity);

	}
	
	public void delete(ApplicantToSurvey entity) {
		super.delete(entity);

	}

}
