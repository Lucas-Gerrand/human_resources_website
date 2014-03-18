package hrrss.service.impl;

import java.util.List;

import hrrss.dao.impl.ApplicantDAO;
import hrrss.dao.impl.ReplyDAO;
import hrrss.model.IApplicant;
import hrrss.model.IMessaging;
import hrrss.model.IPerson;
import hrrss.model.IReply;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Person;
import hrrss.model.impl.Reply;
import hrrss.service.IApplicantService;
import hrrss.service.IReplyService;

// @Component("personService")
public class ReplyService implements IReplyService {

    private ReplyDAO dao;

    public ReplyDAO getDao() {
	return dao;
    }

    public void setDao(ReplyDAO dao) {
	this.dao = dao;
    }
    
    public void update(IReply entity) {
	dao.update((Reply) entity);
    }

    public void deleteById(Long id) {
	dao.deleteById(id);
    }

	@Override
	public void save(IReply entity) {
		dao.save((Reply)entity);
		
	}

	@Override
	public void delete(IReply entity) {
		dao.delete((Reply)entity);
		
	}

	//@Override
	/*public List<IReply> loadAllRepliesByPersonId(Long id) {
		return (List<IReply>) dao.loadAllRepliesByPersonId(id);
	}*/

	

	

	
}