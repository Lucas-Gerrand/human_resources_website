package hrrss.test.model;

import hrrss.model.IApplicantAnswer;
import hrrss.model.impl.ApplicantAnswer;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.CVService;
import hrrss.service.impl.SurveyService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_Answers {
	 ModelFactory modelFactory = new ModelFactory();
	 
	 @Autowired
	 ApplicantAnswerService aService;

	 @Test
	 public void TestAnswer() {
		 
		
		
		 
	 }
	 
	    
}
