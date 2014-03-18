package hrrss.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import hrrss.model.IAddress;
import hrrss.model.IEmployer;
import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.IMessaging;
import hrrss.model.IPerson;
import hrrss.model.IReply;
import hrrss.model.impl.Employer;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.model.impl.SavedApplicants;
import hrrss.service.ISavedApplicantService;
import hrrss.service.impl.EmployerService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SavedApplicantService;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_Person {

    ModelFactory modelFactory = new ModelFactory();

    @Autowired
    private PersonService service;
    
    @Autowired
    private SavedApplicantService savedApplicantService;
    
    
    
    @Test
    public void testPerson() {

	IPerson person1 = ((PersonService) service)
		.loadPersonByUsername("username1");
	assertNotNull(person1);
	assertEquals(person1.getFirstName(), "person1");
	assertEquals(person1.getLastName(), "person1");
	assertEquals(person1.getAddress().getCity(), "city1");
	assertEquals(person1.getAddress().getStreet(), "street1");
	assertEquals(person1.getAddress().getZipCode(), "zip1");
	assertEquals(person1.getEmail(), "email1@email1.at");
	

	  
		Employer employer1 = (Employer) service.find(Long.valueOf(5));
	
	    IEmployerSavedApplicants savedApplicants = modelFactory.createSavedApplicants();
	    
	    savedApplicants.setApplicantID(Long.valueOf(20));
	    
	    savedApplicants.setPerson((Person) employer1);
	    savedApplicantService.update(savedApplicants);
	    
	    List<IEmployerSavedApplicants>listApplicants = savedApplicantService.loadAllSavedApplicantsByEmployerID(
	    		Long.valueOf(5));
	    assertEquals(5, listApplicants.size());
	   /* try {
	    	savedApplicantService.update(savedApplicants);
		    
		    fail("PersistentObjectException expected");
		} catch (DataIntegrityViolationException e) {
		    // expected
		}
	    */
	    
	    
	    /*savedApplicants.setSavedApplicantID(Long.valueOf(7));
	    
	    IEmployer employer1 = (IEmployer) savedApplicants;
	    
	    employer1.setCompanyname("The jukebox");
	    employer1.setContact("23131");
	    ((Person) employer1).setFirstName("ployer1");
	    ((Person) employer1).setLastName("employ1");
	    ((Person) employer1).setAddress(address1);
	    ((Person) employer1).setEmail("emplosdddr@emplor.com");
	    
	    savedApplicantService.update((IEmployerSavedApplicants) employer1);
    
	    IEmployerSavedApplicants savedApplicants2 = modelFactory.createSavedApplicants();
	    savedApplicants2.setSavedApplicantID(Long.valueOf(17));
	    employer1 = (IEmployer)savedApplicants2;
	    savedApplicantService.save((IEmployerSavedApplicants) employer1);*/
	    
	IPerson person2 = ((PersonService) service)
		.loadPersonByUsername("username2");
	assertNotNull(person2);
	assertEquals(person2.getFirstName(), "person2");
	assertEquals(person2.getLastName(), "person2");
	assertEquals(person2.getAddress().getCity(), "city2");
	assertEquals(person2.getAddress().getStreet(), "street2");
	assertEquals(person2.getAddress().getZipCode(), "zip2");
	assertEquals(person2.getEmail(), "email2@email2.at");

	IPerson person3 = ((PersonService) service)
		.loadPersonByUsername("username3");
	assertNotNull(person3);
	assertEquals(person3.getFirstName(), "person3");
	assertEquals(person3.getLastName(), "person3");
	assertEquals(person3.getAddress().getCity(), "city3");
	assertEquals(person3.getAddress().getStreet(), "street3");
	assertEquals(person3.getAddress().getZipCode(), "zip3");
	assertEquals(person3.getEmail(), "email3@email3.at");

	IPerson person4 = ((PersonService) service)
		.loadPersonByUsername("username4");
	assertNotNull(person4);
	assertEquals(person4.getFirstName(), "person4");
	assertEquals(person4.getLastName(), "person4");
	assertEquals(person4.getAddress().getCity(), "city4");
	assertEquals(person4.getAddress().getStreet(), "street4");
	assertEquals(person4.getAddress().getZipCode(), "zip4");
	assertEquals(person4.getEmail(), "email4@email4.at");

	IPerson person5 = ((PersonService) service)
		.loadPersonByEmail("email1@email1.at");
	assertNotNull(person5);
	assertEquals(person5.getFirstName(), "person1");
	assertEquals(person5.getLastName(), "person1");
	assertEquals(person5.getAddress().getCity(), "city1");
	assertEquals(person5.getAddress().getStreet(), "street1");
	assertEquals(person5.getAddress().getZipCode(), "zip1");
	assertEquals(person5.getEmail(), "email1@email1.at");

	IPerson person6 = ((PersonService) service)
		.loadPersonByEmail("email2@email2.at");
	assertNotNull(person6);
	assertEquals(person6.getFirstName(), "person2");
	assertEquals(person6.getLastName(), "person2");
	assertEquals(person6.getAddress().getCity(), "city2");
	assertEquals(person6.getAddress().getStreet(), "street2");
	assertEquals(person6.getAddress().getZipCode(), "zip2");
	assertEquals(person6.getEmail(), "email2@email2.at");

	IPerson person7 = ((PersonService) service)
		.loadPersonByEmail("email3@email3.at");
	assertNotNull(person7);
	assertEquals(person7.getFirstName(), "person3");
	assertEquals(person7.getLastName(), "person3");
	assertEquals(person7.getAddress().getCity(), "city3");
	assertEquals(person7.getAddress().getStreet(), "street3");
	assertEquals(person7.getAddress().getZipCode(), "zip3");
	assertEquals(person7.getEmail(), "email3@email3.at");

	IPerson person8 = ((PersonService) service)
		.loadPersonByEmail("email4@email4.at");
	assertNotNull(person8);
	assertEquals(person8.getFirstName(), "person4");
	assertEquals(person8.getLastName(), "person4");
	assertEquals(person8.getAddress().getCity(), "city4");
	assertEquals(person8.getAddress().getStreet(), "street4");
	assertEquals(person8.getAddress().getZipCode(), "zip4");
	assertEquals(person8.getEmail(), "email4@email4.at");

	List<IPerson> listPerson = service.findAll();
	assertEquals(11, listPerson.size());

	service.delete((Person) person1);
	listPerson = service.findAll();
	//assertEquals(9, listPerson.size());

	service.deleteById(person2.getId());
	listPerson = service.findAll();
	//assertEquals(8, listPerson.size());

	IPerson person9 = modelFactory.createPerson();
	person9.setEmail("email4@email4.at");

	try {
	    service.update((Person) person9);
	    
	    fail("PersistentObjectException expected");
	} catch (DataIntegrityViolationException e) {
	    // expected
	}
    }

    @Before
    public void setUp() {

	try {
	    // Address
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

	    IPerson person1 = modelFactory.createPerson();
	    IPerson person2 = modelFactory.createPerson();
	    IPerson person3 = modelFactory.createPerson();
	    IPerson person4 = modelFactory.createPerson();

	    person1.setFirstName("person1");
	    person1.setLastName("person1");
	    person1.setAddress(address1);
	    person1.setUsername("username1");
	    person1.setEmail("email1@email1.at");

	    person2.setFirstName("person2");
	    person2.setLastName("person2");
	    person2.setAddress(address2);
	    person2.setUsername("username2");
	    person2.setEmail("email2@email2.at");

	    person3.setFirstName("person3");
	    person3.setLastName("person3");
	    person3.setAddress(address3);
	    person3.setUsername("username3");
	    person3.setEmail("email3@email3.at");

	    person4.setFirstName("person4");
	    person4.setLastName("person4");
	    person4.setAddress(address4);
	    person4.setUsername("username4");
	    person4.setEmail("email4@email4.at");
	    

		IAddress employerAddress = modelFactory.createAddress();
		employerAddress.setCity("city1");
		employerAddress.setStreet("street1");
		employerAddress.setZipCode("zip1");
		
		IEmployer employer1 = modelFactory.createEmployer();
		employer1.setCompanyname("The jukebox");
		    employer1.setContact("23131");
		    ((Person) employer1).setFirstName("ployer1");
		    ((Person) employer1).setLastName("employ1");
		    ((Person) employer1).setAddress(employerAddress);
		    ((Person) employer1).setEmail("emplosdsdsdsddr@emplor.com");
		
		service.save((Person) employer1);
	    service.save((Person) person1);
	    service.save((Person) person2);
	    service.save((Person) person3);
	    service.save((Person) person4);

	} catch (Exception e) {
	    System.out.println(e);
	}
    }

}
