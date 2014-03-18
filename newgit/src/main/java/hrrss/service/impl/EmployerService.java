package hrrss.service.impl;

import hrrss.dao.impl.EmployerDAO;
import hrrss.model.IPerson;
import hrrss.model.impl.Person;

// @Component("employerService")
public class EmployerService {

    // @Autowired
    private EmployerDAO dao;

    public Person loadPersonByUsername(String username) {

	return new Person();
    }

    public void savePerson(IPerson person) {
	// dao.save((Person) person);
    }

    public void deletePerson(Person person) {
	// dao.delete(person);
    }

    public Person loadPersonByEmail(String email) {
	// return (Person) dao.loadPersonByEmail(email);
	return new Person();
    }

    public Person login(String email, String password) {
	Person person = loadPersonByEmail(email);
	if (person != null && person.getPassword().equals(password)) {
	    return person;
	}
	return null;
    }
}