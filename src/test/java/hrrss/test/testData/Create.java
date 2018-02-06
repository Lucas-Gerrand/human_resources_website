package hrrss.test.testData;

import static org.junit.Assert.assertTrue;
import hrrss.model.IApplicant;
import hrrss.model.IMessaging;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.ApplicantToSurvey;
import hrrss.model.impl.CV;
import hrrss.model.impl.CVEducation;
import hrrss.model.impl.CVExperience;
import hrrss.model.impl.CVSkill;
import hrrss.model.impl.Employer;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.model.impl.Quastion;
import hrrss.model.impl.SubAnswer;
import hrrss.model.impl.Survey;
import hrrss.service.impl.CVService;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.util.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Create {

	private static ModelFactory mf;

	@Autowired
	private PersonService personService;

	@Autowired
	private CVService cvService;

	@Autowired
	private JobDescriptionService jobService;

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private MessagingService messagingService;
	
	@Test
	public void insertApplicant1() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Bregenz",
					"Arlbergstrasse 92", "6900", "1986-12-22",
					"roman.dreher@gmail.com", "Roman", "Dreher", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.vol.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1990-10-01", "2010-05-01",
					"Java Programmer", "A1",
					"Working as java programmer for a1");
			CVExperience exp2 = mf.createExp(cv, "2010-06-01", "2013-04-01",
					"Java Programmer", "H&M", "Java Webdevelopment Java EE");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Bregenz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Bregenz", "BG Blumenstrasse", "Matura");
			CVEducation edu3 = mf.createEdu(cv, "2005-10-01", "2013-07-01",
					"Wien", "TU Wien", "Software and Information Engineering");

			CVSkill skill1 = mf.createSkill(cv, "Computer Skills",
					"JAVA, PHP, C, C++, JS, Ruby, Perl,..");
			CVSkill skill2 = mf.createSkill(cv, "Driving Skills", "A, B");
			CVSkill skill3 = mf.createSkill(cv, "Other Skills", "Train driver");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);
			cvService.saveCVExperience(exp2);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);
			cvService.saveCVEducation(edu3);

			cvService.saveCVSkill(skill1);
			cvService.saveCVSkill(skill2);
			cvService.saveCVSkill(skill3);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant2() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Bregenz",
					"Gerbergasse 18", "6900", "1986-12-22", "jogi@gmail.com",
					"Johannes", "Rauch", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.rauch.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1990-10-01", "2010-05-01",
					"PHP Programmer", "A1", "Working as php programmer for a1");
			CVExperience exp2 = mf.createExp(cv, "2010-06-01", "2013-04-01",
					"PHP Programmer", "H&M", "PHP Webdevelopment");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Bregenz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Bregenz", "BG Blumenstrasse", "Matura");
			CVEducation edu3 = mf.createEdu(cv, "2005-10-01", "2013-07-01",
					"Wien", "TU Wien", "Software and Information Engineering");

			CVSkill skill1 = mf.createSkill(cv, "Computer Skills", "PHP, Perl");
			CVSkill skill2 = mf.createSkill(cv, "Driving Skills", "A");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);
			cvService.saveCVExperience(exp2);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);
			cvService.saveCVEducation(edu3);

			cvService.saveCVSkill(skill1);
			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant3() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Bregenz",
					"Salbergasse 43", "6900", "1986-05-04",
					"p.klepp@gmail.com", "Philipp", "Klepp", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.klepp.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1990-10-01", "2013-05-01",
					"Banker", "Hypo", "Private banking");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Bregenz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Bregenz", "BG Blumenstrasse", "Matura");

			CVSkill skill1 = mf.createSkill(cv, "Computer Skills",
					"Word, Excel");
			CVSkill skill2 = mf.createSkill(cv, "Driving Skills", "A");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill1);
			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant4() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Bregenz", "Siemensweg 2",
					"6900", "1983-01-06", "thomas.konig@gmail.com", "Thomas",
					"Konig", "male", PasswordUtil.getHashedPw("romanroman"),
					"yes", null);
			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.king.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1990-10-01", "2013-05-01",
					"Installateur", "Hard", "Installateur");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Bregenz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Bregenz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Driving Skills", "A,B,C,D");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant5() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Linz", "Hauptstraße 1",
					"4041", "1957-05-29", "guenter.schmid@gmail.com", "Günter",
					"Schmid", "male", PasswordUtil.getHashedPw("romanroman"),
					"yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant6() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Linz",
					"Kärntnerstraße 26", "4041", "1980-10-01",
					"sebastian.gross@gmail.com", "Sebastian", "Gross", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1990-10-01", "2013-05-01",
					"LKW Driver", "Spedition Braun",
					"Driving with an lkw form a to b");

			CVEducation edu1 = mf.createEdu(cv, "1988-10-01", "1992-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Hauptschule", "Hauptschule");

			CVSkill skill2 = mf.createSkill(cv, "Driving Skills", "A, B, C");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant7() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Linz", "Sombartstraße 1",
					"4041", "1971-07-21", "susanne.binder@gmail.com",
					"Susanne", "Binder", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);
			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "-");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Assistant", "WestBahn", "Assistant");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Salzburg", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Salzburg", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Computer Skills",
					"Word, Excel, Powerpoint, Access");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant8() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Salzburg",
					"Mirabellplatz 4", "5020", "1990-06-12",
					"sabrina.bauer@gmail.com", "Sabrina", "Bauer", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.bauer.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Computer Skills",
					"Word, Excel, Powerpoint, Access");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant9() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Salzburg",
					"Pestalozzistraße 43", "5020", "1950-08-08",
					"peter.gauner@gmail.com", "Peter", "Gauner", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.gauner.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1964-10-01", "2013-05-01",
					"Train Driver", "OEBB", "Driving trains form A to B");

			CVEducation edu1 = mf.createEdu(cv, "1954-10-01", "1958-07-01",
					"Salzburg", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1958-10-01", "1963-07-01",
					"Salzburg", "Hauptschule Blumenstrasse", "Hauptschule");

			CVSkill skill1 = mf.createSkill(cv, "Driving Skills", "A, B, C, D");
			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Train driver");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill1);
			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant10() throws NoSuchAlgorithmException {

		// Create Person
		Applicant applicant = mf.createA("done", "Salzburg",
				"Reinholdgasse 18", "5026", "1973-03-19",
				"peter.rauch@gmail.com", "Peter", "Rauch", "male",
				PasswordUtil.getHashedPw("romanroman"), "yes", null);

		// Save Person
		personService.save(applicant);

		// Create CV
		CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

		// Save CV
		cvService.saveCV(cv);

		// Create experiences, educations and skills for CV
		CVExperience exp1 = mf.createExp(cv, "1990-10-01", "2013-05-01",
				"Chemical", "Rauch Fruchtsaft",
				"Chemical tests on the products of rauch");

		CVEducation edu1 = mf.createEdu(cv, "1982-10-01", "1986-07-01",
				"Salzburg", "Volksschule Schendlingen", "Volksschule");
		CVEducation edu2 = mf.createEdu(cv, "1986-10-01", "1995-07-01",
				"Salzburg", "BG Blumenstrasse", "Matura");

		CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Piano player");

		// Save experiences, educations and skills
		cvService.saveCVExperience(exp1);

		cvService.saveCVEducation(edu1);
		cvService.saveCVEducation(edu2);

		cvService.saveCVSkill(skill2);

		// Insert true
		assertTrue(true);
	}

	@Test
	// NEEUUUUUUUUUUUUUUu
	public void insertApplicant11() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Salzburg",
					"Mozartplatz 6", "5020", "1976-02-01",
					"julia.spiegel@gmail.com", "Julia", "Spiegel", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "2000-10-01", "2013-05-01",
					"kindergarden teacher", "School Riedhof",
					"Teaching childs form 6-9");

			CVEducation edu1 = mf.createEdu(cv, "1982-10-01", "1986-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1986-10-01", "1994-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Designing");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant12() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Innsbruck",
					"Burggraben 3", "6021", "1959-10-03",
					"manuela.greber@gmail.com", "Manuela", "Greber", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Innsbruck", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant13() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Innsbruck",
					"Maria-Theresien-Straße 18", "6020", "1988-11-11",
					"michaelaklein@gmail.com", "Michaela", "Klein", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.coding.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"C Programmer Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Innsbruck", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Innsbruck", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant14() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Innsbruck",
					"Fürstenweg 13", "6020", "1976-07-29",
					"mario.neu@gmail.com", "Mario", "Neu", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.marioneu.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Car sale", "Mario Neu Car Sale",
					"Selling cars to customers");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills",
					"Finance, car repair");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant15() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Innsbruck",
					"Pembaurstraße 18", "6020", "1989-05-17",
					"vanessa.stein@gmail.com", "Vanessa", "Stein", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "-");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher 1", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	// __ WEITERMACHEN
	@Test
	public void insertApplicant16() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Innsbruck",
					"Michael-Gaismayr-Straße 6", "6020", "1966-10-22",
					"robert.geiss@gmail.com", "Robert", "Geiss", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher 12", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant17() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wels", "Stadtplatz 1",
					"4600", "1962-03-27", "petra.hummel@gmail.com", "Petra",
					"Hummel", "female", PasswordUtil.getHashedPw("romanroman"),
					"yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher 123", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant18() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wels",
					"Maria-Theresia-Straße 33", "4600", "1957-05-01",
					"simone.berger@gmail.com", "Simone", "Berger", "female",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher 1234", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant19() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wels",
					"Minoritengasse 5", "4600", "1966-04-14",
					"simon.vettel@gmail.com", "Simon", "Vettel", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathe Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathe");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant20() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wels", "Stadtplatz 55",
					"4600", "1989-10-03", "vivian.vogler@gmail.com", "Vivian",
					"Vogler", "female", PasswordUtil.getHashedPw("romanroman"),
					"yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematics Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematic");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant21() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wien",
					"Favoritenstrasse 202", "1100", "1987-12-02",
					"nanye@gmx.at", "Nan", "Ye", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Statistic Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Wien", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Wien", "Gymnasium Vienna X", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills",
					"Statistic and Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant22() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wien",
					"Ratschkygasse 52", "1150", "1986-07-25",
					"daniel.kuchler@gmail.com", "Daniel", "Kuchler", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Wien", "Volksschule Schendlingen", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Wien", "BG Blumenstrasse", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant23() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wien",
					"Arbeitergasse 17", "1050", "1987-04-22",
					"stefan.w@gmail.com", "Stefan", "Wurza", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.linz.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Wien", "Volksschule Meidling", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Wien", "BGB Amberg", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant24() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wien",
					"Pulverturmgasse 1", "1090", "1980-03-27",
					"hannes.rot@gmail.com", "Hannes", "Rot", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "-");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Augarden",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Linz", "Volksschule Schönau", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Linz", "Gymnasium Schönbrun", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertApplicant25() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Applicant applicant = mf.createA("done", "Wien",
					"Kettenbrückengasse 23", "1060", "1972-10-03",
					"simon.bohler@gmail.com", "Simon", "Bohler", "male",
					PasswordUtil.getHashedPw("romanroman"), "yes", null);

			// Save Person
			personService.save(applicant);

			// Create CV
			CV cv = mf.createACV(applicant, "Austria", "-", "www.bohler.at");

			// Save CV
			cvService.saveCV(cv);

			// Create experiences, educations and skills for CV
			CVExperience exp1 = mf.createExp(cv, "1970-10-01", "2013-05-01",
					"Mathematic Teacher", "School Riedhof",
					"Teaching childs form 10-16");

			CVEducation edu1 = mf.createEdu(cv, "1992-10-01", "1996-07-01",
					"Wien", "VS Bergau", "Volksschule");
			CVEducation edu2 = mf.createEdu(cv, "1996-10-01", "2005-07-01",
					"Wien", "Multilingual Bergstein", "Matura");

			CVSkill skill2 = mf.createSkill(cv, "Other Skills", "Mathematics");

			// Save experiences, educations and skills
			cvService.saveCVExperience(exp1);

			cvService.saveCVEducation(edu1);
			cvService.saveCVEducation(edu2);

			cvService.saveCVSkill(skill2);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertEmployer1() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Employer employer = mf.createE("SAP", "+43 5574 43323",
					"www.sap.at", "done", "Bregenz", "Arlbergstrasse 21",
					"6900", "", "martin.duer@sap.at", "Martin", "Dür", "male",
					PasswordUtil.getHashedPw("romanroman"), null);

			// Save Person
			personService.save(employer);

			// Create jobs
			JobDescription j1 = new JobDescription();
			j1.setEmployer(employer);
			j1.setJobTitle("Java Programmer");
			j1.setMainPurpose("We are searching for java programers...");
			j1.setQualification("java");

			Survey survey1 = mf.createSurvey(employer,
					"Questionnarie for Java programers",
					"consist of 8 questions according the position");
			Quastion question11 = mf.createQuas("Choose you level in Java", 1);
			question11.setTypeOfQuastion("closed");
			question11.setSurvey(survey1);
			SubAnswer sa111 = mf.createSubAnswer(question11, "beginner");
			SubAnswer sa112 = mf.createSubAnswer(question11, "intermediate");
			SubAnswer sa113 = mf.createSubAnswer(question11, "advanced");
			question11.addSubA(sa111);
			question11.addSubA(sa112);
			question11.addSubA(sa113);
			survey1.addQuestion(question11);
			survey1.setDelete(false);

			Quastion question12 = mf.createQuas("Your experinece in Java", 2);
			question12.setTypeOfQuastion("closed");
			question12.setSurvey(survey1);
			SubAnswer sa121 = mf.createSubAnswer(question12, "1 year");
			SubAnswer sa122 = mf.createSubAnswer(question12,
					"from 1 to 5 years");
			SubAnswer sa123 = mf
					.createSubAnswer(question12, "more than 5 yers");
			question12.addSubA(sa121);
			question12.addSubA(sa122);
			question12.addSubA(sa123);
			survey1.addQuestion(question12);

			Quastion question13 = mf
					.createQuas("Describe your last project", 3);
			question13.setTypeOfQuastion("open");
			question13.setSurvey(survey1);
			survey1.addQuestion(question13);

			Quastion question14 = mf.createQuas("Upload your last project", 4);
			question14.setTypeOfQuastion("attach");
			question14.setSurvey(survey1);
			survey1.addQuestion(question14);

			Quastion question15 = mf.createQuas(
					"If you have some Java certifacts upload them", 5);
			question15.setTypeOfQuastion("attach");
			question15.setSurvey(survey1);
			survey1.addQuestion(question15);

			Quastion question16 = mf.createQuas("Upload your motivation", 6);
			question16.setTypeOfQuastion("attach");
			question16.setSurvey(survey1);
			survey1.addQuestion(question16);

			Quastion question17 = mf.createQuas(
					"Which of the following frameworks do you know?", 7);
			question17.setTypeOfQuastion("closed");
			question17.setSurvey(survey1);
			SubAnswer sa171 = mf.createSubAnswer(question17, "HIBERNATE");
			SubAnswer sa172 = mf.createSubAnswer(question17, "SPRING");
			SubAnswer sa173 = mf.createSubAnswer(question17, "WICKET");
			SubAnswer sa174 = mf.createSubAnswer(question17,
					"JAVA MEDIA FRAMEWORK");
			SubAnswer sa175 = mf.createSubAnswer(question17, "APACHE JENA");
			question12.addSubA(sa171);
			question12.addSubA(sa172);
			question12.addSubA(sa173);
			question12.addSubA(sa174);
			question12.addSubA(sa175);
			survey1.addQuestion(question17);

			Quastion question18 = mf.createQuas(
					"Uploud your last 'Arbeitszeugniss'", 8);
			question16.setTypeOfQuastion("attach");
			question16.setSurvey(survey1);
			survey1.addQuestion(question18);

			surveyService.save(survey1);

			Survey survey2 = mf.createSurvey(employer,
					"Questionnarie for Java programers (additional)", "wicket");
			Quastion question21 = mf
					.createQuas("Choose you level in Wicket", 1);
			question21.setTypeOfQuastion("closed");
			question21.setSurvey(survey2);
			SubAnswer sa211 = mf.createSubAnswer(question21, "beginner");
			SubAnswer sa212 = mf.createSubAnswer(question21, "intermediate");
			SubAnswer sa213 = mf.createSubAnswer(question21, "advanced");
			question21.addSubA(sa211);
			question21.addSubA(sa212);
			question21.addSubA(sa213);
			survey2.addQuestion(question21);
			survey2.setDelete(false);
			
			Quastion question22 = mf.createQuas("Your experinece in WICKET", 2);
			question22.setTypeOfQuastion("closed");
			question22.setSurvey(survey2);
			SubAnswer sa221 = mf.createSubAnswer(question22, "1 year");
			SubAnswer sa222 = mf.createSubAnswer(question22,
					"from 1 to 5 years");
			SubAnswer sa223 = mf
					.createSubAnswer(question22, "more than 5 yers");
			question22.addSubA(sa221);
			question22.addSubA(sa222);
			question22.addSubA(sa223);
			survey2.addQuestion(question22);

			Quastion question23 = mf.createQuas(
					"Describe your last project with using Wicket", 3);
			question23.setTypeOfQuastion("open");
			question23.setSurvey(survey2);
			survey2.addQuestion(question23);

			Quastion question24 = mf.createQuas(
					"Upload your last project with wicket", 4);
			question24.setTypeOfQuastion("attach");
			question24.setSurvey(survey2);
			survey2.addQuestion(question24);

			Quastion question25 = mf.createQuas(
					"Upload your language certifacrs if you have some", 5);
			question25.setTypeOfQuastion("attach");
			question25.setSurvey(survey2);
			survey2.addQuestion(question25);

			surveyService.save(survey2);

			JobDescription j2 = new JobDescription();
			j2.setEmployer(employer);
			j2.setJobTitle("PHP Programmer");
			j2.setMainPurpose("We are searching for php programers...");
			j2.setQualification("php");

			Survey survey3 = mf.createSurvey(employer,
					"Questionnarie for PHP programers", "PHP");
			Quastion question31 = mf.createQuas("Choose you level in PHP", 1);
			question31.setTypeOfQuastion("closed");
			question31.setSurvey(survey3);
			SubAnswer sa311 = mf.createSubAnswer(question31, "beginner");
			SubAnswer sa312 = mf.createSubAnswer(question31, "intermediate");
			SubAnswer sa313 = mf.createSubAnswer(question31, "advanced");
			question31.addSubA(sa311);
			question31.addSubA(sa312);
			question31.addSubA(sa313);
			survey3.addQuestion(question31);
			survey3.setDelete(false);
			
			Quastion question32 = mf
					.createQuas("Describe your last project", 2);
			question32.setTypeOfQuastion("open");
			question32.setSurvey(survey3);
			survey3.addQuestion(question32);

			Quastion question33 = mf.createQuas("Upload your last project", 3);
			question33.setTypeOfQuastion("attach");
			question33.setSurvey(survey3);
			survey3.addQuestion(question33);

			Quastion question34 = mf.createQuas(
					"If you have some php certifacts upload them", 4);
			question34.setTypeOfQuastion("attach");
			question34.setSurvey(survey3);
			survey3.addQuestion(question33);

			Quastion question35 = mf.createQuas("Upload your motivation", 5);
			question35.setTypeOfQuastion("attach");
			question35.setSurvey(survey3);
			survey3.addQuestion(question35);

			surveyService.save(survey3);

			JobDescription j3 = new JobDescription();
			j3.setEmployer(employer);
			j3.setJobTitle("Mathematic Teacher");
			j3.setMainPurpose("We are searching for mathematics teacher programers...");
			j3.setQualification("Mathematic Teacher");

			Survey survey4 = mf.createSurvey(employer,
					"Questionnarie mathematics teachers", "mathematic");
			Quastion question41 = mf.createQuas(
					"Which of the folowing software products did you use?", 1);
			question41.setTypeOfQuastion("closed");
			question41.setSurvey(survey4);
			SubAnswer sa411 = mf.createSubAnswer(question41, "MathLab");
			SubAnswer sa412 = mf.createSubAnswer(question41, "Gretl");
			SubAnswer sa413 = mf.createSubAnswer(question41, "Sage");
			SubAnswer sa414 = mf.createSubAnswer(question41, "CTI");
			question41.addSubA(sa411);
			question41.addSubA(sa412);
			question41.addSubA(sa413);
			question41.addSubA(sa414);
			survey4.addQuestion(question41);

			Quastion question42 = mf.createQuas("Describe your phd thesis", 2);
			question42.setTypeOfQuastion("open");
			question42.setSurvey(survey4);
			survey4.addQuestion(question42);

			Quastion question43 = mf.createQuas("Upload your last project", 3);
			question43.setTypeOfQuastion("attach");
			question43.setSurvey(survey4);
			survey4.addQuestion(question43);

			Quastion question44 = mf.createQuas(
					"If you have some certifacts uploud them", 4);
			question44.setTypeOfQuastion("attach");
			question44.setSurvey(survey4);
			survey4.addQuestion(question43);

			survey4.setDelete(false);
			surveyService.save(survey4);

			Survey survey5 = mf.createSurvey(employer,
					"Questionnarie mathematics teachers (advanced)",
					"contains some exercises");
			Quastion question51 = mf
					.createQuas(
							"Implement simple regression function in MathLab, uploaud your solution",
							1);
			question51.setTypeOfQuastion("attach");
			question51.setSurvey(survey5);
			survey5.addQuestion(question51);

			Quastion question52 = mf
					.createQuas(
							"Implement simple regression function in Gretl, uploaud your solution",
							2);
			question52.setTypeOfQuastion("attach");
			question52.setSurvey(survey5);
			survey5.addQuestion(question52);
			survey5.setDelete(false);
			surveyService.save(survey5);
			
			
			
			
			ApplicantToSurvey as1 = new ApplicantToSurvey();
			as1.setDate(new Date());
			as1.setNewq(false);
			as1.setNewqa(true);
			as1.setVis(false);
			as1.setVisapp(true);
			as1.setServeys(survey1);
			Person per1 = personService.find(new Long(1));
			as1.setApplicant((IApplicant) per1);
			surveyService.saveAppSurvey(as1);

			ApplicantToSurvey as2 = new ApplicantToSurvey();
			as2.setDate(new Date());
			as2.setNewq(false);
			as2.setNewqa(true);
			as2.setVis(false);
			as2.setVisapp(true);
			as2.setServeys(survey1);
			Person per2 = personService.find(new Long(5));
			as2.setApplicant((IApplicant) per2);
			surveyService.saveAppSurvey(as2);

			ApplicantToSurvey as3 = new ApplicantToSurvey();
			as3.setDate(new Date());
			as3.setNewq(false);
			as3.setNewqa(true);
			as3.setVis(false);
			as3.setVisapp(true);
			as3.setServeys(survey4);
			Person per3 = personService.find(new Long(2));
			as3.setApplicant((IApplicant) per3);
			surveyService.saveAppSurvey(as3);

			ApplicantToSurvey as4 = new ApplicantToSurvey();
			as4.setDate(new Date());
			as4.setNewq(false);
			as4.setNewqa(true);
			as4.setVis(false);
			as4.setVisapp(true);
			as4.setServeys(survey4);
			Person per4 = personService.find(new Long(3));
			as4.setApplicant((IApplicant) per4);
			surveyService.saveAppSurvey(as4);

			ApplicantToSurvey as5 = new ApplicantToSurvey();
			as5.setDate(new Date());
			as5.setNewq(false);
			as5.setNewqa(true);
			as5.setVis(false);
			as5.setVisapp(true);
			as5.setServeys(survey1);
			Person per5 = personService.find(new Long(3));
			as5.setApplicant((IApplicant) per5);
			surveyService.saveAppSurvey(as5);

			jobService.save(j1);
			jobService.save(j2);
			jobService.save(j3);
			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertEmployer2() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Employer employer = mf.createE("DELL", "+43 5572 43323",
					"www.dell.at", "done", "Bregenz", "Arlbergstrasse 21",
					"6900", "", "hermann.sauer@dell.at", "Hermann", "Sauer",
					"male", PasswordUtil.getHashedPw("romanroman"), null);

			// Save Person
			personService.save(employer);

			// Create jobs
			JobDescription j1 = new JobDescription();
			j1.setEmployer(employer);
			j1.setJobTitle("Java Programmer");
			j1.setMainPurpose("We are searching for java programers...");
			j1.setQualification("java");

			Survey survey1 = mf.createSurvey(employer,
					"Questionnarie for Java programers",
					"consist of 8 questions according the position");
			Quastion question11 = mf.createQuas("Choose you level in Java", 1);
			question11.setTypeOfQuastion("closed");
			question11.setSurvey(survey1);
			SubAnswer sa111 = mf.createSubAnswer(question11, "beginner");
			SubAnswer sa112 = mf.createSubAnswer(question11, "intermediate");
			SubAnswer sa113 = mf.createSubAnswer(question11, "advanced");
			question11.addSubA(sa111);
			question11.addSubA(sa112);
			question11.addSubA(sa113);
			survey1.addQuestion(question11);

			Quastion question12 = mf.createQuas("Your experinece in Java", 2);
			question12.setTypeOfQuastion("closed");
			question12.setSurvey(survey1);
			SubAnswer sa121 = mf.createSubAnswer(question12, "1 year");
			SubAnswer sa122 = mf.createSubAnswer(question12,
					"from 1 to 5 years");
			SubAnswer sa123 = mf
					.createSubAnswer(question12, "more than 5 yers");
			question12.addSubA(sa121);
			question12.addSubA(sa122);
			question12.addSubA(sa123);
			survey1.addQuestion(question12);

			Quastion question13 = mf
					.createQuas("Describe your last project", 3);
			question13.setTypeOfQuastion("open");
			question13.setSurvey(survey1);
			survey1.addQuestion(question13);

			Quastion question14 = mf.createQuas("Upload your last project", 4);
			question14.setTypeOfQuastion("attach");
			question14.setSurvey(survey1);
			survey1.addQuestion(question14);

			Quastion question15 = mf.createQuas(
					"If you have some Java certifacts uploud them", 5);
			question15.setTypeOfQuastion("attach");
			question15.setSurvey(survey1);
			survey1.addQuestion(question15);

			Quastion question16 = mf.createQuas("Uploud your motivation", 6);
			question16.setTypeOfQuastion("attach");
			question16.setSurvey(survey1);
			survey1.addQuestion(question16);

			Quastion question17 = mf.createQuas(
					"Which of the following frameworks do you know?", 7);
			question17.setTypeOfQuastion("closed");
			question17.setSurvey(survey1);
			SubAnswer sa171 = mf.createSubAnswer(question17, "HIBERNATE");
			SubAnswer sa172 = mf.createSubAnswer(question17, "SPRING");
			SubAnswer sa173 = mf.createSubAnswer(question17, "WICKET");
			SubAnswer sa174 = mf.createSubAnswer(question17,
					"JAVA MEDIA FRAMEWORK");
			SubAnswer sa175 = mf.createSubAnswer(question17, "APACHE JENA");
			question12.addSubA(sa171);
			question12.addSubA(sa172);
			question12.addSubA(sa173);
			question12.addSubA(sa174);
			question12.addSubA(sa175);
			survey1.addQuestion(question17);

			Quastion question18 = mf.createQuas(
					"Uploud your last 'Arbeitszeugniss'", 8);
			question16.setTypeOfQuastion("attach");
			question16.setSurvey(survey1);
			survey1.addQuestion(question18);

			survey1.setDelete(false);
			surveyService.save(survey1);

			Survey survey2 = mf.createSurvey(employer,
					"Questionnarie for Java programers (additional)",
					"consist of 8 questions according the position");
			Quastion question21 = mf
					.createQuas("Choose you level in Wicket", 1);
			question21.setTypeOfQuastion("closed");
			question21.setSurvey(survey2);
			SubAnswer sa211 = mf.createSubAnswer(question21, "beginner");
			SubAnswer sa212 = mf.createSubAnswer(question21, "intermediate");
			SubAnswer sa213 = mf.createSubAnswer(question21, "advanced");
			question21.addSubA(sa211);
			question21.addSubA(sa212);
			question21.addSubA(sa213);
			survey2.addQuestion(question21);

			Quastion question22 = mf.createQuas("Your experinece in WICKET", 2);
			question22.setTypeOfQuastion("closed");
			question22.setSurvey(survey2);
			SubAnswer sa221 = mf.createSubAnswer(question22, "1 year");
			SubAnswer sa222 = mf.createSubAnswer(question22,
					"from 1 to 5 years");
			SubAnswer sa223 = mf
					.createSubAnswer(question22, "more than 5 yers");
			question22.addSubA(sa221);
			question22.addSubA(sa222);
			question22.addSubA(sa223);
			survey2.addQuestion(question22);
			survey2.setDelete(false);
			Quastion question23 = mf.createQuas(
					"Describe your last project with using Wicket", 3);
			question23.setTypeOfQuastion("open");
			question23.setSurvey(survey2);
			survey2.addQuestion(question23);

			Quastion question24 = mf.createQuas(
					"Upload your last project with wicket", 4);
			question24.setTypeOfQuastion("attach");
			question24.setSurvey(survey2);
			survey2.addQuestion(question24);

			Quastion question25 = mf.createQuas(
					"Upload your language certifacrs if ypu have some", 5);
			question25.setTypeOfQuastion("attach");
			question25.setSurvey(survey2);
			survey2.addQuestion(question25);

			surveyService.save(survey2);

			JobDescription j2 = new JobDescription();
			j2.setEmployer(employer);
			j2.setJobTitle("PHP Programmer");
			j2.setMainPurpose("We are searching for php programers...");
			j2.setQualification("php");

			Survey survey3 = mf.createSurvey(employer,
					"Questionnarie for PHP programers", "PHP");
			Quastion question31 = mf.createQuas("Choose you level in PHP", 1);
			question31.setTypeOfQuastion("closed");
			question31.setSurvey(survey3);
			SubAnswer sa311 = mf.createSubAnswer(question31, "beginner");
			SubAnswer sa312 = mf.createSubAnswer(question31, "intermediate");
			SubAnswer sa313 = mf.createSubAnswer(question31, "advanced");
			question31.addSubA(sa311);
			question31.addSubA(sa312);
			question31.addSubA(sa313);
			survey3.addQuestion(question31);
			survey3.setDelete(false);
			Quastion question32 = mf
					.createQuas("Describe your last project", 2);
			question32.setTypeOfQuastion("open");
			question32.setSurvey(survey3);
			survey3.addQuestion(question32);

			Quastion question33 = mf.createQuas("Uploud your last project", 3);
			question33.setTypeOfQuastion("attach");
			question33.setSurvey(survey3);
			survey3.addQuestion(question33);

			Quastion question34 = mf.createQuas(
					"If you have some php certifacts uploud them", 4);
			question34.setTypeOfQuastion("attach");
			question34.setSurvey(survey3);
			survey3.addQuestion(question33);

			Quastion question35 = mf.createQuas("Upload your motivation", 5);
			question35.setTypeOfQuastion("attach");
			question35.setSurvey(survey3);
			survey3.addQuestion(question35);

			surveyService.save(survey3);

			JobDescription j3 = new JobDescription();
			j3.setEmployer(employer);
			j3.setJobTitle("Buisness Informatics");
			j3.setMainPurpose("We are searching for programmers who also knows how buisness works...");
			j3.setQualification("Buisness and programmer");

			Survey survey4 = mf.createSurvey(employer,
					"Questionnarie for bussines informaticcs",
					"financial exercises");

			Quastion question41 = mf.createQuas(
					"Describe your last project in finance", 1);
			question41.setTypeOfQuastion("open");
			question41.setSurvey(survey4);
			survey4.addQuestion(question41);
			survey4.setDelete(false);
			
			Quastion question42 = mf
					.createQuas(
							"Research the employee benefits that it may offer to help you determine the types of insurance you may need and typical out-of-pocket costs. ",
							2);
			question42.setTypeOfQuastion("open");
			question42.setSurvey(survey4);
			survey4.addQuestion(question42);

			Quastion question43 = mf.createQuas("Implement balanced scorecard",
					3);
			question43.setTypeOfQuastion("attach");
			question43.setSurvey(survey4);
			survey4.addQuestion(question43);

			surveyService.save(survey4);

			JobDescription j4 = new JobDescription();
			j4.setEmployer(employer);
			j4.setJobTitle("Mathematic Teacher");
			j4.setMainPurpose("We are searching for mathematic teacher for kids form 8 till 16");
			j4.setQualification("mathematic");

			Survey survey5 = mf.createSurvey(employer,
					"Questionnarie mathematics teachers (advanced)",
					"contains some exercises");
			Quastion question51 = mf
					.createQuas(
							"Implement simple regression function in MathLab, uploaud your solution",
							1);
			question51.setTypeOfQuastion("attach");
			question51.setSurvey(survey5);
			survey5.addQuestion(question51);
			survey5.setDelete(false);
			
			Quastion question52 = mf
					.createQuas(
							"Implement simple regression function in Gretl, uploaud your solution",
							2);
			question52.setTypeOfQuastion("attach");
			question52.setSurvey(survey5);
			survey5.addQuestion(question52);

			surveyService.save(survey5);

			JobDescription j5 = new JobDescription();
			j5.setEmployer(employer);
			j5.setJobTitle("Mathematic and Physik Teacher");
			j5.setMainPurpose("We are searching for mathematic teacher who can also teach physics");
			j5.setQualification("mathematic, physic");

			Survey survey7 = mf.createSurvey(employer,
					"Questionnarie for mathematic and physic", "teachers");

			survey7.setDelete(false);
			Quastion question71 = mf.createQuas("Describe your last position",
					1);
			question71.setTypeOfQuastion("open");
			question71.setSurvey(survey7);
			survey7.addQuestion(question71);

			Quastion question72 = mf.createQuas(
					"Upload your physic qualification courses", 2);
			question72.setTypeOfQuastion("attach");
			question72.setSurvey(survey7);
			survey7.addQuestion(question72);

			Quastion question73 = mf
					.createQuas(
							"Upload your mathematics certificat, qualification courses",
							3);
			question73.setTypeOfQuastion("attach");
			question73.setSurvey(survey7);
			survey7.addQuestion(question73);

			surveyService.save(survey7);

			JobDescription j6 = new JobDescription();
			j6.setEmployer(employer);
			j6.setJobTitle("Mathematic and German Teacher");
			j6.setMainPurpose("We are searching for mathematic teacher who can also teach german");
			j6.setQualification("mathematic, german");

			Survey survey6 = mf.createSurvey(employer,
					"Questionnarie for mathematic and german", "teachers");

			Quastion question61 = mf.createQuas("Describe your last position",
					1);
			question61.setTypeOfQuastion("open");
			question61.setSurvey(survey6);
			survey6.addQuestion(question61);
			survey6.setDelete(false);
			Quastion question62 = mf
					.createQuas(
							"Upload your language certificat, qualification courses",
							2);
			question62.setTypeOfQuastion("attach");
			question62.setSurvey(survey6);
			survey6.addQuestion(question62);

			Quastion question63 = mf
					.createQuas(
							"Upload your mathematics certificat, qualification courses",
							3);
			question63.setTypeOfQuastion("attach");
			question63.setSurvey(survey6);
			survey6.addQuestion(question63);

			surveyService.save(survey6);
			
			
			
			ApplicantToSurvey as1 = new ApplicantToSurvey();
			as1.setDate(new Date());
			as1.setNewq(false);
			as1.setNewqa(true);
			as1.setVis(false);
			as1.setVisapp(true);
			as1.setServeys(survey1);
			Person per1 = personService.find(new Long(1));
			as1.setApplicant((IApplicant) per1);
			surveyService.saveAppSurvey(as1);

			ApplicantToSurvey as2 = new ApplicantToSurvey();
			as2.setDate(new Date());
			as2.setNewq(false);
			as2.setNewqa(true);
			as2.setVis(false);
			as2.setVisapp(true);
			as2.setServeys(survey1);
			Person per2 = personService.find(new Long(5));
			as2.setApplicant((IApplicant) per2);
			surveyService.saveAppSurvey(as2);

			ApplicantToSurvey as3 = new ApplicantToSurvey();
			as3.setDate(new Date());
			as3.setNewq(false);
			as3.setNewqa(true);
			as3.setVis(false);
			as3.setVisapp(true);
			as3.setServeys(survey4);
			Person per3 = personService.find(new Long(2));
			as3.setApplicant((IApplicant) per3);
			surveyService.saveAppSurvey(as3);

			ApplicantToSurvey as4 = new ApplicantToSurvey();
			as4.setDate(new Date());
			as4.setNewq(false);
			as4.setNewqa(true);
			as4.setVis(false);
			as4.setVisapp(true);
			as4.setServeys(survey4);
			Person per4 = personService.find(new Long(3));
			as4.setApplicant((IApplicant) per4);
			surveyService.saveAppSurvey(as4);

			ApplicantToSurvey as5 = new ApplicantToSurvey();
			as5.setDate(new Date());
			as5.setNewq(false);
			as5.setNewqa(true);
			as5.setVis(false);
			as5.setVisapp(true);
			as5.setServeys(survey1);
			Person per5 = personService.find(new Long(3));
			as5.setApplicant((IApplicant) per5);
			surveyService.saveAppSurvey(as5);

			ApplicantToSurvey as6 = new ApplicantToSurvey();
			as6.setDate(new Date());
			as6.setNewq(false);
			as6.setNewqa(true);
			as6.setVis(false);
			as6.setVisapp(true);
			as6.setServeys(survey6);
			Person per6 = personService.find(new Long(6));
			as6.setApplicant((IApplicant) per6);
			surveyService.saveAppSurvey(as6);

			ApplicantToSurvey as7 = new ApplicantToSurvey();
			as7.setDate(new Date());
			as7.setNewq(false);
			as7.setNewqa(true);
			as7.setVis(false);
			as7.setVisapp(true);
			as7.setServeys(survey7);
			Person per7 = personService.find(new Long(1));
			as7.setApplicant((IApplicant) per7);
			surveyService.saveAppSurvey(as7);			
			

			jobService.save(j1);
			jobService.save(j2);
			jobService.save(j3);
			jobService.save(j4);
			jobService.save(j5);
			jobService.save(j6);
			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertEmployer3() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Employer employer = mf.createE("BG Blumenstrasse",
					"+43 5572 43323", "www.bgblumenstrasse.at", "done",
					"Bregenz", "Arlbergstrasse 121", "6900", "",
					"peter.hutter@bgb.at", "Peter", "Hutter", "male",
					PasswordUtil.getHashedPw("romanroman"), null);

			// Save Person
			personService.save(employer);

			// Create jobs
			JobDescription j1 = new JobDescription();
			j1.setEmployer(employer);
			j1.setJobTitle("Mathematic Teacher");
			j1.setMainPurpose("We are searching for mathematic Tearchers...");
			j1.setQualification("mathematic");

			Survey survey4 = mf.createSurvey(employer,
					"Questionnarie mathematics teachers", "mathematic");
			Quastion question41 = mf.createQuas(
					"Which of the folowing software products did you use?", 1);
			question41.setTypeOfQuastion("closed");
			question41.setSurvey(survey4);
			SubAnswer sa411 = mf.createSubAnswer(question41, "MathLab");
			SubAnswer sa412 = mf.createSubAnswer(question41, "Gretl");
			SubAnswer sa413 = mf.createSubAnswer(question41, "Sage");
			SubAnswer sa414 = mf.createSubAnswer(question41, "CTI");
			question41.addSubA(sa411);
			question41.addSubA(sa412);
			question41.addSubA(sa413);
			question41.addSubA(sa414);
			survey4.addQuestion(question41);
			survey4.setDelete(false);
			
			Quastion question42 = mf.createQuas("Describe your phd thesis", 2);
			question42.setTypeOfQuastion("open");
			question42.setSurvey(survey4);
			survey4.addQuestion(question42);

			Quastion question43 = mf.createQuas("Upload your last project", 3);
			question43.setTypeOfQuastion("attach");
			question43.setSurvey(survey4);
			survey4.addQuestion(question43);

			Quastion question44 = mf.createQuas(
					"If you have some certifacts upload them", 4);
			question44.setTypeOfQuastion("attach");
			question44.setSurvey(survey4);
			survey4.addQuestion(question43);

			surveyService.save(survey4);

			Survey survey6 = mf.createSurvey(employer,
					"Questionnarie for mathematic and german", "teachers");

			Quastion question61 = mf.createQuas("Describe your last position",
					1);
			question61.setTypeOfQuastion("open");
			question61.setSurvey(survey6);
			survey6.addQuestion(question61);
			survey6.setDelete(false);
			
			Quastion question62 = mf
					.createQuas(
							"Upload your language certificat, qualification courses",
							2);
			question62.setTypeOfQuastion("attach");
			question62.setSurvey(survey6);
			survey6.addQuestion(question62);

			Quastion question63 = mf
					.createQuas(
							"Upload your mathematics certificat, qualification courses",
							3);
			question63.setTypeOfQuastion("attach");
			question63.setSurvey(survey6);
			survey6.addQuestion(question63);

			surveyService.save(survey6);

			JobDescription j2 = new JobDescription();
			j2.setEmployer(employer);
			j2.setJobTitle("German Teacher");
			j2.setMainPurpose("We are searching for german teachers...");
			j2.setQualification("german");

			Survey survey1 = mf.createSurvey(employer,
					"Questionnarie german teachers", "german");
			Quastion question11 = mf.createQuas(
					"Where were you teaching german?", 1);
			question11.setTypeOfQuastion("closed");
			question11.setSurvey(survey4);
			SubAnswer sa111 = mf.createSubAnswer(question11, "school");
			SubAnswer sa112 = mf.createSubAnswer(question11, "univercity");
			SubAnswer sa113 = mf.createSubAnswer(question11, "college");
			question11.addSubA(sa111);
			question11.addSubA(sa112);
			question11.addSubA(sa113);
			survey1.addQuestion(question11);
			survey1.setDelete(false);
			
			Quastion question12 = mf.createQuas(
					"Describe your complexity to be german teacher", 2);
			question12.setTypeOfQuastion("open");
			question12.setSurvey(survey1);
			survey1.addQuestion(question12);

			Quastion question13 = mf.createQuas(
					"Upload your last 'Arbeitszeugniss", 3);
			question13.setTypeOfQuastion("attach");
			question13.setSurvey(survey1);
			survey1.addQuestion(question13);

			Quastion question14 = mf.createQuas(
					"If you have some certifacts upload them", 4);
			question14.setTypeOfQuastion("attach");
			question14.setSurvey(survey1);
			survey1.addQuestion(question13);

			surveyService.save(survey1);

			JobDescription j5 = new JobDescription();
			j5.setEmployer(employer);
			j5.setJobTitle("Mathematic and Physik Teacher");
			j5.setMainPurpose("We are searching for mathematic teacher who can also teach physics");
			j5.setQualification("mathematic, physic");

			Survey survey7 = mf.createSurvey(employer,
					"Questionnarie for mathematic and physic", "teachers");

			Quastion question71 = mf.createQuas("Describe your last position",
					1);
			question71.setTypeOfQuastion("open");
			question71.setSurvey(survey7);
			survey7.addQuestion(question71);
			survey7.setDelete(false);
			Quastion question72 = mf.createQuas(
					"Uploud your physic qualification courses", 2);
			question72.setTypeOfQuastion("attach");
			question72.setSurvey(survey7);
			survey7.addQuestion(question72);

			Quastion question73 = mf
					.createQuas(
							"Uploud your mathematics certificat, qualification courses",
							3);
			question73.setTypeOfQuastion("attach");
			question73.setSurvey(survey7);
			survey7.addQuestion(question73);

			surveyService.save(survey7);
			
			
			
			ApplicantToSurvey as1 = new ApplicantToSurvey();
			as1.setDate(new Date());
			as1.setNewq(false);
			as1.setNewqa(true);
			as1.setVis(false);
			as1.setVisapp(true);
			as1.setServeys(survey1);
			Person per1 = personService.find(new Long(1));
			as1.setApplicant((IApplicant) per1);
			surveyService.saveAppSurvey(as1);

			ApplicantToSurvey as2 = new ApplicantToSurvey();
			as2.setDate(new Date());
			as2.setNewq(false);
			as2.setNewqa(true);
			as2.setVis(false);
			as2.setVisapp(true);
			as2.setServeys(survey1);
			Person per2 = personService.find(new Long(5));
			as2.setApplicant((IApplicant) per2);
			surveyService.saveAppSurvey(as2);

			ApplicantToSurvey as3 = new ApplicantToSurvey();
			as3.setDate(new Date());
			as3.setNewq(false);
			as3.setNewqa(true);
			as3.setVis(false);
			as3.setVisapp(true);
			as3.setServeys(survey4);
			Person per3 = personService.find(new Long(2));
			as3.setApplicant((IApplicant) per3);
			surveyService.saveAppSurvey(as3);

			ApplicantToSurvey as4 = new ApplicantToSurvey();
			as4.setDate(new Date());
			as4.setNewq(false);
			as4.setNewqa(true);
			as4.setVis(false);
			as4.setVisapp(true);
			as4.setServeys(survey4);
			Person per4 = personService.find(new Long(3));
			as4.setApplicant((IApplicant) per4);
			surveyService.saveAppSurvey(as4);

			
			ApplicantToSurvey as6 = new ApplicantToSurvey();
			as6.setDate(new Date());
			as6.setNewq(false);
			as6.setNewqa(true);
			as6.setVis(false);
			as6.setVisapp(true);
			as6.setServeys(survey6);
			Person per6 = personService.find(new Long(6));
			as6.setApplicant((IApplicant) per6);
			surveyService.saveAppSurvey(as6);

			
			JobDescription j6 = new JobDescription();
			j6.setEmployer(employer);
			j6.setJobTitle("Mathematic and German Teacher");
			j6.setMainPurpose("We are searching for mathematic teacher who can also teach german");
			j6.setQualification("mathematic, german");

			jobService.save(j1);
			jobService.save(j2);
			jobService.save(j5);
			jobService.save(j6);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}

	@Test
	public void insertEmployer4() throws NoSuchAlgorithmException {
		try {
			// Create Person
			Employer employer = mf.createE("Deloitte", "+43 5563 43323",
					"www.deloitte.at", "done", "Bregenz", "Arlbergstrasse 121",
					"6900", "", "manuel.holder@deloitte.at", "Manuel",
					"Holder", "male", PasswordUtil.getHashedPw("romanroman"),
					null);

			// Save Person
			personService.save(employer);

			// Create jobs
			JobDescription j1 = new JobDescription();
			j1.setEmployer(employer);
			j1.setJobTitle("Mathematic Teacher");
			j1.setMainPurpose("We are searching for mathematic Tearchers...");
			j1.setQualification("mathematic");

			Survey survey4 = mf.createSurvey(employer,
					"Questionnarie mathematics teachers", "mathematic");
			Quastion question41 = mf.createQuas(
					"Which of the folowing software products did you use?", 1);
			question41.setTypeOfQuastion("closed");
			question41.setSurvey(survey4);
			SubAnswer sa411 = mf.createSubAnswer(question41, "MathLab");
			SubAnswer sa412 = mf.createSubAnswer(question41, "Gretl");
			SubAnswer sa413 = mf.createSubAnswer(question41, "Sage");
			SubAnswer sa414 = mf.createSubAnswer(question41, "CTI");
			question41.addSubA(sa411);
			question41.addSubA(sa412);
			question41.addSubA(sa413);
			question41.addSubA(sa414);
			survey4.addQuestion(question41);
			survey4.setDelete(false);
			
			Quastion question42 = mf.createQuas("Describe your phd thesis", 2);
			question42.setTypeOfQuastion("open");
			question42.setSurvey(survey4);
			survey4.addQuestion(question42);

			Quastion question43 = mf.createQuas("Upload your last project", 3);
			question43.setTypeOfQuastion("attach");
			question43.setSurvey(survey4);
			survey4.addQuestion(question43);

			Quastion question44 = mf.createQuas(
					"If you have some certifacts upload them", 4);
			question44.setTypeOfQuastion("attach");
			question44.setSurvey(survey4);
			survey4.addQuestion(question43);

			surveyService.save(survey4);

			JobDescription j2 = new JobDescription();
			j2.setEmployer(employer);
			j2.setJobTitle("German Teacher");
			j2.setMainPurpose("We are searching for german teachers...");
			j2.setQualification("german");

			JobDescription j5 = new JobDescription();
			j5.setEmployer(employer);
			j5.setJobTitle("Mathematic and Physik Teacher");
			j5.setMainPurpose("We are searching for mathematic teacher who can also teach physics");
			j5.setQualification("mathematic, physic");

			JobDescription j6 = new JobDescription();
			j6.setEmployer(employer);
			j6.setJobTitle("Mathematic and German Teacher");
			j6.setMainPurpose("We are searching for mathematic teacher who can also teach german");
			j6.setQualification("mathematic, german");

			Survey survey6 = mf.createSurvey(employer,
					"Questionnarie for mathematic and german", "teachers");

			Quastion question61 = mf.createQuas("Describe your last position",
					1);
			question61.setTypeOfQuastion("open");
			question61.setSurvey(survey6);
			survey6.addQuestion(question61);
			survey6.setDelete(false);
			
			Quastion question62 = mf
					.createQuas(
							"Upload your language certificat, qualification courses",
							2);
			question62.setTypeOfQuastion("attach");
			question62.setSurvey(survey6);
			survey6.addQuestion(question62);

			Quastion question63 = mf
					.createQuas(
							"Upload your mathematics certificat, qualification courses",
							3);
			question63.setTypeOfQuastion("attach");
			question63.setSurvey(survey6);
			survey6.addQuestion(question63);

			surveyService.save(survey6);

			jobService.save(j1);
			jobService.save(j2);
			jobService.save(j5);
			jobService.save(j6);

			// Insert true
			assertTrue(true);
		} catch (Exception e) {
			// Insert false
			assertTrue(false);
		}
	}
    
    
	@Test
    public void insertMessage1(){
		Person p = personService.find(Long.valueOf(1));
			try {
				IMessaging message1 = mf.createMessage();
				message1.setMessageTitle("We'd like to offer you a job");
				message1.setMessageBody("Hi there, we've seen your application and we'd like to welcome you to meet with us! Please give me a ring on +41 1231 2211");
				message1.setMessageSubject("Offer");
				message1.setReceived(false);
				message1.setSenderID(Long.valueOf(26));
				message1.setReceiverID(Long.valueOf(1));
				message1.setSent(true);
				message1.setPerson(p);
				messagingService.update(message1);
				assertTrue(true);
			}
			catch (Exception e) {
				//Insert false
				assertTrue(false);
			} 	
	 }
  
	@Test


    public void insertMessage2(){

		Person p = personService.find(Long.valueOf(2));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hello, we would like to offer you the open position in our firm.");

				message1.setMessageSubject("Job-Offer");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(2));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage3(){

		Person p = personService.find(Long.valueOf(3));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Dear Mr. Suess, We are interested in giving you the position as a co-worker. For more details please call 472-90-76.");

				message1.setMessageSubject("Co-Worker Position");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(3));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage4(){

		Person p = personService.find(Long.valueOf(4));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hello, We read your resumee and would like to invite you to an interview.");

				message1.setMessageSubject("Resumee");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(4));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage5(){

		Person p = personService.find(Long.valueOf(5));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Dear Mrs. Wald, Your application has been approved. Please call 234-54-89 for more information.");

				message1.setMessageSubject("Application");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(5));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage6(){

		Person p = personService.find(Long.valueOf(6));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hi, We'd like to offer you a job in our office.");

				message1.setMessageSubject("Job");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(6));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage7(){

		Person p = personService.find(Long.valueOf(7));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Dear Mr. Sach, We have an open possition in our firm you would be suited for. Please call 874-98-02 if interested.");

				message1.setMessageSubject("Click-Match-Hire");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(7));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage8(){

		Person p = personService.find(Long.valueOf(8));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hi, I would like to offer you a part time job in my firm.");

				message1.setMessageSubject("Part Time Job");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(8));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage9(){

		Person p = personService.find(Long.valueOf(9));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Dear Mrs. Mauser, I would like to offer you the open position in my firm.");

				message1.setMessageSubject("Open Position");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(9));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage10(){

		Person p = personService.find(Long.valueOf(10));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hi, I read your application on Click-Match-Hire and want to offer you a position.");

				message1.setMessageSubject("Application Click-Match-Hire");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(10));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage11(){

		Person p = personService.find(Long.valueOf(11));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hello, Me and my team want to offer you a job in my firm.");

				message1.setMessageSubject("Job Offer");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(11));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage12(){

		Person p = personService.find(Long.valueOf(12));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hello, We want to offer you a job for the season 2013/2014. Call 453-40-09 for more information.");

				message1.setMessageSubject("Seasonal Job");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(12));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage13(){

		Person p = personService.find(Long.valueOf(13));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Dear Mr. Grim, I would like to offer you a position at Tech and Co.");

				message1.setMessageSubject("Tech and Co.");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(13));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage14(){

		Person p = personService.find(Long.valueOf(14));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Dear Ms. Frick, Due to your application on Click-Match-Hire I would like to offer you a job at my office.");

				message1.setMessageSubject("Offering Job");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(14));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }

