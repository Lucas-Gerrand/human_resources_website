package hrrss.model.impl;

import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.ISurvey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYER")
@DiscriminatorValue("2")
public class Employer extends Person implements IEmployer {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, targetEntity = JobDescription.class)
	private List<IJobDescription> jobDescriptions = new ArrayList<IJobDescription>();

	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, targetEntity = Survey.class)
	private List<ISurvey> surveys;

	@Column(name = "COMPANY_NAME")
	private String companyname;

	@Column(name = "contact")
	private String contact;

	@Column(name = "homepage")
	private String homepage;

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<IJobDescription> getJobDescriptions() {
		return jobDescriptions;
	}

	public void setJobDescriptions(List<IJobDescription> jobDescriptions) {
		this.jobDescriptions = jobDescriptions;
	}

	public List<ISurvey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<ISurvey> surveys) {
		this.surveys = surveys;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

}
