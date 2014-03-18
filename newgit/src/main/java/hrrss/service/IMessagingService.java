package hrrss.service;

import hrrss.model.IMessaging;
import hrrss.model.IReply;

import java.util.List;

public interface IMessagingService {

	
	  	public List<IMessaging> loadAllMessagesByPersonId(Long id);
	  	
	  	public List<IMessaging> loadAllMessages(Long id); 
	  	
	  	public List<IMessaging> loadAllInboxByPersonId(Long id);
	  	
	  	public List<IMessaging> loadAllSentByPersonId(Long id);
	  	
	  	public Long countNewMessagesByPersonId(Long id);

	    public IMessaging find(Long id);

	    public void save(IMessaging entity);

	    public void update(IMessaging entity);

	    public void delete(IMessaging entity);
	    
	    public List<IMessaging> loadAllRepliesByPersonId(Long id);
	    
	    
}
