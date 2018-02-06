package hrrss.service.impl;

import hrrss.dao.impl.PersonDAO;
import hrrss.model.IPerson;
import hrrss.model.impl.Person;
import hrrss.service.IPersonService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

// @Component("personService")
public class PersonService implements IPersonService {

    @Autowired
    private PersonDAO dao;

    public PersonDAO getDao() {
	return dao;
    }

    public void setDao(PersonDAO dao) {
	this.dao = dao;
    }

    public IPerson loadPersonByUsername(String username) {
	return dao.loadPersonByUsername(username);
    }

    public IPerson loadPersonByEmail(String email) {
	return dao.loadPersonByEmail(email);
    }
    
    public IPerson loadPersonByActivation(String act) {
    	return dao.loadPersonByActivation(act);
        }

    public IPerson login(String email, String password) {
	IPerson person = loadPersonByEmail(email);
	if (person != null && person.getPassword().equals(password)) {
	    return person;
	}
	return null;
    }

    public void save(IPerson entity) {
	dao.save((Person) entity);
    }

    public void update(IPerson entity) {
	dao.update((Person) entity);
    }

    public Person find(Long id) {
    	return (Person) dao.loadPersonById(id);
    }
    

    public void deleteById(Long id) {
	dao.deleteById(id);
    }

    public List<IPerson> findAll() {
	dao.setClazz(Person.class);
	List<IPerson> persons = new ArrayList<IPerson>();
	for (Person person : dao.findAll()) {
	    persons.add(person);
	}
	return persons;
    }

    @Override
    public void delete(IPerson entity) {
	dao.delete((Person) entity);
    }
}