@Test


    public void insertMessage15(){

		Person p = personService.find(Long.valueOf(15));

			try {

				IMessaging message1 = mf.createMessage();


				message1.setMessageBody("Hello, We would like to offer you the open position at Flips, Brothers and Co.");

				message1.setMessageSubject("Flips, Brothers and Co.");

				message1.setReceived(false);

				message1.setSenderID(Long.valueOf(26));

				message1.setReceiverID(Long.valueOf(15));

				message1.setSent(true);

				message1.setPerson(p);
				
				
				// savemessage

				messagingService.save(message1);

				assertTrue(true);

			}

			catch (Exception e) {

				//Insert false

				assertTrue(false);

			} 	

	 }
	
@Test
public void insertMessage127(){
	Person p = personService.find(Long.valueOf(1));
		try {
			IMessaging message1 = mf.createMessage();
			message1.setMessageTitle("We'd like to offer you a job");
			message1.setMessageBody("Hi there, we've seen your application and we'd like to welcome you to meet with us! Please give me a ring on +41 1231 2211");
			message1.setMessageSubject("Offer");
			message1.setReceived(false);
			message1.setSenderID(Long.valueOf(27));
			message1.setReceiverID(Long.valueOf(1));
			message1.setSent(true);
			message1.setPerson(p);
			messagingService.update(message1);
			assertTrue(true);
		}
		catch (Exception e) {
			//Insert false
			assertTrue(false);
		} 	
 }

