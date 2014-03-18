package hrrss.service;

import hrrss.model.IJobDescription;

import java.util.List;

public interface IJobDescriptionService {

    public List<IJobDescription> loadAllJobDescriptionByPerson(String username);

    public IJobDescription find(Long id);

    public void save(IJobDescription entity);

    public void update(IJobDescription entity);

    public void delete(IJobDescription entity);

}
