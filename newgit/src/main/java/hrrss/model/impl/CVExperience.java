package hrrss.model.impl;

import hrrss.model.ICV;
import hrrss.model.ICVExperience;

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
@Table(name = "CV_EXPERIENCE")
@NamedQueries({ @NamedQuery(name = "loadCVExperiencesByCV", query = "select c from CVExperience c where c.cv.id = :id order by c.start") })
public class CVExperience implements ICVExperience, Serializable {

    private static final long serialVersionUID = -1436213966132891423L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cv_experience_generator")
    @SequenceGenerator(name = "seq_cv_experience_generator", sequenceName = "SEQ_CV_EXPERIENCE")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = CV.class)
    @JoinColumn(name = "cv_id", nullable = false)
    private ICV cv;

    @Column(name = "START_DATE")
    private String start;

    @Column(name = "END_DATE")
    private String end;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "TITLE", length=400)
    private String title;

    @Column(name = "BUSINESS_SECTOR")
    private String businessSector;

    @Column(name = "DESCRIPTION", columnDefinition="TEXT")
    private String description;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public ICV getCv() {
	return cv;
    }

    public void setCv(ICV cv) {
	this.cv = cv;
    }

    public String getEnd() {
	return end;
    }

    public void setEnd(String end) {
	this.end = end;
    }

    public String getStart() {
	return start;
    }

    public void setStart(String start) {
	this.start = start;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    public String getBusinessSector() {
	return businessSector;
    }

    public void setBusinessSector(String businessSector) {
	this.businessSector = businessSector;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getCompany() {
	return company;
    }

    public void setCompany(String company) {
	this.company = company;
    }

}
