package hrrss.service;

import hrrss.model.IApplicantAnswer;

import java.util.List;

/**
 * class for work with the applicant answer
 * 
 * @author Irina
 * 
 */
public interface IApplicantAnswerService {

	/**
	 * saves an answer of an applicant in the DB
	 * 
	 * @param entity
	 */
	public void save(IApplicantAnswer entity);

	/**
	 * updates an answer of an applicant in the DB
	 * 
	 * @param entity
	 */
	public void update(IApplicantAnswer entity);

	/**
	 * deletes an answer of an applicant in the DB
	 * 
	 * @param entity
	 */
	public void delete(IApplicantAnswer entity);

	/**
	 * finds an answer of an applicant in the DB, returns an instance of
	 * ApplicantAnswer
	 * 
	 * @param id
	 * @return
	 */
	public IApplicantAnswer find(Long id);

	/**
	 * finds an answer of an applicant according the questions ID and the
	 * applicants ID, returns list of applicants answers
	 * 
	 * @param id
	 * @param app
	 * @return
	 */
	public List<IApplicantAnswer> findByQuestion(Long id, long app);

}
