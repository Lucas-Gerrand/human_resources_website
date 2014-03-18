package hrrss.test.model;

import static org.junit.Assert.fail;
import hrrss.model.IAddress;
import hrrss.model.IApplicant;
import hrrss.model.IPerson;
import hrrss.model.impl.ModelFactory;

import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityTransaction;

import org.junit.Test;

public class Insert_TestData extends AbstractTest {

    @Test
    public void testEntities() throws NoSuchAlgorithmException {
	ModelFactory modelFactory = new ModelFactory();

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

	    IApplicant applicant1 = modelFactory.createApplicant();
	    IApplicant applicant2 = modelFactory.createApplicant();
	    IApplicant applicant3 = modelFactory.createApplicant();
	    IApplicant applicant4 = modelFactory.createApplicant();

	    ((IPerson) applicant1).setFirstName("applicant1");
	    ((IPerson) applicant1).setLastName("applicant1");
	    ((IPerson) applicant1).setAddress(address1);

	    ((IPerson) applicant2).setFirstName("applicant2");
	    ((IPerson) applicant2).setLastName("applicant2");
	    ((IPerson) applicant2).setAddress(address1);

	    ((IPerson) applicant3).setFirstName("applicant3");
	    ((IPerson) applicant3).setLastName("applicant3");
	    ((IPerson) applicant3).setAddress(address1);

	    ((IPerson) applicant4).setFirstName("applicant4");
	    ((IPerson) applicant4).setLastName("applicant4");
	    ((IPerson) applicant4).setAddress(address1);

	    em.persist(applicant1);
	    em.persist(applicant2);
	    em.persist(applicant3);
	    em.persist(applicant4);

	    em.flush();

	    tx.commit();

	} catch (Exception e) {
	    tx.rollback();
	    fail(e.getMessage());
	}

    }
}
