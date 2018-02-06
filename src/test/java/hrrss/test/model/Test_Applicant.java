package hrrss.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import hrrss.model.IAddress;
import hrrss.model.IApplicant;
import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.IPerson;
import hrrss.service.impl.ApplicantService;
import hrrss.service.impl.PersonService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Test_Applicant extends AbstractTest {
	@Autowired
    ApplicantService appService;
	
    @Test
    public void testApplicant() {

    }

    // @Test
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

	    IApplicant applicant1 = modelFactory.createApplicant();
	    IApplicant applicant2 = modelFactory.createApplicant();
	    IApplicant applicant3 = modelFactory.createApplicant();
	    IApplicant applicant4 = modelFactory.createApplicant();
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, 1985);
	    cal.set(Calendar.MONTH, 10);
	    cal.set(Calendar.DAY_OF_MONTH, 10);
	    
	    applicant1.setHiredDate(cal.getTime());
	    applicant1.setHired(Long.valueOf(1));
	    assertEquals(applicant1.getHired(), Long.valueOf(1));
	    
	    List<IApplicant>hiredList = appService.loadHiredDateByJobID(Long.valueOf(1));
	    assertEquals(hiredList.get(0), cal.getTime());
	    //new ArrayList<IApplicant>();
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

	    ICVEducation cvEducation1 = modelFactory.createCVEducation();
	    ICVEducation cvEducation2 = modelFactory.createCVEducation();
	    ICVEducation cvEducation3 = modelFactory.createCVEducation();
	    ICVEducation cvEducation4 = modelFactory.createCVEducation();

	    cvEducation1.setStart("2005-03-21");
	    cvEducation1.setEnd("2005-03-21");

	    cvEducation2.setStart("2005-03-21");
	    cvEducation2.setEnd("2005-03-21");

	    cvEducation3.setStart("2005-03-21");
	    cvEducation3.setEnd("2005-03-21");

	    cvEducation4.setStart("2005-03-21");
	    cvEducation4.setEnd("2005-03-21");

	    List<ICVEducation> listEducation1 = new ArrayList<ICVEducation>();
	    listEducation1.add(cvEducation1);
	    listEducation1.add(cvEducation2);

	    List<ICVEducation> listEducation2 = new ArrayList<ICVEducation>();
	    listEducation2.add(cvEducation3);
	    listEducation2.add(cvEducation4);

	    ICV cv1 = modelFactory.createCV();
	    ICV cv2 = modelFactory.createCV();

	    cv1.setEducations(listEducation1);
	    cv2.setEducations(listEducation2);

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
