package hrrss.model.impl;

import hrrss.model.IMediaFile;
import hrrss.model.IPerson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MEDIA_FILE")
public class MediaFile implements IMediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_media_file_generator")
    @SequenceGenerator(name = "seq_media_file_generator", sequenceName = "SEQ_MEDIA_FILE")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id")
    private IPerson person;

    @Transient
    private String test;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public IPerson getPerson() {
	return person;
    }

    public void setPerson(IPerson person) {
	this.person = person;
    }
}
