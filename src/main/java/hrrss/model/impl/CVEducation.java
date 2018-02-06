package hrrss.model.impl;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;

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
@Table(name = "CV_EDUCATION")
@NamedQueries({ @NamedQuery(name = "loadCVEducationsByCV", query = "select c from CVEducation c where c.cv.id = :id order by c.start") })
public class CVEducation implements ICVEducation, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cv_education_generator")
    @SequenceGenerator(name = "seq_cv_education_generator", sequenceName = "SEQ_CV_EDUCATION")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = CV.class)
    @JoinColumn(name = "CV_ID", nullable = false)
    private ICV cv;

    @Column(name = "LOCATIION")
    private String location;

    @Column(name = "FACILITY")
    private String facility;

    @Column(name = "START_DATE")
    private String start;

    @Column(name = "END_DATE")
    private String end;

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

    @Override
    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String getDescription() {
	return description;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getFacility() {
	return facility;
    }

    public void setFacility(String facility) {
	this.facility = facility;
    }
}
