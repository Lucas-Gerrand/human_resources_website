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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "REPLY")
@DiscriminatorValue("1")
@NamedQueries({
	@NamedQuery(name = "loadAllRepliesByPersonId", query = "select a from Reply a where a.msgID = :id")

	
})

public class Reply extends Messaging implements IReply, IMessaging, Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Column(name = "PersonID")
    private Long personID;
    
    
    @Column(name = "MessageID")
    private Long msgID;
    
    public void setReplyPersonId(Long personID){
    	this.personID = personID;
    }
    public Long getReplyPersonId() {
		return personID;
	}
    
    public void setReplyMessageId(Long msgID){
    	this.msgID = msgID;
    }
    
    public Long getReplyMessageId(){
    	return msgID;
    }
	
}
