package hrrss.model.impl;

import hrrss.model.IMessaging;
import hrrss.model.IPerson;
import hrrss.model.IReply;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "MESSAGING")

@NamedQueries({
	@NamedQuery(name = "loadAllMessagesByPersonId", query = "select j from Messaging j join j.person e where e.id = :id"),
	@NamedQuery(name = "loadAllMessages", query = "select j from Messaging j where j.id = :id"), 
	@NamedQuery(name = "loadAllInboxByPersonId", query = "select j from Messaging j where j.id.receiverID = :id ORDER BY j.date DESC"),
	@NamedQuery(name = "countNewMessagesByPersonId", query = "select count (*) from Messaging j where j.id.receiverID = :id and j.received = false"),
	@NamedQuery(name = "loadAllSentByPersonId", query = "select j from Messaging j where j.id.senderID = :id ORDER BY j.date DESC"),
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "messID")

public class Messaging implements IMessaging, Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_messaging_generator")
    @SequenceGenerator(name = "seq_messaging_generator", sequenceName = "SEQ_MESSAGING")
    @Column(name = "ID")
    private Long id;

    
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id")
    private IPerson person;
    
    //private IPerson person;
    //private List<IMessaging> messages =  new ArrayList<IMessaging>();
	    
	@Column(name = "SENDER_ID")
    private long senderID;
    
    @Column(name = "RECEIVER_ID")
    private long receiverID;

    @Column(name = "MESSAGE_TITLE")
    private String messageTitle;

    @Column(name = "MESSAGE_SUBJECT")
    private String messageSubject;

    @Column(name = "MESSAGE_BODY",  columnDefinition="TEXT")
    private String messageBody;

    @Column(name = "SENT")
    private Boolean sent;

    @Column(name = "RECEIVED")
    private Boolean received;
    
    @Column(name = "DATE")
    private Date date = new Date();
    
    public IPerson getPerson() {
	return person;
    }

    public void setPerson(IPerson person) {
	this.person = person;
    }
    
	public String getTitle(){
    	return messageTitle; 
    }
    
    
    public Long getMessageId() {
		return id;
	}
	
   
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
	public void setMessageId(Long id) {
		this.id = id;
	}


	public long getSenderID() {
		return senderID;
	}

	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}

	public long getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(long receiverID) {
		this.receiverID = receiverID;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	public Boolean getReceived() {
		return received;
	}

	public void setReceived(Boolean received) {
		this.received = received;
	}


	
	

	

	

}
