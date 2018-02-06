package hrrss.model.impl;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IApplicantToSurvey;
import hrrss.model.ICV;
import hrrss.model.ISurvey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "APPLICANT")
@NamedQueries({ @NamedQuery(name = "loadHiredDateByJobID", query = "select c from Applicant c where c.hired_id = :id")})

@DiscriminatorValue("1")
public class Applicant extends Person implements IApplicant {

	private static final long serialVersionUID = 1L;

	@Column(name = "status")
	private String status="yes";

	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, targetEntity = CV.class)
	private List<ICV> CVs = new ArrayList<ICV>();

	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, targetEntity = ApplicantAnswer.class)
	private List<IApplicantAnswer> answers = new ArrayList<IApplicantAnswer>();

	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, targetEntity = ApplicantToSurvey.class)
	private List<IApplicantToSurvey> applicantToSurveys = new ArrayList<IApplicantToSurvey>();

	@Column(name = "HIRED_ID")
    private Long hired_id;
  
	@Column(name = "HIRED_DATE")
    private Date hired_date;
	
    public Date getHiredDate() {
	return hired_date;
    }

    public void setHiredDate(Date hired_date) {
	this.hired_date = hired_date;
    }
    
    public Long getHired(){
    	return hired_id;
    }
    public void setHired(Long hired_id){
    	this.hired_id = hired_id;
    }
	
	public List<ICV> getCVs() {
		return CVs;
	}

	public void setCVs(List<ICV> cVs) {
		CVs = cVs;
	}

	@Override
	public void addCV(ICV cv) {
		CVs.add(cv);
	}

	@Override
	public List<IApplicantAnswer> getAnswers() {
		return answers;
	}

	@Override
	public void setAnswers(List<IApplicantAnswer> answers) {
		this.answers = answers;
	}

	@Override
	public void addAnswer(IApplicantAnswer answer) {
		this.answers.add(answer);
	}

	@Override
	public void deleteAnswer(IApplicantAnswer answer) {
		this.answers.remove(answer);
	}

	public List<IApplicantToSurvey> getApplicantToSurveys() {
		return applicantToSurveys;
	}

	public void setApplicantToSurveys(
			List<IApplicantToSurvey> applicantToSurveys) {
		this.applicantToSurveys = applicantToSurveys;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}