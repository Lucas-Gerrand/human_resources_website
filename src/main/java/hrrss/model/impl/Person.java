package hrrss.model.impl;

import hrrss.model.IAddress;
import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.IMediaFile;
import hrrss.model.IPerson;
import hrrss.model.IMessaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "pTypeId")
@NamedQueries({
	@NamedQuery(name = "loadPersonByUsername", query = "select p from Person p where p.username = :username"),
	@NamedQuery(name = "loadPersonById", query = "select p from Person p where p.id = :id"),
	@NamedQuery(name = "loadPersonByActivation", query = "select p from Person p where p.activation = :activation"),
	@NamedQuery(name = "loadPersonByEmail", query = "select p from Person p where p.email = :email") })
public class Person implements IPerson, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_person_generator")
    @SequenceGenerator(name = "seq_person_generator", sequenceName = "SEQ_PERSON")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "E_MAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "GENDER")
    private String gender;
    
    @Column(name = "ACTIVATION")
    private String activation;

    @Column(name = "BIRTHDAY")
    private String birthday;
    
    @Column(name="PICTURE")
    @Lob
    
    
    private byte[] pic;
    
    public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, targetEntity = Messaging.class)
	    
	private List<IMessaging> messaging = new ArrayList<IMessaging>();
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, targetEntity = SavedApplicants.class)
    
	private List<IEmployerSavedApplicants> applicantSaving = new ArrayList<IEmployerSavedApplicants>();
	
    @Embedded
    private Address address;

   
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, targetEntity = MediaFile.class)
    
    private List<IMediaFile> medias = new ArrayList<IMediaFile>();
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public IAddress getAddress() {
	return address;
    }

    public void setAddress(IAddress address) {
	this.address = (Address) address;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @Override
    public String getPassword() {
	return password;
    }

    @Override
    public void setPassword(String password) {
	this.password = password;
    }

    @Override
    public String getEmail() {
	return email;
    }

    @Override
    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public List<IMediaFile> getMedias() {
	return medias;
    }

    @Override
    public void setMedias(List<IMediaFile> medias) {
	this.medias = medias;
    }
    
    @Override
    public List<IMessaging> getMessages(){
    	return messaging;
    }
    
    @Override
    public void setMessages(List<IMessaging> messaging){
    	this.messaging = messaging;
    }   

    @Override
    public String getBirthday() {
	return birthday;
    }

    @Override
    public void setBirthday(String birthday) {
	this.birthday = birthday;
    }

    @Override
    public String getGender() {
	return gender;
    }

    @Override
    public void setGender(String gender) {
	this.gender = gender;
    }
    
    @Override
    public String getActivation() {
	return activation;
    }

    @Override
    public void setActivation(String act) {
	this.activation = act;
    }

}
