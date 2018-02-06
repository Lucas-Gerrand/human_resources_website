package hrrss.model;

import java.util.Date;
import java.util.List;

/**
 * Class represent a model of the survey.
 * 
 * @author Irina
 * 
 */
public interface ISurvey {

	/**
	 * gets surveys ID
	 * 
	 * @return
	 */
	public Long getId();

	/**
	 * sets surveys ID
	 * 
	 * @param id
	 */
	public void setId(Long id);

	/**
	 * gets the Instance of an employer
	 * 
	 * @return
	 */
	public IEmployer getEmployer();

	/**
	 * sets an employers
	 * 
	 * @param empl
	 *            - IEmployer
	 */
	public void setEmployer(IEmployer empl);

	/**
	 * gets list of questions
	 * 
	 * @return
	 */
	public List<IQuastion> getQuastions();

	/**
	 * sets list of questions to the survey
	 * 
	 * @param quastions
	 */
	public void setQuastions(List<IQuastion> quastions);

	/**
	 * gets the name of a survey
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * sets a name for the survey
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * add a question to the list of questions
	 * 
	 * @param quastion
	 *            - IQuestion
	 */
	public void addQuestion(IQuastion quastion);

	/**
	 * delete a questions from the list of questions
	 * 
	 * @param question
	 *            - IQuestion
	 */
	public void deleteQuestion(IQuastion question);

	/**
	 * gets description of a survey
	 * 
	 * @return
	 */
	public String getText();

	/**
	 * sets description of a survey
	 * 
	 * @param text
	 *            - String
	 */
	public void setText(String text);

	/**
	 * gets date of a Survey
	 * 
	 * @return
	 */
	public Date getDate();

	/**
	 * sets date of a survey
	 * 
	 * @param date
	 */
	public void setDate(Date date);

	/**
	 * gets applicantstosurvey instances
	 * 
	 * @return
	 */
	public List<IApplicantToSurvey> getApplicantToSurvey();

	/**
	 * set list of applicanttosurvey instances
	 * 
	 * @param applicantToSurveys
	 *            - List<applicantToSurvey
	 */
	public void setApplicantToSurvey(List<IApplicantToSurvey> applicantToSurveys);

	/**
	 * adds a applicanttosurvey object to the list
	 * 
	 * @param applicantToSurvey
	 */
	public void addApplicantToSurvey(IApplicantToSurvey applicantToSurvey);

	/**
	 * deletes a applicanttosurvey object from the list
	 * 
	 * @param applicantToSurvey
	 */
	public void deleteApplicantToSurvey(IApplicantToSurvey applicantToSurvey);

	/**
	 * logical delete
	 * 
	 * @return
	 */
	boolean isDelete();

	/**
	 * set logical delete
	 * 
	 * @param delete
	 */
	void setDelete(boolean del);

}
