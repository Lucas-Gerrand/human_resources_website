package hrrss.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import hrrss.model.IAddress;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.IPerson;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_JobDescription {

    ModelFactory modelFactory = new ModelFactory();

    @Autowired
    PersonService personService;

    @Autowired
    JobDescriptionService jobDescriptionService;

    @Test
    public void testJobDescription() {

	IEmployer employer1 = (IEmployer) personService
		.loadPersonByUsername("username1");
	assertNotNull(employer1);
	assertEquals(((IPerson) employer1).getFirstName(), "employer1");
	assertEquals(((IPerson) employer1).getLastName(), "employer1");
	assertEquals(((IPerson) employer1).getAddress().getCity(), "city1");
	assertEquals(((IPerson) employer1).getAddress().getStreet(), "street1");
	assertEquals(((IPerson) employer1).getAddress().getZipCode(), "zip1");
	assertEquals(((IPerson) employer1).getEmail(), "employer1@employer1.at");

	IJobDescription jobDescription1 = modelFactory.createJobDescription();
	IJobDescription jobDescription2 = modelFactory.createJobDescription();
	IJobDescription jobDescription3 = modelFactory.createJobDescription();
	IJobDescription jobDescription4 = modelFactory.createJobDescription();

	jobDescription1.setJobTitle("title1");
	jobDescription1.setMainPurpose("mainPurpose1");
	jobDescription1.setQualification("qualification1");

	jobDescription2.setJobTitle("title2");
	jobDescription2.setMainPurpose("mainPurpose2");
	jobDescription2.setQualification("qualification2");

	jobDescription3.setJobTitle("title3");
	jobDescription3.setMainPurpose("mainPurpose3");
	jobDescription3.setQualification("qualification3");

	try {
	    jobDescriptionService.save(jobDescription1);
	    fail("PropertyValueException expected - Employer null");
	} catch (PropertyValueException e) {
	    // expected
	}

	jobDescription1.setEmployer(employer1);
	jobDescription2.setEmployer(employer1);
	jobDescription3.setEmployer(employer1);
	jobDescription4.setEmployer(employer1);

	jobDescriptionService.save(jobDescription1);
	jobDescriptionService.save(jobDescription2);
	jobDescriptionService.save(jobDescription3);
	jobDescriptionService.save(jobDescription4);

	List<IJobDescription> listJD = new ArrayList<IJobDescription>();
	listJD.add(jobDescription1);
	listJD.add(jobDescription2);
	listJD.add(jobDescription3);
	listJD.add(jobDescription4);

	@SuppressWarnings("unchecked")
	List<IJobDescription> listJDloaded = (List<IJobDescription>) jobDescriptionService
		.loadAllJobDescriptionByPerson(((Person) employer1)
			.getUsername());
	assertNotNull(listJDloaded);
	assertEquals(listJDloaded.size(), 4);

	listJDloaded = (List<IJobDescription>) jobDescriptionService
		.loadAllJobDescriptionByPerson("xxx");
	assertNotNull(listJDloaded);
	assertEquals(listJDloaded.size(), 0);
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

	    IEmployer employer1 = modelFactory.createEmployer();
	    IEmployer employer2 = modelFactory.createEmployer();
	    IEmployer employer3 = modelFactory.createEmployer();
	    IEmployer employer4 = modelFactory.createEmployer();

	    ((IPerson) employer1).setFirstName("employer1");
	    ((IPerson) employer1).setLastName("employer1");
	    ((IPerson) employer1).setAddress(address1);
	    ((IPerson) employer1).setUsername("username1");
	    ((IPerson) employer1).setEmail("employer1@employer1.at");

	    ((IPerson) employer2).setFirstName("employer2");
	    ((IPerson) employer2).setLastName("employer2");
	    ((IPerson) employer2).setAddress(address2);
	    ((IPerson) employer2).setUsername("username2");
	    ((IPerson) employer2).setEmail("employer2@employer2.at");

	    ((IPerson) employer3).setFirstName("employer3");
	    ((IPerson) employer3).setLastName("employer3");
	    ((IPerson) employer3).setAddress(address3);
	    ((IPerson) employer3).setUsername("username3");
	    ((IPerson) employer3).setEmail("employer3@employer3.at");

	    ((IPerson) employer4).setFirstName("employer4");
	    ((IPerson) employer4).setLastName("employer4");
	    ((IPerson) employer4).setAddress(address4);
	    ((IPerson) employer4).setUsername("username4");
	    ((IPerson) employer4).setEmail("employer4@employer4.at");

	    personService.save((Person) employer1);
	    personService.save((Person) employer2);
	    personService.save((Person) employer3);
	    personService.save((Person) employer4);

	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
