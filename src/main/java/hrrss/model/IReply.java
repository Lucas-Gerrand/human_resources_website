package hrrss.model;

import java.util.Date;
import java.util.List;


public interface IReply {
 
	public void setReplyPersonId(Long personID);
	
    public Long getReplyPersonId();
    
    public void setReplyMessageId(Long msgID);
    
    public Long getReplyMessageId();

}
