package hrrss.model.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


import hrrss.model.IApplicantAnswer;
import hrrss.model.IQuastion;
import hrrss.model.ISubAnswer;
import hrrss.model.ISurvey;

@Entity
@Table(name = "Quastion")
@NamedQueries({
	@NamedQuery(name = "loadQuestionsBySurvey", query = "select q from Quastion q where q.survey.id = :id")
})
public class Quastion implements IQuastion, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_quastion_generator")
	@SequenceGenerator(name = "seq_quastion_generator", sequenceName = "SEQ_QUASTION")
	@Column(name = "ID")
	private Long id;

	@ManyToOne(targetEntity = Survey.class)
	@JoinColumn(name = "Survey_ID")
	private ISurvey survey;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, targetEntity = SubAnswer.class)
	private List<ISubAnswer> subanswers = new ArrayList<ISubAnswer>();

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, targetEntity = ApplicantAnswer.class)
	private List<IApplicantAnswer> applicantAnswers = new ArrayList<IApplicantAnswer>();
	
	@Column(name = "Quastion")
	private String quastion;

	@Column(name = "Answer")
	private String answer;
	
	@Column(name = "Ques_type")
	private String qType;
	
	@Column(name = "Additional_inf")
	private String addInf;
	
	@Column(name = "number")
	private int number;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * file to attach
	 */
	@Column(name = "file")
	private File file ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ISurvey getSurvay() {
		return survey;
	}

	public void setSurvey(ISurvey survey) {
		this.survey = survey;
	}

	public String getQuastion() {
		return quastion;
	}

	public void setQuastion(String quastion) {
		this.quastion = quastion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String getTypeOfQuastion() {
		return qType;
	}

	@Override
	public void setTypeOfQuastion(String qType) {
		this.qType = qType;
	}

	@Override
	public String getAdditionalInformation() {
		return addInf;
	}

	@Override
	public void setAdditionalInformation(String addInf) {
		this.addInf = addInf;	
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setFile(File file) {
		this.file = file;
	}

	public List<ISubAnswer> getSubAnswers() {
		return subanswers;
	}

	
	public void setSubAnswers(List<ISubAnswer> subanswers) {
		this.subanswers = subanswers;
	}
	
	public void addSubA(ISubAnswer subanswer){
		this.subanswers.add(subanswer);
	}
	
	public void deleteSubA(ISubAnswer subanswer){
		this.subanswers.remove(subanswer);
	}

	@Override
	public List<IApplicantAnswer> getApplicantAnswer() {
		return applicantAnswers;
	}

	@Override
	public void setApplicantAnswer(List<IApplicantAnswer> applicantAnswer) {
		this.applicantAnswers = applicantAnswer;
	}

	@Override
	public void addAplAns(IApplicantAnswer applAns) {
		this.applicantAnswers.add(applAns);
	}

	@Override
	public void deleteAplAns(IApplicantAnswer applAns) {
		this.applicantAnswers.remove(applAns);
	}

}
