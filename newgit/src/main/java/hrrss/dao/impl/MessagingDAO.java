package hrrss.dao.impl;

import hrrss.model.IMessaging;
import hrrss.model.IReply;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.Messaging;

import java.util.List;

import org.hibernate.Query;

public class MessagingDAO extends AbstractDAO<Messaging> {
 
	
    @SuppressWarnings("unchecked")
    public List<IMessaging> loadAllRepliesByPersonId(Long id) {
		List<IMessaging> listReplies = (List<IMessaging>)getCurrentSession()
				.getNamedQuery("loadAllRepliesByPersonId").setParameter("id", id).list();
		//loadAllRepliesByPersonId
		return listReplies;
	 	/*Query query = getCurrentSession().getNamedQuery("loadAllRepliesByPersonId");
		query.setParameter("id", id);

		return query.list();*/
	    }

    public List<IMessaging> loadAllMessagesByPersonId(Long id) {
	Query query = getCurrentSession().getNamedQuery("loadAllMessagesByPersonId");
	query.setParameter("id", id);

	return query.list();
    }
    
    public Long countNewMessagesByPersonId(Long id){
    Query query = getCurrentSession().getNamedQuery("countNewMessagesByPersonId");
    query.setParameter("id", id);
    return (Long) query.uniqueResult();
    }
    
    public List<IMessaging> loadAllInboxByPersonId(Long id) {
	Query query = getCurrentSession().getNamedQuery("loadAllInboxByPersonId");
	query.setParameter("id", id);

	return query.list();
	
	
    }
    public List<IMessaging> loadAllSentByPersonId(Long id) {
    	Query query = getCurrentSession().getNamedQuery("loadAllSentByPersonId");
    	query.setParameter("id", id);
    	return query.list();
        }
     
    @SuppressWarnings("unchecked")
    public List<IMessaging> loadAllMessages(Long id) {
    	Query query = getCurrentSession().getNamedQuery("loadAllMessages");
    	query.setParameter("id", id);
    	return query.list();
    }
    
}
