package hrrss.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.IEmployer;
import hrrss.model.IQuastion;
import hrrss.model.ISurvey;

@Entity
@Table(name = "Survey")
@NamedQueries({
		@NamedQuery(name = "loadSurveyByEmployer", query = "select s from Survey s where s.employer.id = :id and s.del = false ORDER BY s DESC"),
		@NamedQuery(name = "loadSurveyByQ", query = "select s from Survey s join fetch s.quastions a where a.id = :id ORDER BY s DESC"),
		@NamedQuery(name = "loadSurveyNewAnswer", query = "select s from Survey s join fetch s.applicantToSurveys a where s.employer.id = :id and a.newq=true ORDER BY s DESC"),
		@NamedQuery(name = "loadSurveyById", query = "select s from Survey s where s.id = :id") })
public class Survey implements ISurvey, Serializable {

	public List<IQuastion> getQuastions() {
		return quastions;
	}

	public void setQuastions(List<IQuastion> quastions) {
		this.quastions = quastions;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sur_generator")
	@SequenceGenerator(name = "seq_sur_generator", sequenceName = "SEQ_SUR")
	@Column(name = "ID")
	private Long id;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, targetEntity = Quastion.class)
	private List<IQuastion> quastions = new ArrayList<IQuastion>();

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, targetEntity = ApplicantToSurvey.class)
	private List<IApplicantToSurvey> applicantToSurveys = new ArrayList<IApplicantToSurvey>();

	@ManyToOne(targetEntity = Employer.class)
	@JoinColumn(name = "Employer_ID")
	private IEmployer employer;

	@Column(name = "name")
	private String name;

	@Column(name = "text")
	private String text;

	@Column(name = "del")
	private boolean del;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 10)
	private Date date;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Survey() {
		super();
	}

	public Survey(Survey survey) {
		super();
		this.quastions = survey.quastions;
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
	public IEmployer getEmployer() {
		return employer;
	}

	@Override
	public void setEmployer(IEmployer employer) {
		this.employer = employer;
	}

	public void addQuestion(IQuastion question) {
		this.quastions.add(question);
	}

	public void deleteQuestion(IQuastion question) {
		this.quastions.remove(question);
	}

	@Override
	public List<IApplicantToSurvey> getApplicantToSurvey() {
		return applicantToSurveys;
	}

	@Override
	public void setApplicantToSurvey(List<IApplicantToSurvey> applicantToSurveys) {
		this.applicantToSurveys = applicantToSurveys;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void addApplicantToSurvey(IApplicantToSurvey applicantToSurvey) {
		this.applicantToSurveys.add(applicantToSurvey);

	}

	@Override
	public void deleteApplicantToSurvey(IApplicantToSurvey applicantToSurvey) {
		this.applicantToSurveys.remove(applicantToSurvey);
	}

	@Override
	public boolean isDelete() {
		return del;
	}

	@Override
	public void setDelete(boolean del) {
		this.del = del;
	}

}