@Test


public void insertMessage227(){

	Person p = personService.find(Long.valueOf(2));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, we would like to offer you the open position in our firm.");

			message1.setMessageSubject("Job-Offer");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(2));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage327(){

	Person p = personService.find(Long.valueOf(3));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Suess, We are interested in giving you the position as a co-worker. For more details please call 472-90-76.");

			message1.setMessageSubject("Co-Worker Position");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(3));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage427(){

	Person p = personService.find(Long.valueOf(4));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We read your resumee and would like to invite you to an interview.");

			message1.setMessageSubject("Resumee");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(4));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage527(){

	Person p = personService.find(Long.valueOf(5));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mrs. Wald, Your application has been approved. Please call 234-54-89 for more information.");

			message1.setMessageSubject("Application");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(5));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage627(){

	Person p = personService.find(Long.valueOf(6));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, We'd like to offer you a job in our office.");

			message1.setMessageSubject("Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(6));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage727(){

	Person p = personService.find(Long.valueOf(7));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Sach, We have an open possition in our firm you would be suited for. Please call 874-98-02 if interested.");

			message1.setMessageSubject("Click-Match-Hire");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(7));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage827(){

	Person p = personService.find(Long.valueOf(8));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, I would like to offer you a part time job in my firm.");

			message1.setMessageSubject("Part Time Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(8));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage927(){

	Person p = personService.find(Long.valueOf(9));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mrs. Mauser, I would like to offer you the open position in my firm.");

			message1.setMessageSubject("Open Position");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(9));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1027(){

	Person p = personService.find(Long.valueOf(10));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, I read your application on Click-Match-Hire and want to offer you a position.");

			message1.setMessageSubject("Application Click-Match-Hire");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(10));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1127(){

	Person p = personService.find(Long.valueOf(11));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, Me and my team want to offer you a job in my firm.");

			message1.setMessageSubject("Job Offer");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(11));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1227(){

	Person p = personService.find(Long.valueOf(12));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We want to offer you a job for the season 2013/2014. Call 453-40-09 for more information.");

			message1.setMessageSubject("Seasonal Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(12));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1327(){

	Person p = personService.find(Long.valueOf(13));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Grim, I would like to offer you a position at Tech and Co.");

			message1.setMessageSubject("Tech and Co.");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(13));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1427(){

	Person p = personService.find(Long.valueOf(14));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Ms. Frick, Due to your application on Click-Match-Hire I would like to offer you a job at my office.");

			message1.setMessageSubject("Offering Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(14));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1527(){

	Person p = personService.find(Long.valueOf(15));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We would like to offer you the open position at Flips, Brothers and Co.");

			message1.setMessageSubject("Flips, Brothers and Co.");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(27));

			message1.setReceiverID(Long.valueOf(15));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }
@Test
public void insertMessage128(){
	Person p = personService.find(Long.valueOf(1));
		try {
			IMessaging message1 = mf.createMessage();
			message1.setMessageTitle("We'd like to offer you a job");
			message1.setMessageBody("Hi there, we've seen your application and we'd like to welcome you to meet with us! Please give me a ring on +41 1231 2211");
			message1.setMessageSubject("Offer");
			message1.setReceived(false);
			message1.setSenderID(Long.valueOf(28));
			message1.setReceiverID(Long.valueOf(1));
			message1.setSent(true);
			message1.setPerson(p);
			messagingService.update(message1);
			assertTrue(true);
		}
		catch (Exception e) {
			//Insert false
			assertTrue(false);
		} 	
 }

@Test


public void insertMessage228(){

	Person p = personService.find(Long.valueOf(2));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, we would like to offer you the open position in our firm.");

			message1.setMessageSubject("Job-Offer");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(2));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage328(){

	Person p = personService.find(Long.valueOf(3));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Suess, We are interested in giving you the position as a co-worker. For more details please call 472-90-76.");

			message1.setMessageSubject("Co-Worker Position");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(3));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage428(){

	Person p = personService.find(Long.valueOf(4));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We read your resumee and would like to invite you to an interview.");

			message1.setMessageSubject("Resumee");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(4));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage528(){

	Person p = personService.find(Long.valueOf(5));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mrs. Wald, Your application has been approved. Please call 234-54-89 for more information.");

			message1.setMessageSubject("Application");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(5));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage628(){

	Person p = personService.find(Long.valueOf(6));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, We'd like to offer you a job in our office.");

			message1.setMessageSubject("Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(6));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage728(){

	Person p = personService.find(Long.valueOf(7));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Sach, We have an open possition in our firm you would be suited for. Please call 874-98-02 if interested.");

			message1.setMessageSubject("Click-Match-Hire");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(7));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage828(){

	Person p = personService.find(Long.valueOf(8));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, I would like to offer you a part time job in my firm.");

			message1.setMessageSubject("Part Time Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(8));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage928(){

	Person p = personService.find(Long.valueOf(9));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mrs. Mauser, I would like to offer you the open position in my firm.");

			message1.setMessageSubject("Open Position");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(9));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1028(){

	Person p = personService.find(Long.valueOf(10));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, I read your application on Click-Match-Hire and want to offer you a position.");

			message1.setMessageSubject("Application Click-Match-Hire");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(10));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1128(){

	Person p = personService.find(Long.valueOf(11));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, Me and my team want to offer you a job in my firm.");

			message1.setMessageSubject("Job Offer");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(11));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1228(){

	Person p = personService.find(Long.valueOf(12));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We want to offer you a job for the season 2013/2014. Call 453-40-09 for more information.");

			message1.setMessageSubject("Seasonal Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(12));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1328(){

	Person p = personService.find(Long.valueOf(13));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Grim, I would like to offer you a position at Tech and Co.");

			message1.setMessageSubject("Tech and Co.");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(13));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1428(){

	Person p = personService.find(Long.valueOf(14));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Ms. Frick, Due to your application on Click-Match-Hire I would like to offer you a job at my office.");

			message1.setMessageSubject("Offering Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(14));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1528(){

	Person p = personService.find(Long.valueOf(15));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We would like to offer you the open position at Flips, Brothers and Co.");

			message1.setMessageSubject("Flips, Brothers and Co.");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(28));

			message1.setReceiverID(Long.valueOf(15));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }
@Test
public void insertMessage129(){
	Person p = personService.find(Long.valueOf(1));
		try {
			IMessaging message1 = mf.createMessage();
			message1.setMessageTitle("We'd like to offer you a job");
			message1.setMessageBody("Hi there, we've seen your application and we'd like to welcome you to meet with us! Please give me a ring on +41 1231 2211");
			message1.setMessageSubject("Offer");
			message1.setReceived(false);
			message1.setSenderID(Long.valueOf(29));
			message1.setReceiverID(Long.valueOf(1));
			message1.setSent(true);
			message1.setPerson(p);
			messagingService.update(message1);
			assertTrue(true);
		}
		catch (Exception e) {
			//Insert false
			assertTrue(false);
		} 	
 }

@Test


public void insertMessage229(){

	Person p = personService.find(Long.valueOf(2));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, we would like to offer you the open position in our firm.");

			message1.setMessageSubject("Job-Offer");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(2));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage329(){

	Person p = personService.find(Long.valueOf(3));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Suess, We are interested in giving you the position as a co-worker. For more details please call 472-90-76.");

			message1.setMessageSubject("Co-Worker Position");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(3));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage429(){

	Person p = personService.find(Long.valueOf(4));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We read your resumee and would like to invite you to an interview.");

			message1.setMessageSubject("Resumee");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(4));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage529(){

	Person p = personService.find(Long.valueOf(5));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mrs. Wald, Your application has been approved. Please call 234-54-89 for more information.");

			message1.setMessageSubject("Application");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(5));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage629(){

	Person p = personService.find(Long.valueOf(6));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, We'd like to offer you a job in our office.");

			message1.setMessageSubject("Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(6));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage729(){

	Person p = personService.find(Long.valueOf(7));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Sach, We have an open possition in our firm you would be suited for. Please call 874-98-02 if interested.");

			message1.setMessageSubject("Click-Match-Hire");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(7));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage829(){

	Person p = personService.find(Long.valueOf(8));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, I would like to offer you a part time job in my firm.");

			message1.setMessageSubject("Part Time Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(8));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage929(){

	Person p = personService.find(Long.valueOf(9));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mrs. Mauser, I would like to offer you the open position in my firm.");

			message1.setMessageSubject("Open Position");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(9));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1029(){

	Person p = personService.find(Long.valueOf(10));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hi, I read your application on Click-Match-Hire and want to offer you a position.");

			message1.setMessageSubject("Application Click-Match-Hire");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(10));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1129(){

	Person p = personService.find(Long.valueOf(11));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, Me and my team want to offer you a job in my firm.");

			message1.setMessageSubject("Job Offer");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(11));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1229(){

	Person p = personService.find(Long.valueOf(12));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We want to offer you a job for the season 2013/2014. Call 453-40-09 for more information.");

			message1.setMessageSubject("Seasonal Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(12));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1329(){

	Person p = personService.find(Long.valueOf(13));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Mr. Grim, I would like to offer you a position at Tech and Co.");

			message1.setMessageSubject("Tech and Co.");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(13));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1429(){

	Person p = personService.find(Long.valueOf(14));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Dear Ms. Frick, Due to your application on Click-Match-Hire I would like to offer you a job at my office.");

			message1.setMessageSubject("Offering Job");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(14));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

@Test


public void insertMessage1529(){

	Person p = personService.find(Long.valueOf(15));

		try {

			IMessaging message1 = mf.createMessage();


			message1.setMessageBody("Hello, We would like to offer you the open position at Flips, Brothers and Co.");

			message1.setMessageSubject("Flips, Brothers and Co.");

			message1.setReceived(false);

			message1.setSenderID(Long.valueOf(29));

			message1.setReceiverID(Long.valueOf(15));

			message1.setSent(true);

			message1.setPerson(p);
			
			
			// savemessage

			messagingService.save(message1);

			assertTrue(true);
			

		}

		catch (Exception e) {

			//Insert false

			assertTrue(false);

		} 	

 }

    @BeforeClass
    public static void setup() {
    	mf = new ModelFactory();
    	
    }
 

}
