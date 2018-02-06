package hrrss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import hrrss.dao.impl.QuastionDAO;
import hrrss.model.IQuastion;
import hrrss.model.impl.Quastion;
import hrrss.service.IQuastionService;

public class QuastionService implements IQuastionService{
	
	@Autowired
	private QuastionDAO dao;
	
	public QuastionDAO getDao() {
		return dao;
	}

	public void setDao(QuastionDAO sDAO) {
		this.dao = dao;
	}

	@Override
	public List<IQuastion> loadAllQuastionBySurveyId(long survey_id) {
		return null;
	}

	@Override
	public IQuastion find(Long id) {
		return dao.findOne(id);
	}

	public void save(IQuastion entity) {
		dao.save((Quastion) entity);
	}

	@Override
	public void update(IQuastion entity) {
		dao.update((Quastion) entity);	
	}

	@Override
	public void delete(IQuastion entity) {
		dao.delete((Quastion) entity);
	}

}
