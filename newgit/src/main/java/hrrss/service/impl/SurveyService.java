package hrrss.service.impl;

import java.util.ArrayList;
import java.util.List;


import hrrss.dao.impl.ApplicantToSurveyDAO;
import hrrss.dao.impl.QuastionDAO;
import hrrss.dao.impl.SubAnswerDAO;
import hrrss.dao.impl.SurveyDAO;
import hrrss.model.IApplicantToSurvey;
import hrrss.model.IQuastion;
import hrrss.model.ISubAnswer;
import hrrss.model.ISurvey;
import hrrss.model.impl.ApplicantToSurvey;
import hrrss.model.impl.Survey;
import hrrss.service.ISurveyService;

public class SurveyService implements ISurveyService {
	
	private SurveyDAO dao;
	private QuastionDAO qDao;
	private SubAnswerDAO sDao;
	private ApplicantToSurveyDAO aDao;
	


	public SubAnswerDAO getsDao() {
		return sDao;
	}

	public void setsDao(SubAnswerDAO sDao) {
		this.sDao = sDao;
	}

	public SurveyDAO getDao() {
		return dao;
	}

	public void setDao(SurveyDAO dao) {
		this.dao = dao;
	}
	

	public ApplicantToSurveyDAO getaDao() {
		return aDao;
	}

	public void setaDao(ApplicantToSurveyDAO aDao) {
		this.aDao = aDao;
	}

	@Override
	public List<IApplicantToSurvey> loadAllServeyByUser(Long id) {
		return aDao.loadAllSurveysByApplicant(id);
	}

	@Override
	public ISurvey find(Long id) {
		return dao.loadSurveyById(id);
	}

	@Override
	public void save(ISurvey entity) {
		dao.save((Survey) entity);
	}
	
	public void saveAppSurvey(IApplicantToSurvey entity){
		aDao.save((ApplicantToSurvey) entity);
	}
	
	public void updateAppSurvey(IApplicantToSurvey entity){
		aDao.update((ApplicantToSurvey) entity);
	}
	
	@Override
	public void update(ISurvey entity) {
		dao.update((Survey) entity);
	}

	@Override
	public void delete(ISurvey entity) {
		dao.delete((Survey) entity);
	}
	
	@Override
	public void deleteApplicantToSurvey(IApplicantToSurvey entity) {
		aDao.delete((ApplicantToSurvey) entity);
	}

	@Override
	public List<ISurvey> loadAllServeyByEmployer(Long id) {
		return dao.loadAllServeysByEmploer(id);
	}

	public QuastionDAO getqDao() {
		return qDao;
	}

	public void setqDao(QuastionDAO qDao) {
		this.qDao = qDao;
	}

	@Override
	public List<IQuastion> loadAllQuestionBySurvey(Long id) {
		System.out.println("GET ID: " + id);
		return qDao.loadQuestionBySurvey(id);
	}

	@Override
	public List<ISurvey> findAll() {
		dao.setClazz(Survey.class);
		List<ISurvey> Surveys = new ArrayList<ISurvey>();
		for (ISurvey  survey: dao.findAll()) {
		    Surveys.add(survey);
		}
		return Surveys;
	}

	@Override
	public List<ISurvey> loadAllServeyByQ(Long id) {
		return dao.loadAllSurveysByQ(id);
	}

	@Override
	public List<ISubAnswer> loadAllSubAnswerByQuestion(Long id) {
		return sDao.loadSubAnswersByQuestion(id);
	}

	@Override
	public List<IApplicantToSurvey> loadAllSurveysByApplicant(Long id) {
		return aDao.loadAllSurveysByApplicant(id);
	}

	@Override
	public List<IApplicantToSurvey> loadNewAnswerByEmployer(boolean newq, Long id) {
		return aDao.loadNewAnswerByEmployer(newq, id);
	}

	@Override
	public List<IApplicantToSurvey> loadVisibleAnswerByEmployer(boolean vis) {
		return aDao.loadVisibleAnswerForEmployer(vis);
	}

	@Override
	public List<IApplicantToSurvey> loadAllAnswerByEmployer(Long id) {
		return aDao.loadAllAnswerByEmployer(id);
	}

	@Override
	public List<IApplicantToSurvey> loadAppSurveyByApplicantAndSurvey(Long id,
			Long ids) {
		return aDao.loadAppSurByApplicantAndSurvey(id, ids);
	}	

	@Override
	public List<IApplicantToSurvey> loadNewSurveysByApplicant(Long id) {
		return aDao.loadNewSurveysByApplicant(id);
	}	

}
