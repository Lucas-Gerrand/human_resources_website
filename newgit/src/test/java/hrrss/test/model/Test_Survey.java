package hrrss.test.model;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IApplicantToSurvey;
import hrrss.model.IEmployer;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.ISurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.SubAnswer;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;

import org.hibernate.PropertyValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_Survey {
	 ModelFactory modelFactory = new ModelFactory();

	    @Autowired
	    SurveyService surveyService;

	    @Autowired
	    PersonService pS;
	    
	    
	    @Autowired
		ApplicantAnswerService aService;
	    
	    
	    @Test
	    public void testSurvey() {
	    
	    	//survey 1 
	    ISurvey s = modelFactory.createSurvey();
		s.setName("IT");
	    s.setText("Write something");
		
		//create applicants
		IApplicant applicant1 = modelFactory.createApplicant();
	    ((IPerson) applicant1).setEmail("t@t.ru");
	    pS.save((IPerson) applicant1);
	    
	    IApplicant applicant2 = modelFactory.createApplicant();
	    ((IPerson) applicant2).setEmail("a@t.ru");
	    pS.save((IPerson) applicant2);
	    
	    //create employers
	    IEmployer employer1 = modelFactory.createEmployer();
	    ((IPerson) employer1).setEmail("e1@t.ru");
	    pS.save((IPerson) employer1);
	    
	    IEmployer employer2 = modelFactory.createEmployer();
	    ((IPerson) employer2).setEmail("e2@t.ru");
	    pS.save((IPerson) employer2);
	    
	    s.setEmployer(employer1);
		
	    IQuastion quastion = modelFactory.createQuastion();
	    ((IQuastion) quastion).setQuastion("Discribe your experience");
	    ((IQuastion) quastion).setTypeOfQuastion("open");
	    ((IQuastion) quastion).setAdditionalInformation("write the text");
	    ((IQuastion) quastion).setSurvey(s);
	    
	    
	    IApplicantAnswer ans = modelFactory.createApplicantAnswer();
	    ans.setApplicant(applicant1);
	    ans.setQuestion(quastion);
	    
	    IApplicantAnswer ans1 = modelFactory.createApplicantAnswer();
	    ans1.setApplicant(applicant2);
	    ans1.setQuestion(quastion);
	    
	    
	    quastion.addAplAns(ans);
	    quastion.addAplAns(ans1);
	    s.addQuestion(quastion);
	    
	    
	    IQuastion quastion1 = modelFactory.createQuastion();
	    ((IQuastion) quastion1).setQuastion("What is your favorite programming language");
	    ((IQuastion) quastion1).setTypeOfQuastion("open");
	    ((IQuastion) quastion1).setAdditionalInformation("write the text");
	    ((IQuastion) quastion1).setSurvey(s);
	    
	    IApplicantAnswer ans2 = modelFactory.createApplicantAnswer();
	    ans2.setApplicant(applicant1);
	    ans2.setQuestion(quastion1);
	    
	    IApplicantAnswer ans3 = modelFactory.createApplicantAnswer();
	    ans3.setApplicant(applicant2);
	    ans3.setQuestion(quastion1);
	    
	    
	    quastion.addAplAns(ans2);
	    quastion.addAplAns(ans3);
	    
	    
	    s.addQuestion(quastion1);
	    
	    IQuastion quastion2 = modelFactory.createQuastion();
	    ((IQuastion) quastion2).setQuastion("Choice one software");
	    ((IQuastion) quastion2).setTypeOfQuastion("closed");
	    ((IQuastion) quastion2).setAdditionalInformation("write the text");
	    
	    SubAnswer sA = new SubAnswer();
	    sA.setSubAnswer("Windows");
	    ((IQuastion) quastion2).addSubA(sA);
	    SubAnswer sA1 = new SubAnswer();
	    sA1.setSubAnswer("Linux");
	    ((IQuastion) quastion2).addSubA(sA1);
	    SubAnswer sA2 = new SubAnswer();
	    sA2.setSubAnswer("Mac");
	    ((IQuastion) quastion2).addSubA(sA2);
	    sA.setQuestion(quastion2);
	    sA1.setQuestion(quastion2);
	    sA2.setQuestion(quastion2);
	    
	    
	    IApplicantAnswer ans4 = modelFactory.createApplicantAnswer();
	    ans4.setApplicant(applicant1);
	    ans4.setQuestion(quastion2);
	    
	    IApplicantAnswer ans5 = modelFactory.createApplicantAnswer();
	    ans5.setApplicant(applicant2);
	    ans5.setQuestion(quastion2);
	    
	    
	    quastion.addAplAns(ans4);
	    quastion.addAplAns(ans5);
	    
	    
	    ((IQuastion) quastion2).setSurvey(s);
	    s.addQuestion(quastion2);
	    
	    //servey 2
	    
	    ISurvey s1 = modelFactory.createSurvey();
		s1.setName("Praktikant");
		s1.setText("IT");
	    s1.setEmployer(employer2);
		
	    IQuastion quastion3 = modelFactory.createQuastion();
	    ((IQuastion) quastion3).setQuastion("Discribe your experience");
	    ((IQuastion) quastion3).setTypeOfQuastion("open");
	    ((IQuastion) quastion3).setAdditionalInformation("write the text");
	    ((IQuastion) quastion3).setSurvey(s1);
	    s1.addQuestion(quastion3);
	     
	    IQuastion quastion4 = modelFactory.createQuastion();
	    ((IQuastion) quastion4).setQuastion("Choice one software");
	    ((IQuastion) quastion4).setTypeOfQuastion("closed");
	    ((IQuastion) quastion4).setAdditionalInformation("write the text");
	    
	    SubAnswer sA3 = new SubAnswer();
	    sA.setSubAnswer("Windows");
	    ((IQuastion) quastion4).addSubA(sA3);
	    SubAnswer sA4 = new SubAnswer();
	    sA1.setSubAnswer("Linux");
	    ((IQuastion) quastion4).addSubA(sA4);
	    SubAnswer sA5 = new SubAnswer();
	    sA2.setSubAnswer("Mac");
	    ((IQuastion) quastion4).addSubA(sA5);
	    sA3.setQuestion(quastion4);
	    sA4.setQuestion(quastion4);
	    sA5.setQuestion(quastion4);
	    
	    
	    s.setDate(new Date());
	    s1.setDate(new Date());
	    
	    ((IQuastion) quastion4).setSurvey(s1);
	    s1.addQuestion(quastion4);
	   
	    surveyService.save((ISurvey) s);
		surveyService.save((ISurvey) s1);
	
	   
	    IApplicantToSurvey appToS1 = modelFactory.createApplicantToSurvey();
	    appToS1.setApplicant(applicant1);
	    appToS1.setServeys(s);
	    surveyService.saveAppSurvey((IApplicantToSurvey) appToS1);
	    
	    s.addApplicantToSurvey(appToS1);
	    
	    IApplicantToSurvey appToS2 = modelFactory.createApplicantToSurvey();
	    appToS2.setApplicant(applicant2);
	    appToS2.setServeys(s);
	    
	    s.addApplicantToSurvey(appToS2);
	    
	    surveyService.saveAppSurvey((IApplicantToSurvey) appToS2);
	    
	    IApplicantToSurvey appToS3 = modelFactory.createApplicantToSurvey();
	    appToS3.setApplicant(applicant2);
	    appToS3.setServeys(s1);
	    
	    s.addApplicantToSurvey(appToS3);
	    
	    surveyService.saveAppSurvey((IApplicantToSurvey) appToS3);
//	    s.addApplicant(applicant1);
//	    s.addApplicant(applicant2);
//	    s1.addApplicant(applicant2);
//	    
	    
	    
		try {
			System.out.println(quastion.getQuastion());
			
			IApplicantAnswer ans10 = modelFactory.createApplicantAnswer();
		//	ans10  = aService.find((long) 1);
			
			ans.setAnswer("yes");
			ans1.setAnswer("no");
			
			aService.update(ans);
			aService.update(ans1);
			
			List<ISurvey> listSurveys = surveyService.findAll();
			
			List<IApplicantToSurvey> appS = new ArrayList<IApplicantToSurvey>();
	    	
	    	appS = surveyService.loadAllSurveysByApplicant(new Long(1));
	    	
	    	System.out.println("SIZE: " + appS.size());
	    	
	    	appS = surveyService.loadNewAnswerByEmployer(false, new Long(4));
	    	
	    	System.out.println("SIZE: " + appS.size());
	    	
	    	appS = surveyService.loadAllAnswerByEmployer(new Long(3));
	    	
	    	System.out.println("SIZE: " + appS.size());
	    	
			
			assertEquals(2, listSurveys.size());
			
		} catch (PropertyValueException e) {
		    // expected
		}
	    }
	    
//	    @Test
//	    public void getSurveyByApplicant(){
//	    	
//	    	List<IApplicantToSurvey> surveys = new ArrayList<IApplicantToSurvey>();
//	       	Long id = 2L;
//	    	
//			surveys = surveyService.loadAllServeyByUser(id);
//			
//			for (IApplicantToSurvey survey : surveys){
//				System.out.println(survey.getId());
//			}
//			
//			
//	    }
//	    
//	    @Test
//	    public void getSurveyByEmployer(){
//	    	
//	    	List<ISurvey> surveys = new ArrayList<ISurvey>();
//	       	Long id = 4L;
//	    	
//			surveys = surveyService.loadAllServeyByEmployer(id);
//			
//			
////			for (ISurvey survey : surveys){
////				System.out.println("###Test Get SurveysByEmployer:" + survey.getId());
////			//	assertEquals(new Long(2), survey.getId());
////			}
//		//	System.out.println("###Test Get SurveysByEmployer:" + surveys.get(0).getId());
//		//	assertEquals(new Long(2), surveys.get(0).getId());
////			 
//	    }
//	    
//	    @Test
//	    public void getQuestionBySurvey(){
//	    	ISurvey sur = modelFactory.createSurvey();
//	    	Long id = 1L;
//	    	sur.setId(id);
//	    	
//	    	List<IQuastion> questions = new ArrayList<IQuastion>();
//	       	
//	    	
//			questions = surveyService.loadAllQuestionBySurvey(sur.getId());
//			
//			for (IQuastion question : questions){
//				System.out.println("Questions: "+ question.getId());
//			}
//			
//			//assertEquals(2, questions.size());
//
//			
//			//assertEquals(new Long(4), questions.get(0).getId());
//			//assertEquals(new Long(5), questions.get(1).getId());
//			
//	    }
//	    
//	    @Test(expected = NullPointerException.class)
//	    public void deleteSurvey(){
//	        ISurvey s3 = modelFactory.createSurvey();
//			s3.setAnsV(false);
//			s3.setDerA(false);
//			s3.setSurV(false);
//		    
//			//create applicants
//			IApplicant applicant10 = modelFactory.createApplicant();
//		    ((IPerson) applicant10).setEmail("a10@t.ru");
//		    pS.save((IPerson) applicant10);
//		    
//		    
//		    //create employers
//		    IEmployer employer10 = modelFactory.createEmployer();
//		    ((IPerson) employer10).setEmail("e10@t.ru");
//		    pS.save((IPerson) employer10);
//		    
//		    s3.setEmployer(employer10);
//		    
//		    
//		    IApplicantToSurvey appToS4 = modelFactory.createApplicantToSurvey();
//		    appToS4.setApplicant(applicant10);
//		    appToS4.setServeys(s3);
//		    
//		    s3.addApplicantToSurvey(appToS4);
//		  
//		    
//			//s3.addApplicant(applicant10);
//			
//		    IQuastion quastion10 = modelFactory.createQuastion();
//		    ((IQuastion) quastion10).setQuastion("Discribe your experience");
//		    ((IQuastion) quastion10).setTypeOfQuastion("open");
//		    ((IQuastion) quastion10).setAdditionalInformation("write the text");
//		    ((IQuastion) quastion10).setSurvey(s3);
//		    
//		    
//		    IApplicantAnswer ans10 = modelFactory.createApplicantAnswer();
//		    ans10.setApplicant(applicant10);
//		    ans10.setQuestion(quastion10);
//		   
//		    quastion10.addAplAns(ans10);
//
//		    s3.addQuestion(quastion10);
//		    
//		    
//		    
//			try {
//			
//				surveyService.save((ISurvey) s3);
//				surveyService.delete(s3);
//				ISurvey survey = surveyService.find(s3.getId());
//				
//				assertEquals(new Long(0), survey.getId());
//				
//				
//			} catch (PropertyValueException e) {
//			    // expected
//			}
//		    	
//	    }
//	    
////	    @Test
////	    public void testUpdateSurvey(){
////	    	ISurvey s4 = modelFactory.createSurvey();
////			s4.setAnsV(false);
////			s4.setDerA(false);
////			s4.setSurV(false);
////			
////			surveyService.save((ISurvey) s4);
////			
////			s4.setAnsV(true);
////			surveyService.update(s4);
////		    
////			ISurvey s10 = surveyService.find(s4.getId());
////			assertEquals(true, s10.getAnsV());
////	    }
//	    
//	    @Test
//	    public void testFindApplAns(){
//	    	ModelFactory modelFactory = new ModelFactory();
//			IApplicant app = modelFactory.createApplicant();
//			((IPerson) app).setId(new Long(1));
//			System.out.println("###SELECTED: " + ((IPerson) app).getId());
//			List<IApplicantAnswer> appl =  new ArrayList<IApplicantAnswer>();
//			appl= aService.findByQuestion(new Long(1), new Long(1));
//			
//			System.out.println("### TEST: " + appl.size());
//			
//			for (IApplicantAnswer app1: appl){
//				System.out.println("### TEST: " + app1.getQuestion().getQuastion());
//			}
//			
//			
//			System.out.println("### AplAns: "+ appl.get(0).getQuestion());
//			appl.get(0).setAnswer("new");
//			aService.update(appl.get(0));
//			
//			
//			//	IApplicantAnswer applNew = new ApplicantAnswer();
//		//		applNew.setAnswer(q.getValue());
//	    //		as.update(applNew);
//			
//	    }
	    
	    
//	    @Test
//	    public void findSurveyAndSetAnswer(){
//	    	List<IQuastion> questions = new ArrayList<IQuastion>();
//	    	List<ISurvey> surveys = new ArrayList<ISurvey>();
//	    	List<IApplicantAnswer> answers = new ArrayList<IApplicantAnswer>();
//	    	
//			IApplicantAnswer ans = modelFactory.createApplicantAnswer();
//			
//	       	Long id = 2L;
//	    	
//			surveys = surveyService.loadAllServeyByUser(id);
//			questions = surveyService.loadAllQuestionBySurvey(surveys.get(0).getId());
//			
//			for (IQuastion question : questions){
//				answers.add(aService.findByQuestion(question.getId()));
//			}
//
//			for (IApplicantAnswer answer : answers){
//				answer.setAnswer("yes");
//				aService.update(answer);
//			}
//		
//	    	
//	    }
	    
	    
	

}

