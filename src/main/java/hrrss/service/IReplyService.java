package hrrss.service;

import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.ICV;
import hrrss.model.IJobDescription;
import hrrss.model.IMessaging;
import hrrss.model.IReply;

public interface IReplyService {

	public void save(IReply entity);

    public void update(IReply entity);

    public void delete(IReply entity);
    
    
}
