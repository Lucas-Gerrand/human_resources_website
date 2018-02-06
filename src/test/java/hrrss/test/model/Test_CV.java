package hrrss.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import hrrss.model.CVSkillType;
import hrrss.model.IApplicant;
import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.IPerson;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.ApplicantService;
import hrrss.service.impl.CVService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_CV {

    ModelFactory modelFactory = new ModelFactory();

    @Autowired
    private CVService service;

    @Autowired
    private ApplicantService as;

    @Test
    public void testCV() {

	List<ICV> listCV1 = service.loadCVByUsername("cv_test_user1");
	assertNotNull(listCV1);
	assertEquals(2, listCV1.size());
	assertNotNull(listCV1.get(0));
	assertNotNull(listCV1.get(1));

	ICV cv1 = listCV1.get(0);

	assertNotNull(cv1.getEducations());
	assertNotNull(cv1.getExperiences());

	assertEquals("pmEmail@gmx.at", cv1.getPersonalEmail());
	assertEquals("http:\\website.at", cv1.getPersonalWebsite());
	assertEquals("Austria", cv1.getNationality());

	assertNotNull(cv1.getSkills());
	List<ICVSkill> skills = cv1.getSkills();
	assertEquals(5, skills.size());

	assertNotNull(cv1.getExperiences());
	List<ICVExperience> experiences = cv1.getExperiences();
	assertEquals(4, experiences.size());
	ICVExperience experience1 = experiences.get(0);
	assertEquals("Description working experience",
		experience1.getDescription());

	ICVExperience experience2 = experiences.get(1);
	assertEquals("Description working experience",
		experience2.getDescription());

	assertNotNull(cv1.getEducations());
	List<ICVEducation> educations = cv1.getEducations();
	assertEquals(2, educations.size());

	List<ICV> listCV2 = service.loadCVByUsername("cv_test_user2");
	assertNotNull(listCV2);
	assertEquals(2, listCV2.size());
	assertNotNull(listCV2.get(0));
	assertNotNull(listCV2.get(1));

	ICV cv2 = listCV2.get(0);
	assertNotNull(cv2.getSkills());
	assertNotNull(cv2.getExperiences());
	assertNotNull(cv2.getEducations());

	List<ICV> listCV3 = service.loadCVByUsername("cv_test_user3");
	assertNotNull(listCV3);
	assertEquals(0, listCV3.size());

	service.deleteCVExperienceById(experience2.getId());
	List<ICV> listCV4 = service.loadCVByUsername("cv_test_user1");
	assertEquals(3, listCV4.get(0).getExperiences().size());

	service.deleteCVExperience(experience1);
	List<ICV> listCV5 = service.loadCVByUsername("cv_test_user1");
	assertEquals(2, listCV5.get(0).getExperiences().size());

	service.deleteCVEducation(educations.get(0));
	List<ICV> listCV6 = service.loadCVByUsername("cv_test_user1");
	assertEquals(1, listCV6.get(0).getEducations().size());

    }

    @Before
    public void setUp() {

	try {

	    Calendar cal = Calendar.getInstance();

	    IApplicant applicant1 = modelFactory.createApplicant();
	    ((IPerson) applicant1).setEmail("cv_test1@email.at");
	    ((IPerson) applicant1).setUsername("cv_test_user1");
	    ((IPerson) applicant1).setActivation("done");
	    ((IPerson) applicant1)
		    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
	    ((IPerson) applicant1).setFirstName("Max");
	    ((IPerson) applicant1).setLastName("Mustermann");
	    ((IPerson) applicant1).setBirthday("1985-10-20");
	    
	    IApplicant applicant2 = modelFactory.createApplicant();
	    ((IPerson) applicant2).setEmail("cv_test2@email.at");
	    ((IPerson) applicant2).setUsername("cv_test_user2");
	    ((IPerson) applicant1).setActivation("done");
	    cal.set(Calendar.YEAR, 1985);
	    cal.set(Calendar.MONTH, 10);
	    cal.set(Calendar.DAY_OF_MONTH, 10);
	    ((IPerson) applicant2).setBirthday("1985-10-20");
	    
	    IApplicant applicant3 = modelFactory.createApplicant();
	    ((IPerson) applicant3).setEmail("MaxMusterman@gmail.com");
	    ((IPerson) applicant3).setUsername("MaxMusterman");
	    ((IPerson) applicant3).setActivation("done");
	    ((IPerson) applicant3)
		    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
	    ((IPerson) applicant3).setFirstName("Max");
	    ((IPerson) applicant3).setLastName("Mustermann");
	    ((IPerson) applicant3).setBirthday("1985-10-20");
	    
	    
	    IApplicant applicant4 = modelFactory.createApplicant();
	    ((IPerson) applicant4).setEmail("samSmatches@email.at");
	    ((IPerson) applicant4).setUsername("samSmatches");
	    ((IPerson) applicant4).setActivation("done");
	    cal.set(Calendar.YEAR, 1985);
	    cal.set(Calendar.MONTH, 10);
	    cal.set(Calendar.DAY_OF_MONTH, 10);
	    ((IPerson) applicant4).setBirthday("1985-10-20");
	    ((IPerson) applicant4)
	    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
	    ((IPerson) applicant4).setFirstName("Sam");
	    ((IPerson) applicant4).setLastName("Snatches");
	    
	    
	    IApplicant applicant5 = modelFactory.createApplicant();
	    ((IPerson) applicant5).setEmail("RachelMcGee@hotmail.at");
	    ((IPerson) applicant5).setUsername("RachelMcGee");
	    ((IPerson) applicant5).setActivation("done");
	    ((IPerson) applicant5)
		    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
	    ((IPerson) applicant5).setFirstName("Test2");
	    ((IPerson) applicant5).setLastName("Test2last");
	    ((IPerson) applicant5).setBirthday("1985-10-20");
	    
	    IApplicant applicant6 = modelFactory.createApplicant();
	    ((IPerson) applicant6).setEmail("BrianSpillner@email.at");
	    ((IPerson) applicant6).setUsername("BrianSpillner");
	    ((IPerson) applicant6).setActivation("done");
	    ((IPerson) applicant6)
		    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
	    ((IPerson) applicant6).setFirstName("Brian");
	    ((IPerson) applicant6).setLastName("Spillner");
	    ((IPerson) applicant6).setBirthday("1985-10-20");
	    
	    
	    IApplicant applicant7 = modelFactory.createApplicant();
	    ((IPerson) applicant7).setEmail("cv_test4@email.at");
	    ((IPerson) applicant7).setUsername("cv_test_user4");
	    ((IPerson) applicant7).setActivation("done");
	    ((IPerson) applicant7)
		    .setPassword("7d5825412ad70930a249f706922146ba694b3448");
	    ((IPerson) applicant7).setFirstName("Test3");
	    ((IPerson) applicant7).setLastName("Test3last");
	    ((IPerson) applicant7).setBirthday("1985-10-20");
	    
	    
	    as.save(applicant1);
	    as.save(applicant2);
	    as.save(applicant3);
	    as.save(applicant4);
	    as.save(applicant5);
	    as.save(applicant6);
	    as.save(applicant7);
	   
	    ICV cv1 = modelFactory.createCV();
	    ICV cv2 = modelFactory.createCV();
	    ICV cv3 = modelFactory.createCV();
	    ICV cv4 = modelFactory.createCV();
	    ICV cv5 = modelFactory.createCV();
	    ICV cv6 = modelFactory.createCV();
	    ICV cv7 = modelFactory.createCV();
	    
	    ICVSkill skill1 = modelFactory.createCVSkill();
	    skill1.setSkillType("COMPUTER_SKILLS");
	    skill1.setDescription("C++, JAVA, ...");
	    skill1.setCv(cv1);
	    cv1.addSkill(skill1);

	    ICVSkill skill2 = modelFactory.createCVSkill();
	    skill2.setSkillType("DRIVING_LICENCE");
	    skill2.setDescription("Car: ClassB, Truck ClassC");
	    skill2.setCv(cv1);
	    cv1.addSkill(skill2);

	    ICVSkill skill3 = modelFactory.createCVSkill();
	    skill3.setSkillType("OTHER_SKILLS");
	    skill3.setDescription("Description other skills");
	    skill3.setCv(cv1);
	    cv1.addSkill(skill3);

	    ICVSkill skill4 = modelFactory.createCVSkill();
	    skill4.setSkillType("JOB_RELATED_SKILLS");
	    skill4.setDescription("Description job related skills");
	    skill4.setCv(cv1);
	    cv1.addSkill(skill4);

	    ICVSkill skill5 = modelFactory.createCVSkill();
	    skill5.setSkillType("MANAGER_SKILLS");
	    skill5.setDescription("Description manager skills");
	    skill5.setCv(cv1);
	    cv1.addSkill(skill5);

	    ICVExperience experience1 = modelFactory.createCVExperience();
	    cal.set(Calendar.YEAR, 1990);
	    experience1.setStart("2005-03-21");
	    cal.set(Calendar.YEAR, 1991);
	    experience1.setEnd("2005-03-21");
	    experience1.setTitle("DEVELOPER");
	    experience1.setCompany("A1 TELEKOM");
	    experience1.setDescription("Description working experience");
	    experience1.setCv(cv1);
	    cv1.addExperience(experience1);

	    ICVExperience experience2 = modelFactory.createCVExperience();
	    cal.set(Calendar.YEAR, 2000);
	    experience2.setStart("2005-03-21");
	    cal.set(Calendar.YEAR, 2001);
	    experience2.setEnd("2005-03-21");
	    experience2.setTitle("DEVELOPER");
	    experience2.setCompany("A1 TELEKOM");
	    experience2.setDescription("Description working experience");
	    experience2.setCv(cv1);
	    cv1.addExperience(experience2);

	    ICVExperience experience3 = modelFactory.createCVExperience();
	    cal.set(Calendar.YEAR, 2004);
	    experience3.setStart("2005-03-21");
	    cal.set(Calendar.YEAR, 2005);
	    experience3.setEnd("2005-03-21");
	    experience3.setTitle("Developer");
	    experience3.setCompany("A1 Telekom");
	    experience3.setDescription("Description working experience");
	    experience3.setCv(cv1);
	    cv1.addExperience(experience3);

	    ICVExperience experience4 = modelFactory.createCVExperience();
	    experience4.setCompany("COMPANY XYZ");
	    experience4.setTitle("Buisness Analyst");
	    cal.set(Calendar.YEAR, 2006);
	    experience4.setStart("2005-03-21");
	    cal.set(Calendar.YEAR, 2007);
	    experience4.setEnd("2005-03-21");
	    experience4.setDescription("Description working experience");
	    experience4.setCv(cv1);
	    cv1.addExperience(experience4);

	    ICVEducation education1 = modelFactory.createCVEducation();
	    cal.set(Calendar.YEAR, 2004);
	    education1.setStart("2005-03-21");
	    cal.set(Calendar.YEAR, 2008);
	    education1.setEnd("2005-03-21");
	    education1.setDescription("Description of Education");
	    education1.setCv(cv1);
	    education1.setFacility("TU WIEN");
	    education1.setLocation("WIEN");
	    cv1.addEducation(education1);

	    ICVEducation education2 = modelFactory.createCVEducation();
	    education2.setStart("2005-03-21");
	    education2.setEnd("2005-03-21");
	    education2
		    .setDescription("Description of Education Education "
			    + "Education Education Education Education Education Education ");
	    education2.setCv(cv1);
	    education2.setFacility("TUWIEN");
	    education2.setLocation("WIEN");
	    cv1.addEducation(education2);

	    cv1.setPersonalEmail("pmEmail@gmx.at");
	    cv1.setPersonalWebsite("http:\\website.at");
	    cv1.setNationality("Austria");
	    cv1.setApplicant(applicant1);

	    cv2.setApplicant(applicant1);
	    cv3.setApplicant(applicant2);
	    cv4.setApplicant(applicant2);
	    
	    service.saveCV(cv1);
	    service.saveCV(cv2);
	    service.saveCV(cv3);
	    service.saveCV(cv4);

	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
