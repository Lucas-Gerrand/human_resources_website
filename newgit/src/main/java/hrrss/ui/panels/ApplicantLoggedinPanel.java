package hrrss.ui.panels;

import java.util.ArrayList;
import java.util.List;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.error.Authentication;
import hrrss.ui.message.Hire;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicantLoggedinPanel extends Panel {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;
    
    @SpringBean
    SurveyService ss;
    
    final Logger logger = LoggerFactory.getLogger(ApplicantLoggedinPanel.class);
    
    private Person p;
    
    public ApplicantLoggedinPanel(String id) {
	super(id);
	 ModelFactory modelFactory = new ModelFactory();
	// TODO Auto-generated constructor stub

	String msg;
	msg = getSession().getAttribute("firstname") + " "
		+ getSession().getAttribute("lastname");
	String count = "";
	
	add(new Label("logged_in", msg));
	String links = "<li><a href=\"/applicant/profile/"
		+ getSession().getAttribute("id") + "/\">My Profile</a></li>";

	String Messagelinks = "";
	try{
	
		Long personId = Long.valueOf(getSession().getAttribute("id").toString());
		Long countNew = Long.valueOf(0);
			countNew = 
				msService.countNewMessagesByPersonId(personId);
		
			List<IApplicantToSurvey> applicantSurveys = new ArrayList<IApplicantToSurvey>();
			applicantSurveys = ss.loadNewSurveysByApplicant(personId);
			String newSurveys = "";
			if (applicantSurveys.size() == 0){
				newSurveys = " (" + Integer.toString(0) + ")";
			} else {
			
		   newSurveys = " (" + Integer.toString(applicantSurveys.size()) + ")";
			}
			add(new Label("newsurvey", newSurveys).setEscapeModelStrings(false));
			
			
			
		String stringOfCount = "0"; 
		
		if(countNew > 0){
			stringOfCount =  String.valueOf(countNew);
		}
		
		
		
		Messagelinks = "<li><a href=\"/message/inbox/"
				+ getSession().getAttribute("id") + "/\">Message Inbox</a> (" +stringOfCount + ") </li>";
		    }
	catch (Exception e) {
		StackTraceElement[] stack = e.getStackTrace();
	    String exception = "";
	    for (StackTraceElement s : stack) {
	        exception = exception + s.toString() + "\n\t\t";
	    }
	    logger.info(e.toString());

	    }
	
	
	

	
	add(new Label("links", links).setEscapeModelStrings(false));
	add(new Label("Messagelinks", Messagelinks).setEscapeModelStrings(false));
	
    }

    private static final long serialVersionUID = 1L;

}
