package hrrss.service;

import hrrss.model.IPerson;

import java.util.List;

public interface IPersonService {

    public IPerson loadPersonByUsername(String username);

    public IPerson loadPersonByEmail(String email);

    public IPerson login(String email, String password);

    public void save(IPerson entity);

    public void update(IPerson entity);

    public void delete(IPerson entity);

    public void deleteById(Long id);

    public List<IPerson> findAll();

    public IPerson find(Long id);

}
