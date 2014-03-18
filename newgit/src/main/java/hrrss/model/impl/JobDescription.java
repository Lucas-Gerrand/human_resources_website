package hrrss.model.impl;

import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;

import java.io.Serializable;
import java.util.Date;

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

@Entity
@Table(name = "JOB_DESCRIPTION")
@NamedQueries({ @NamedQuery(name = "loadAllJobDescriptionByUsername", query = "select j from JobDescription j join j.employer e where e.username = :username"),
	@NamedQuery(name = "loadAllJobDescriptionByEmployerId", query = "select j from JobDescription j join j.employer e where e.id = :id"),
	@NamedQuery(name = "loadAllJobDescription", query = "select j from JobDescription j"),
	@NamedQuery(name = "getJobById", query = "select j from JobDescription j where j.id=:id")
})

public class JobDescription implements IJobDescription, Serializable {

    private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_job_description_generator")
    @SequenceGenerator(name = "seq_job_description_generator", sequenceName = "SEQ_JOB_DESCRIPTION")
    @Column(name = "ID")
    private Long id;

    
    @ManyToOne(targetEntity = Employer.class)
    @JoinColumn(name = "EMPLOYER_ID", nullable = false)
    private IEmployer employer;

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "MAIN_PURPOSE",  columnDefinition="TEXT")
    private String mainPurpose;

    @Column(name = "QUALIFICATON")
    private String qualification;
    
    @Column(name = "DATE")
    private Date date = new Date();
    
    public IEmployer getEmployer() {
	return employer;
    }

    public void setEmployer(IEmployer employer) {
	this.employer = employer;
    }

    public String getJobTitle() {
	return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
	this.jobTitle = jobTitle;
    }

    public String getMainPurpose() {
	return mainPurpose;
    }

    public void setMainPurpose(String mainPurpose) {
	this.mainPurpose = mainPurpose;
    }

    public String getQualification() {
	return qualification;
    }

    public void setQualification(String qualification) {
	this.qualification = qualification;
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
	public String getAll() {
		String all = jobTitle+" / "+mainPurpose+" / "+qualification;
		return all;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
