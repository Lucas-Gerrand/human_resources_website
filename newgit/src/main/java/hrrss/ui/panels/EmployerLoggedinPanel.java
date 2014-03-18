package hrrss.ui.panels;

import java.util.ArrayList;
import java.util.List;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.impl.Person;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.error.Authentication;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployerLoggedinPanel extends Panel {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;

    @SpringBean
	SurveyService ss;
    
    private Person p;
    final Logger logger = LoggerFactory.getLogger(EmployerLoggedinPanel.class);
    public EmployerLoggedinPanel(String id) {
	super(id);
	// TODO Auto-generated constructor stub
	String msg;
	String count = "";
	msg = getSession().getAttribute("firstname") + " "
		+ getSession().getAttribute("lastname");
	add(new Label("logged_in", msg));
	String links = "<li><a href=\"/employer/profile/"
		+ getSession().getAttribute("id") + "/\">My Profile</a></li>";
	String Messagelinks = "";
	try{
	
	Long personId = Long.valueOf(getSession().getAttribute("id").toString());
	Long countNew = Long.valueOf(0);
		countNew = 
			msService.countNewMessagesByPersonId(personId);
	
	String stringOfCount = "0"; 
	
	if(countNew > 0){
		stringOfCount =  String.valueOf(countNew);
	}
	
	List<IApplicantToSurvey> applicantSurveys = new ArrayList<IApplicantToSurvey>();
	applicantSurveys = ss.loadNewAnswerByEmployer(true, personId);
	
	String newSurveys = "";
	if (applicantSurveys.size() == 0){
		newSurveys = " (" + Integer.toString(0) + ")";
	} else {
	
   newSurveys = " (" + Integer.toString(applicantSurveys.size()) + ")";
	}
	add(new Label("newsurvey", newSurveys).setEscapeModelStrings(false));
	
	
	
	
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
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

}
