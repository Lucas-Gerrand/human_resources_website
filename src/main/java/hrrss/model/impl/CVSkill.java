package hrrss.model.impl;

import hrrss.model.CVSkillType;
import hrrss.model.ICV;
import hrrss.model.ICVSkill;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "CV_SKILLS")
@NamedQueries({ @NamedQuery(name = "loadCVSkillsByCV", query = "select c from CVSkill c where c.cv.id = :id") })
public class CVSkill implements ICVSkill, Serializable {

    private static final long serialVersionUID = 7580612447948378864L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_cv_skill_generator")
    @SequenceGenerator(name = "seq_cv_skill_generator", sequenceName = "SEQ_CV_SKILL")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = CV.class)
    @JoinColumn(name = "cv_id", nullable = false)
    private ICV cv;

    @Column(name = "skillType")
    private String skillType;

    @Column(name = "DESCRIPTION",  columnDefinition="TEXT")
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

    @Override
    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String getDescription() {
	return description;
    }

    public String getSkillType() {
	return skillType;
    }

    public void setSkillType(String skillType) {
	this.skillType = skillType;
    }

}
