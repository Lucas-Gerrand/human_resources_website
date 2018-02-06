package hrrss.service;

import hrrss.model.IQuastion;
import hrrss.model.ISurvey;

import java.util.List;

public interface IQuastionService {
	public List<IQuastion> loadAllQuastionBySurveyId(long survey_id);

    public IQuastion find(Long id);

    public void save(IQuastion entity);

    public void update(IQuastion entity);

    public void delete(IQuastion entity);

}
