package hrrss.model;

import java.util.Date;
import java.util.List;


public interface IMessaging {
 
	public IPerson getPerson();
	
	public void setPerson(IPerson person);
	
	public String getTitle();
	
    public Long getMessageId();
    
	public void setMessageId(Long id);
	
	public long getSenderID();
	
	public void setSenderID(long senderID);
	
	public long getReceiverID();
	
	public void setReceiverID(long receiverID);
	
	public String getMessageTitle();
	
	public void setMessageTitle(String messageTitle);
	
	public String getMessageSubject();
	
	public void setMessageSubject(String messageSubject);
	
	public String getMessageBody();
	
	public void setMessageBody(String messageBody);
	
	public Boolean getSent();
	
	public void setSent(Boolean sent);
	
	public Boolean getReceived();

	public void setReceived(Boolean received);
	
	public Date getDate();

	public void setDate(Date date);
	
	
}