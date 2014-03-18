package hrrss.model.impl;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IQuastion;

/**
 * creates for the table "ApplicantAnswer" (answers which the applicant saved)
 * Query "loadApplicantAnswersByQuestion" loads all instances ApplicantAnswer
 * according the user ID and the question ID
 * 
 * @author Irina
 * 
 */
@Entity
@Table(name = "ApplicantAnswer")
@NamedQueries({ @NamedQuery(name = "loadApplicantAnswersByQuestion", query = "select a from ApplicantAnswer a where a.question.id = :id and a.applicant.id = :app") })
public class ApplicantAnswer implements IApplicantAnswer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Columns: id, applicant_id, question_id, answer, file, files name
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_applicant_answer_generator")
	@SequenceGenerator(name = "seq_applicant_answer_generator", sequenceName = "SEQ_APPLICANT_ANSWER")
	@Column(name = "ID")
	private Long id;

	@ManyToOne(targetEntity = Applicant.class)
	@JoinColumn(name = "Applicant_ID")
	private IApplicant applicant;

	@ManyToOne(targetEntity = Quastion.class)
	@JoinColumn(name = "Question_ID")
	private IQuastion question;

	@Column(name = "Answer")
	private String answer;

	@Column(name = "File")
	private File file;

	@Column(name = "Filename")
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public IApplicant getApplicant() {
		return applicant;
	}

	@Override
	public void setApplicant(IApplicant applicant) {
		this.applicant = applicant;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public IQuastion getQuestion() {
		return question;
	}

	@Override
	public void setQuestion(IQuastion quastion) {
		this.question = quastion;
	}

	@Override
	public File getApplicantFile() {
		return file;
	}

	@Override
	public void setApplicantFile(File file) {
		this.file = file;
	}

}
