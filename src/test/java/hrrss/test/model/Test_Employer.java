package hrrss.test.model;

import static org.junit.Assert.fail;
import hrrss.model.IAddress;
import hrrss.model.IEmployer;
import hrrss.model.IPerson;

import javax.persistence.EntityTransaction;

import org.junit.Test;

public class Test_Employer extends AbstractTest {

    @Test
    public void testEmployer() {

    }

    //@Test
    public void setUp() {

	EntityTransaction tx = em.getTransaction();
	tx.begin();

	try {
	    // Addresses
	    IAddress address1 = modelFactory.createAddress();
	    IAddress address2 = modelFactory.createAddress();
	    IAddress address3 = modelFactory.createAddress();
	    IAddress address4 = modelFactory.createAddress();

	    address1.setCity("city1");
	    address1.setStreet("street1");
	    address1.setZipCode("zip1");

	    address2.setCity("city2");
	    address2.setStreet("street2");
	    address2.setZipCode("zip2");

	    address3.setCity("city3");
	    address3.setStreet("street3");
	    address3.setZipCode("zip3");

	    address4.setCity("city4");
	    address4.setStreet("street4");
	    address4.setZipCode("zip4");

	    IEmployer employer1 = modelFactory.createEmployer();
	    IEmployer employer2 = modelFactory.createEmployer();
	    IEmployer employer3 = modelFactory.createEmployer();
	    IEmployer employer4 = modelFactory.createEmployer();

	    ((IPerson) employer1).setFirstName("employer1");
	    ((IPerson) employer1).setLastName("employer1");
	    ((IPerson) employer1).setAddress(address1);

	    ((IPerson) employer2).setFirstName("employer2");
	    ((IPerson) employer2).setLastName("employer2");
	    ((IPerson) employer2).setAddress(address1);

	    ((IPerson) employer3).setFirstName("employer3");
	    ((IPerson) employer3).setLastName("employer3");
	    ((IPerson) employer3).setAddress(address1);

	    ((IPerson) employer4).setFirstName("employer4");
	    ((IPerson) employer4).setLastName("employer4");
	    ((IPerson) employer4).setAddress(address1);

	    em.persist(employer1);
	    em.persist(employer2);
	    em.persist(employer3);
	    em.persist(employer4);

	    em.flush();

	    tx.commit();

	} catch (Exception e) {
	    tx.rollback();
	    fail(e.getMessage());
	}
    }
}
