package hrrss.model.impl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantToSurvey;
import hrrss.model.ISurvey;


@Entity
@Table(name = "ApplicantToSurvey")
@NamedQueries({ 
	@NamedQuery(name = "loadSurveysByApplicant", query = "select a from ApplicantToSurvey a where a.applicant.id = :id and a.visapp = true ORDER BY a.date DESC"),
	@NamedQuery(name = "loadNewSurveysByApplicant", query = "select a from ApplicantToSurvey a where a.applicant.id = :id and a.newqa = true ORDER BY a.date DESC"),
	@NamedQuery(name = "loadApplicantsBySurvey", query = "select a from ApplicantToSurvey a where a.survey.id = :id"),
	@NamedQuery(name = "loadApplicantSuvByApplicantSurvey", query = "select a from ApplicantToSurvey a where a.applicant.id = :id and a.survey.id = :ids"),
	@NamedQuery(name = "loadApplicantNewAnswersSurvey", query = "select a from ApplicantToSurvey a join fetch a.survey s where a.newq = :newq and s.employer.id = :id"),
	@NamedQuery(name = "loadApplicantVisibleAnswerForEmployer", query = "select a from ApplicantToSurvey a where a.vis = :vis"),
	@NamedQuery(name = "loadApplicantAnswersSurveyByEmployer", query = "select a from ApplicantToSurvey a join fetch a.survey s where s.employer.id = :id and a.vis = true ORDER BY a.date DESC")
})

@DiscriminatorValue("1")
public class ApplicantToSurvey implements IApplicantToSurvey, Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_app_sur_generator")
	@SequenceGenerator(name = "seq_app_sur_generator", sequenceName = "SEQ_APP_SUR")
	@Column(name = "ID")
	private Long id;

	@ManyToOne(targetEntity = Applicant.class)
	@JoinColumn(name = "Applicant_ID")
	private IApplicant applicant;
	
	@ManyToOne(targetEntity = Survey.class)
	@JoinColumn(name = "Survey_ID")
	private ISurvey survey;
	
	/**
	 * vis - boolean value
	 * 		shows does the employer see the applicants answers
	 */
	@Column(name = "visible")
	private boolean vis;
	
	/**
	 * newq - boolean value
	 * 		shows the applicants answer new
	 * 		for an employer
	 */
	@Column(name = "newq")
	private boolean newq;
	
	/**
	 * newqa - boolean value
	 * 		shows that the survey new
	 * 		for an applicant  
	 */
	@Column(name = "newqa")
	private boolean newqa;
	

	/**
	 * visapp - boolean value
	 * 		shows does the applicant see the survey
	 * 		use for sending it from employee to applicant  
	 */
	@Column(name = "visibleapp")
	private boolean visapp;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", length = 10)
	private Date date;
	
	@Column(name = "comment")
	private String comment;
	
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getNewq() {
		return newq;
	}

	public void setNewq(boolean newq) {
		this.newq = newq;
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
	public ISurvey getServey() {
		return survey;
	}

	@Override
	public void setServeys(ISurvey survey) {
		this.survey = survey;
	}

	@Override
	public boolean getVis() {
		return vis;
	}

	@Override
	public void setVis(boolean vis) {
		this.vis = vis;
	}
	
	public boolean getNewqa() {
		return newqa;
	}

	public void setNewqa(boolean newqa) {
		this.newqa = newqa;
	}
	
	public boolean getVisapp(){
		return visapp;
	}


	public void setVisapp(boolean visapp) {
		this.visapp = visapp;
	}


}
