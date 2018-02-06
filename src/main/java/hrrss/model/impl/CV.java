package hrrss.model.impl;

import hrrss.model.IApplicant;
import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;

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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "CV")
@NamedQueries({ @NamedQuery(name = "loadCVByUsername", query = "select c from CV c where c.applicant.username = :username"),
@NamedQuery(name = "loadCVById", query = "select c from CV c where c.applicant.id = :id") })
public class CV implements ICV, Serializable {

    private static final long serialVersionUID = -4309282052225979807L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cv_generator")
    @SequenceGenerator(name = "seq_cv_generator", sequenceName = "SEQ_CV")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = Applicant.class, optional = false)
    @JoinColumn(name = "APPLICANT_ID")
    private IApplicant applicant;

    @Column(name = "PERSONAL_EMAIL")
    private String personalEmail;

    @Column(name = "PERSONAL_WEBSITE")
    private String personalWebsite;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "CREATED")
    private Date created;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, targetEntity = CVEducation.class)
    private List<ICVEducation> educations = new ArrayList<ICVEducation>();

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, targetEntity = CVSkill.class)
    private List<ICVSkill> skills = new ArrayList<ICVSkill>();

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, targetEntity = CVExperience.class)
    private List<ICVExperience> experiences = new ArrayList<ICVExperience>();

    @Override
    public Long getId() {
	return id;
    }

    @Override
    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public List<ICVEducation> getEducations() {
	return educations;
    }

    @Override
    public void setEducations(List<ICVEducation> educations) {
	this.educations = educations;
    }

    @Override
    public List<ICVSkill> getSkills() {
	return skills;
    }

    @Override
    public void setSkills(List<ICVSkill> skills) {
	this.skills = skills;
    }

    @Override
    public List<ICVExperience> getExperiences() {
	return experiences;
    }

    @Override
    public void setExperiences(List<ICVExperience> experiences) {
	this.experiences = experiences;
    }

    public void addEducationItem(ICVEducation education) {
	this.educations.add(education);
    }

    public void deleteEducationItem(ICVEducation education) {
	this.educations.remove(education);
    }

    public void addSkillsItem(ICVSkill skill) {
	this.skills.add(skill);
    }

    public void deleteSkillsItem(ICVSkill skill) {
	this.skills.remove(skill);
    }

    public void addEducationItem(ICVExperience exp) {
	this.experiences.add(exp);
    }

    public void deleteEducationItem(ICVExperience exp) {
	this.experiences.remove(exp);
    }

    public String getPersonalEmail() {
	return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
	this.personalEmail = personalEmail;
    }

    public String getPersonalWebsite() {
	return personalWebsite;
    }

    public void setPersonalWebsite(String personalWebsite) {
	this.personalWebsite = personalWebsite;
    }

    public String getNationality() {
	return nationality;
    }

    public void setNationality(String nationality) {
	this.nationality = nationality;
    }

    @Override
    public void addEducation(ICVEducation education) {
	this.educations.add(education);

    }

    @Override
    public void addSkill(ICVSkill skill) {
	this.skills.add(skill);
    }

    @Override
    public void addExperience(ICVExperience experience) {
	this.experiences.add(experience);
    }

    public IApplicant getApplicant() {
	return applicant;
    }

    public void setApplicant(IApplicant applicant) {
	this.applicant = applicant;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }
}
