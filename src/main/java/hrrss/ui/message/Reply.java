package hrrss.ui.message;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.IMessaging;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.Messaging;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;
import hrrss.ui.home.Home;
import hrrss.ui.util.PasswordUtil;
import hrrss.ui.util.SendMail;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Reply extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;
    
    final Logger logger = LoggerFactory.getLogger(Reply.class);
    
    private Person p;
  
    private static final long serialVersionUID = 1L;

    public Reply() {
    	 ModelFactory modelFactory = new ModelFactory();

     	String url = RequestCycle.get().getRequest().getUrl().toString();
     	String[] idFromUrl = url.split("/");
     	final Long id = Long.valueOf(idFromUrl[2]);
     	final Long senderID = Long.valueOf(idFromUrl[3]);
     	
     	String replySubject = "RE: ";
     	
     	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
     	
     	logger.info("The reply subject is: " + replySubject);
     	add(new Label("title", "Send Message "));
	
		Form form = new Form("editForm");
	
		//final Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	p = ps.find(id);
    	Person sendPerson = ps.find(senderID);
    	String senderName = "";
    	senderName = sendPerson.getFirstName() + " " + sendPerson.getLastName();
    	
    	final TextField<String> sendTo  = new TextField<String>("sendTo", Model.of(senderName));
		final TextField<String> Subject  = new TextField<String>("iTitle",Model.of(replySubject));	
		final TextArea<String> text = new TextArea<String>("text",Model.of(""));
		
		form.add(sendTo);
		form.add(Subject);
		form.add(text);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	try{
		    	boolean error=false;
		    	
		    	if(Subject.getValue().equals("")) {
		    		error("Subject is required");
		    		error=true;
		    	}
		    	
		    	if(Subject.getValue().length() >= 100) {
		    		error("Subject maximum 100 Chararacters");
		    		error=true;
		    	}
		    	
		    	if(text.getValue().equals("")) {
		    		error("Main body of message is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		
		    		Messaging msg = new Messaging();
		    		msg.setMessageSubject(Subject.getValue());
		    		msg.setMessageBody(text.getValue());
		    		msg.setReceiverID(senderID);
		    		msg.setReceived(false);
		    		msg.setSent(true);
		    		msg.setSenderID(id);
		    		msg.setPerson(p);
		    		msService.save(msg);
		    		info("Message sent");
		    		
		    		setResponsePage(Inbox.class);
		    		
		    		
		    	}
		    	}
		    	catch(Exception e){
		    		
		    		StackTraceElement[] stack = e.getStackTrace();
		    	    String exception = "";
		    	    for (StackTraceElement s : stack) {
		    	        exception = exception + s.toString() + "\n\t\t";
		    	    }
		    	    logger.info(exception);

		    		
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
		
		
    	String jobs="";
    	
    	add(new Label("showJobs", jobs).setEscapeModelStrings(false));
    	
		add(form);

    }

}