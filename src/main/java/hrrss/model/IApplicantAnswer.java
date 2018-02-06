package hrrss.model;

import java.io.File;

/**
 * Class represents a model of the Applicant Answer. The table "applicant answer"
 * includes the information about applicants answer. It contains: id - answers
 * id applicant - instance of an applicant answer - answer file - file, which
 * the user uploaded (for attach question) fileName - files name
 * 
 * @author Irina
 * 
 */
public interface IApplicantAnswer {

	/**
	 * gets ID of the Applicants Answer
	 * 
	 * @return
	 */
	public Long getId();

	/**
	 * sets ID of the Applicants Answer
	 * 
	 * @param id
	 */
	public void setId(Long id);

	/**
	 * get an instance of the applicant
	 * 
	 * @return
	 */
	public IApplicant getApplicant();

	/**
	 * set an instance of an Applicant
	 * 
	 * @param applicant
	 */
	public void setApplicant(IApplicant applicant);

	/**
	 * gets Question
	 * 
	 * @return
	 */
	public IQuastion getQuestion();

	/**
	 * sets Question
	 * 
	 * @param quastion
	 */
	public void setQuestion(IQuastion quastion);

	/**
	 * get a Answer of an applicant
	 * 
	 * @return
	 */
	public String getAnswer();

	/**
	 * sets an Answer of an applicant
	 * 
	 * @param answer
	 */
	public void setAnswer(String answer);

	/**
	 * gets a file, which the applicant uploaded
	 * 
	 * @return
	 */
	public File getApplicantFile();

	/**
	 * sets a file
	 * 
	 * @param file
	 */
	public void setApplicantFile(File file);

	/**
	 * gets the name of an uploaded file
	 * 
	 * @return
	 */
	public String getFileName();

	/**
	 * sets a name for the applicant file
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName);
}
