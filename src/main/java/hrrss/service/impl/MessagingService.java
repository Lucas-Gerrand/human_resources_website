package hrrss.service.impl;

import hrrss.dao.impl.JobDescriptionDAO;
import hrrss.dao.impl.MessagingDAO;
import hrrss.dao.impl.CVDAO;
import hrrss.dao.impl.CVEducationDAO;
import hrrss.dao.impl.CVExperienceDAO;
import hrrss.dao.impl.CVSkillDAO;
import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.IJobDescription;
import hrrss.model.IMessaging;
import hrrss.model.impl.CV;
import hrrss.model.impl.CVEducation;
import hrrss.model.impl.CVExperience;
import hrrss.model.impl.CVSkill;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.Messaging;
import hrrss.service.ICVService;
import hrrss.service.IMessagingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagingService implements IMessagingService {

	public MessagingDAO getMsgDAO() {
		return MsgDAO;
	}

	public void setMsgDAO(MessagingDAO msgDAO) {
		MsgDAO = msgDAO;
	}

	private MessagingDAO MsgDAO;	

	    
	@Override
	public List<IMessaging> loadAllMessagesByPersonId(Long id) {
		return (List<IMessaging>) MsgDAO.loadAllMessagesByPersonId(id);
	
	}
	
	public List<IMessaging> loadAllMessages(Long id) {
    	return (List<IMessaging>) MsgDAO.loadAllMessages(id);
    }

	
	
	@Override
	public IMessaging find(Long id) {
		return MsgDAO.findOne(id);
	}

	@Override
	public void save(IMessaging entity) {
		MsgDAO.save((Messaging) entity);
	}

	@Override
	public void update(IMessaging entity) {
		MsgDAO.update((Messaging) entity);
		
	}

	@Override
	public void delete(IMessaging entity) {
		MsgDAO.delete((Messaging) entity);
	}

	@Override
	public List<IMessaging> loadAllInboxByPersonId(Long id) {
		return (List<IMessaging>) MsgDAO.loadAllInboxByPersonId(id);
	}

	@Override
	public List<IMessaging> loadAllRepliesByPersonId(Long id) {
		return (List<IMessaging>) MsgDAO.loadAllRepliesByPersonId(id);
	}

	@Override
	public Long countNewMessagesByPersonId(Long id) {
		return MsgDAO.countNewMessagesByPersonId(id);
	}

	@Override
	public List<IMessaging> loadAllSentByPersonId(Long id) {
		return (List<IMessaging>) MsgDAO.loadAllSentByPersonId(id);
	}
	
	

}