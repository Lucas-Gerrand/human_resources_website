package hrrss.test.service;

import hrrss.dao.impl.PersonDAO;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.service.IPersonService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_1 {

    ApplicationContext context = new ClassPathXmlApplicationContext(
	    "application-context.xml");
    PersonDAO ps1 = (PersonDAO) context.getBean("PersonDAO");
    IPersonService ps = (IPersonService) context.getBean("personService");

    @Test
    public void testcreateApplicant() {
	Applicant a = new Applicant();
	a.setFirstName("Applicant");

	// Applicant a1 = (Applicant)ps.createPerson(a);

	// assertEquals(a, a1);
    }

    @Test
    public void testcreateEmployer() {

	Employer e = new Employer();
	e.setFirstName("Employer");

	// Employer e1 = (Employer)ps.createPerson(e);
	// assertEquals(e, e1);

    }

    @Test
    public void testLoadPersonByEmail() {

	// Person p = ps.loadPersonByEmail("roman.dreher@gmail.com");
	// System.out.println(p.getFirstName());

    }

    @Test
    public void testFindDao() {
	// System.out.println(ps1.find(new Long(15)).getFirstName());

    }

}