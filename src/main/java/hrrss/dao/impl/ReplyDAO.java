package hrrss.dao.impl;

import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.ICV;
import hrrss.model.IMessaging;
import hrrss.model.IReply;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Reply;


import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class ReplyDAO extends AbstractDAO<Reply> {

	 public List<IReply> loadAllRepliesByPersonId(Long id) {
			List<IReply> listReplies = (List<IReply>)getCurrentSession()
					.getNamedQuery("loadAllRepliesByPersonId").setParameter("id", id).list();
			//loadAllRepliesByPersonId
			return listReplies;
		 	/*Query query = getCurrentSession().getNamedQuery("loadAllRepliesByPersonId");
			query.setParameter("id", id);

			return query.list();*/
		    }

	 
	//loadHiredDateByJobID
}
