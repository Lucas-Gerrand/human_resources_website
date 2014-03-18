package hrrss.dao.impl;

import hrrss.model.IPerson;
import hrrss.model.impl.Person;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class PersonDAO extends AbstractDAO<Person> {

    public IPerson loadPersonByUsername(String username) {
	return (IPerson) getCurrentSession()
		.getNamedQuery("loadPersonByUsername")
		.setParameter("username", username).uniqueResult();
    }

    public IPerson loadPersonByEmail(String email) {
	return (IPerson) getCurrentSession().getNamedQuery("loadPersonByEmail")
		.setParameter("email", email).uniqueResult();
    }
    
    public IPerson loadPersonById(Long id) {
    	return (IPerson) getCurrentSession().getNamedQuery("loadPersonById")
    		.setParameter("id", id).uniqueResult();
        }
    
    public IPerson loadPersonByActivation(String activation) {
    	return (IPerson) getCurrentSession().getNamedQuery("loadPersonByActivation")
    		.setParameter("activation", activation).uniqueResult();
    }
    
    
}
