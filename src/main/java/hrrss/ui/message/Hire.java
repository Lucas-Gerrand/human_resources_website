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
import hrrss.service.impl.ApplicantService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class Hire extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;

    @SpringBean
    ApplicantService apService;
    
	@SpringBean
	private JobDescriptionService js;
    
	final Logger logger = LoggerFactory.getLogger(Hire.class);
	
	private Person p;
    
    private static final long serialVersionUID = 1L;

    public Hire() {
    	 ModelFactory modelFactory = new ModelFactory();

    	 if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
		add(new Label("title", "Send Message "));
	
		Form form = new Form("editForm");
	
		final Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	p = ps.find(id);
    	String hiredBy = p.getFirstName() + " " + p.getLastName();
    	String url = RequestCycle.get().getRequest().getUrl().toString();
     	String[] idFromUrl = url.split("/");
    	final Long receiverID = Long.valueOf(idFromUrl[3]);
    	final Integer jobID = Integer.valueOf(idFromUrl[4]);
    	
    	logger.info("The job id is: " + jobID);
    	
    	List<IJobDescription> jobDescriptionList = js.loadAllJobDescriptionByEmployerId(id);
    	IJobDescription job = jobDescriptionList.get(jobID);
    	logger.info("job title: " + job.getJobTitle());
    	Long jobActualID = job.getId();
    	
    	
    	logger.info("You want to hire: " + receiverID);
    	Applicant applicant = (Applicant) ps.find(receiverID);
		applicant.setHired(jobActualID);
		
		Date date = new Date();
		
		applicant.setHiredDate(date);
		apService.update(applicant);
		
    	Person sender = ps.find(receiverID);
    	String senderName = sender.getFirstName() + " " + sender.getLastName();
    	
    	
    	String senderNameLink ="";
    	
    	if(sender instanceof Applicant){
    		senderName = sender.getFirstName() + " " + sender.getLastName();
        	senderNameLink = "<a href=\"/applicant/profile/" + receiverID + "\">" + senderName + "</a><br>";
    	}
    	else{
    		Employer employer = (Employer) sender;
    		if(!employer.getCompanyname().equals(" ") && employer.getCompanyname()!=null){
    			senderName = employer.getCompanyname();
    		}
    		else{
    			senderName = sender.getFirstName() + " " + sender.getLastName();
    		}
    		
        	senderNameLink = "<a href=\"/employer/profile/" + receiverID + "\">" + senderName + "</a><br>";
    	}
    	add(new Label("sendTo",senderNameLink).setEscapeModelStrings(false));
    	
    	String hireMessage = "Dear " + senderName + ", \n" + "Congratulations! " + hiredBy + " would like to hire you. \nKind Regards, \nClick Match Hire team";
    	//final TextField<String> MessageTo  = new TextField<String>("MessageTo",Model.of(senderName));
		final TextField<String> Subject  = new TextField<String>("iTitle",Model.of("Congratulations!"));	
		final TextArea<String> text = new TextArea<String>("text",Model.of(hireMessage));
		
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