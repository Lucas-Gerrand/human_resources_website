package hrrss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import hrrss.dao.impl.ApplicantAnswerDAO;
import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.ISurvey;
import hrrss.model.impl.ApplicantAnswer;
import hrrss.service.IApplicantAnswerService;

public class ApplicantAnswerService implements IApplicantAnswerService {
	@Autowired
	private ApplicantAnswerDAO dao;
	
	public ApplicantAnswerDAO getDao() {
		return dao;
	}

	public void setDao(ApplicantAnswerDAO dao) {
		this.dao = dao;
	}

	

	
	@Override
	public void save(IApplicantAnswer entity) {
		dao.save((ApplicantAnswer) entity);
	}

	@Override
	public void update(IApplicantAnswer entity) {
		dao.update((ApplicantAnswer) entity);
	}

	@Override
	public void delete(IApplicantAnswer entity) {
		dao.delete((ApplicantAnswer) entity);	
	}


	@Override
	public IApplicantAnswer find(Long id) {
		return dao.findOne(id);
	}

	@Override
	public List<IApplicantAnswer> findByQuestion(Long id, long app) {
		return dao.loadAnswerByQuestion(id, app);
	}

	
	
}
