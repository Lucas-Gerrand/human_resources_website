package hrrss.service;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.IQuastion;
import hrrss.model.ISubAnswer;
import hrrss.model.ISurvey;

import java.util.List;

/**
 * class for work with the survey
 * 
 * @author Irina
 * 
 */
public interface ISurveyService {

	/**
	 * returns an instance of the survey according the Survey ID
	 * 
	 * @param id
	 * @return
	 */
	public ISurvey find(Long id);

	/**
	 * finds all surveys in the DB
	 * 
	 * @return
	 */
	public List<ISurvey> findAll();

	/**
	 * saves a new survey into the DB
	 * 
	 * @param entity
	 */
	public void save(ISurvey entity);

	/**
	 * updates a survey in the DB
	 * 
	 * @param entity
	 */
	public void update(ISurvey entity);

	/**
	 * delete a survey from the DB
	 * 
	 * @param entity
	 */
	public void delete(ISurvey entity);

	/**
	 * loads all surveys by applicants ID
	 * 
	 * @param id
	 * @return
	 */
	public List<IApplicantToSurvey> loadAllServeyByUser(Long id);

	/**
	 * loads all surveys by employers ID
	 * 
	 * @param id
	 * @return
	 */
	public List<ISurvey> loadAllServeyByEmployer(Long id);

	/**
	 * loads all surveys by questions ID
	 * 
	 * @param id
	 * @return
	 */
	public List<ISurvey> loadAllServeyByQ(Long id);

	/**
	 * loads questions by surveys ID
	 * 
	 * @param id
	 * @return
	 */
	public List<IQuastion> loadAllQuestionBySurvey(Long id);

	/**
	 * loads all answers for a closed question
	 * 
	 * @param id
	 * @return
	 */
	public List<ISubAnswer> loadAllSubAnswerByQuestion(Long id);

	/**
	 * loads all survey according the applicants ID
	 * 
	 * @param id
	 * @return
	 */
	public List<IApplicantToSurvey> loadAllSurveysByApplicant(Long id);

	/**
	 * loads new answers from applicants
	 * 
	 * @param newq
	 * @param id
	 * @return
	 */
	public List<IApplicantToSurvey> loadNewAnswerByEmployer(boolean newq,
			Long id);

	/**
	 * loads all visible for an employer answers of all applicant according the
	 * employers ID
	 * 
	 * @param id
	 * @return
	 */
	public List<IApplicantToSurvey> loadAllAnswerByEmployer(Long id);

	/**
	 * loads all visible/ not visible answers
	 * 
	 * @param vis
	 *            - visibility
	 * @return
	 */
	public List<IApplicantToSurvey> loadVisibleAnswerByEmployer(boolean vis);

	/**
	 * loads applicanttosurveys instances according the applicants ID and the
	 * surveys ID
	 * 
	 * @param id
	 *            - applicants ID
	 * @param ids
	 *            - surveys ID
	 * @return
	 */
	public List<IApplicantToSurvey> loadAppSurveyByApplicantAndSurvey(Long id,
			Long ids);

	/**
	 * loads applicanttosurveys instances according the applicants ID and the
	 * surveys ID
	 * 
	 * @param entity
	 */
	public void updateAppSurvey(IApplicantToSurvey entity);

	/**
	 * saves applicanttosurveys instances into the table "applicanttosurvey"
	 * 
	 * @param entity
	 */
	public void saveAppSurvey(IApplicantToSurvey entity);

	/**
	 * delete applicanttosurveys record in the table "applicanttosurvey"
	 * 
	 * @param entity
	 */
	public void deleteApplicantToSurvey(IApplicantToSurvey entity);

	/**
	 * returns list of new applicanttosurveys 
	 * @param id
	 * @return
	 */
	public List<IApplicantToSurvey> loadNewSurveysByApplicant(Long id);

}
