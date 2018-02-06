package hrrss.ui.message;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
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

public class Message extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;

    private Person p;
    
    final Logger logger = LoggerFactory.getLogger(Message.class);
    private static final long serialVersionUID = 1L;

    public Message() {
    	 ModelFactory modelFactory = new ModelFactory();

    	 if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
		add(new Label("title", "Send Message "));
	
		
	
		final Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	p = ps.find(id);
    	String url = RequestCycle.get().getRequest().getUrl().toString();
     	String[] idFromUrl = url.split("/");
    	final Long receiverID = Long.valueOf(idFromUrl[3]);
    	//Person sender = ps.find(receiverID);
    	
    	Person sendPerson = ps.find(receiverID);
    	String senderNameLink ="";
    	String senderName = "";
    	if(sendPerson instanceof Applicant){
    		senderName = sendPerson.getFirstName() + " " + sendPerson.getLastName();
        	senderNameLink = "<a href=\"/applicant/profile/" + receiverID + "\">" + senderName + "</a><br>";
    	}
    	else{
    		Employer employer = (Employer) sendPerson;
    		if(!employer.getCompanyname().equals(" ") && employer.getCompanyname()!=null){
    			senderName = employer.getCompanyname();
    		}
    		else{
    			senderName = sendPerson.getFirstName() + " " + sendPerson.getLastName();
    		}
    		
        	senderNameLink = "<a href=\"/employer/profile/" + receiverID + "\">" + senderName + "</a><br>";
    	}
    	add(new Label("sendTo",senderNameLink).setEscapeModelStrings(false));
    	
    	
    	Form form = new Form("editForm");
    	
    	
    	//String senderName = sender.getFirstName() + " " + sender.getLastName();
    	
    	//final TextField<String> MessageTo  = new TextField<String>("MessageTo",Model.of(senderName));
		final TextField<String> Subject  = new TextField<String>("iTitle",Model.of(""));	
		final TextField<String> text = new TextField<String>("text",Model.of(""));
		
		//form.add(MessageTo);
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
		    	
		    	/*if(MessageTo.getValue().equals("")) {
		    		error("Please fill in who you want to send this to: ");
		    		error=true;
		    	}
*/		    	
		    	if(!error) {
		    		//Long tempSender = Long.valueOf(text.getValue());
		    		
		    		info("Subject is: " + Subject.getValue());
		    		info("Message is: " + text.getValue());
		    		
		    		Messaging msg = new Messaging();
		    		msg.setMessageSubject(Subject.getValue());
		    		msg.setMessageBody(text.getValue());
		    		msg.setReceiverID(receiverID);
		    		msg.setSent(true);
		    		msg.setReceived(false);
		    		msg.setSenderID(id);
		    		msg.setPerson(p);
		    		msService.save(msg);
		    		info("Message sent");
		    		
		    		
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