package hrrss.model.impl;

import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.IPerson;

import java.io.Serializable;

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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "SAVED_APPLICANTS")
@DiscriminatorValue("2")
@NamedQueries({
	@NamedQuery(name = "loadAllSavedApplicantsByEmployerID", query = "select j from SavedApplicants j where j.person.id = :id"),
	@NamedQuery(name = "searchForApplicantsIn", query = "select j from SavedApplicants j where j.applicantID = :id and j.person.id = :empid")
	
})
public class SavedApplicants implements IEmployerSavedApplicants, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_saver_generator")
    @SequenceGenerator(name = "seq_saver_generator", sequenceName = "SEQ_SAVE")
    @Column(name = "ID")
    private Long id;
	
	@ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id")
    private IPerson person;
	
	@Column(name = "APPLICANTID")
	private Long applicantID;
	
	@Column(name = "COMMENT")
	private String comment;

	public void setSaveId(Long id) {
		this.id = id;
	}
	public Long getMessageId() {
		return id;
	}
	
	 public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getApplicantID() {
		return applicantID;
	}
	public void setApplicantID(Long applicantID) {
		this.applicantID = applicantID;
	}
	public IPerson getPerson() {
			return person;
	 }

	public void setPerson(IPerson person) {
		this.person = person;
	}
	
	
	
	
}
