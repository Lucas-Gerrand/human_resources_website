package hrrss.ui.message;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.IMessaging;
import hrrss.model.IReply;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.Messaging;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.ReplyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;
import hrrss.ui.forgetpw.Password;
import hrrss.ui.home.Home;
import hrrss.ui.util.PasswordUtil;
import hrrss.ui.util.SendMail;
import hrrss.ui.util.StringUtil;

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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CopyOfReply extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;
    
    @Autowired
    ReplyService replyService;
    
    final Logger logger = LoggerFactory.getLogger(CopyOfReply.class);
    private Person p;
    private Long realMessageID = null;
    
    private static final long serialVersionUID = 1L;
     
    public CopyOfReply() {
    	String replyBody = "";
    	
    	
     	String url = RequestCycle.get().getRequest().getUrl().toString();
     	String[] idFromUrl = url.split("/");
     	final Long id = Long.valueOf(idFromUrl[2]);
     	final Long senderID = Long.valueOf(idFromUrl[3]);
     	final int messageID = Integer.valueOf(idFromUrl[4]);
     	
     	String replySubject = "RE: ";
     	String replyRE =  "RE: ";
     	
     	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
     	
     	
     	try {
    		List<IMessaging>messageList = msService.loadAllInboxByPersonId(id);
    		IMessaging messageToReply = messageList.get(messageID);
    		realMessageID = messageToReply.getMessageId();
    		logger.info("message id is: " + realMessageID);
    		replyBody = messageToReply.getMessageBody();
    		replySubject += messageToReply.getMessageSubject();
    		String testSubject="";
    		if(replySubject.contains(replyRE)){
    			int index = replySubject.lastIndexOf(replyRE);
    			int length= replySubject.length();
    			testSubject = replySubject.substring(index, length);
    			replySubject = testSubject;
    		}
    		
    		
     	} catch(Exception e) {
		setResponsePage(Inbox.class);
     	}
     	
     	logger.info("The reply subject is: " + replySubject);
     	add(new Label("title", "Send Message "));
	
		Form form = new Form("editForm");
	
		//final Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	p = ps.find(id);
    	Person sendPerson = ps.find(senderID);
    	String senderNameLink ="";
    	String senderName = "";
    	if(sendPerson instanceof Applicant){
    		senderName = sendPerson.getFirstName() + " " + sendPerson.getLastName();
        	senderNameLink = "<a href=\"/applicant/profile/" + senderID + "\">" + senderName + "</a><br>";
    	}
    	else{
    		Employer employer = (Employer) sendPerson;
    		if(!employer.getCompanyname().equals(" ") && employer.getCompanyname()!=null){
    			senderName = employer.getCompanyname();
    		}
    		else{
    			senderName = sendPerson.getFirstName() + " " + sendPerson.getLastName();
    		}
    		
        	senderNameLink = "<a href=\"/employer/profile/" + senderID + "\">" + senderName + "</a><br>";
    	}
    	add(new Label("sendTo",senderNameLink).setEscapeModelStrings(false));
    	//final TextField<String> sendTo  = new TextField<String>("sendTo", Model.of(senderName));
		final TextField<String> Subject  = new TextField<String>("iTitle",Model.of(replySubject));	
		final TextArea<String> text = new TextArea<String>("text",Model.of(""));
		
		//form.add(sendTo);
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
		    		// start to change things
		    		//ModelFactory modelFactory = new ModelFactory();
		    		
		    		IReply reply = new hrrss.model.impl.Reply();
		    		
		    		
		    		reply.setReplyPersonId(senderID);
		    		reply.setReplyMessageId(realMessageID);
		    		IMessaging message123 = (IMessaging) reply;
		    		message123.setMessageBody(text.getValue());
		    		
		    		message123.setMessageBody(text.getValue());
		    		message123.setMessageSubject(Subject.getValue());
		    		message123.setReceived(false);
		    		message123.setSenderID(id);
		    		message123.setReceiverID(senderID);
		    		message123.setSent(true);
		    		message123.setPerson(p);
		    		//message123.setMessageId(realMessageID);
		    		
		    		
		    		//reply.setReplyId(message123.getMessageId());
		    		//message123.getMessageId()
		    		//messagingService.update(message123);
		    		//replyService.update((IReply) message123);
		    		//replyService.delete(reply);
		    		msService.update(message123);
		    		
		    		
		    		/*
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
		    		*/
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
		
		List<IMessaging> newReply = new ArrayList<IMessaging>();
		replyList(realMessageID);
		newReply.addAll(resultList);
				//replyList(realMessageID); 
		
		//List<IMessaging> newReply = msService.loadAllRepliesByPersonId(realMessageID);
    	String allReplies = "";
    	try{
    		if(!newReply.isEmpty() && newReply!=null){
    			for(int i=0;i<newReply.size();i++){
    				List<IMessaging> messRep = msService.loadAllMessages(newReply.get(i).getMessageId());
    				logger.info("The message Id is: "+ newReply.get(i).getMessageId());
    				for(int j=0;j<messRep.size();j++){
    					allReplies = StringUtil.stringWithNewline(messRep.get(j).getMessageBody());
    					replyBody += "<tr><td colspan=\"2\">" + allReplies + "</td></tr>";
    				}
    				
    			}
    		}
    	}
    	
    	catch(Exception e){
    		
    		StackTraceElement[] stack = e.getStackTrace();
    	    String exception = "";
    	    for (StackTraceElement s : stack) {
    	        exception = exception + s.toString() + "\n\t\t";
    	    }
    	}
		form.add(new FeedbackPanel("feedback"));
		
    	
    	add(new Label("showJobs", replyBody).setEscapeModelStrings(false));
    	
    	
		add(form);
		/*
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageableListView inboxMessagesNew = new PageableListView("inboxMessagesNew", newReply, 5){
			*//**
			 * 
			 *//*
			private static final long serialVersionUID = 1L;
			int counter = 0;
			
			@Override
			protected void populateItem(ListItem item) {
				Long senderId = null;
				
				String userName="";
				try{
					IMessaging messag = (IMessaging) item.getModelObject();
					//IPerson per = ps.find(((IPerson) survey.getEmployer()).getId());
				
					item.add(new Label("MessageReply", messag.getMessageBody()));
					counter++;
				}
				catch(Exception e){
		    		
		    		StackTraceElement[] stack = e.getStackTrace();
		    	    String exception = "";
		    	    for (StackTraceElement s : stack) {
		    	        exception = exception + s.toString() + "\n\t\t";
		    	    }
		    	}
			};
		};
		add(inboxMessagesNew);
		add(new PagingNavigator("Inboxnavigator", inboxMessagesNew));
		
		*/

    }
    private List<IMessaging>resultList = new ArrayList<IMessaging>();
    private int counterReply =0;
    public void replyList(Long messageID){
    
    	
    	
    	
    		List<IMessaging> newReply = new ArrayList<IMessaging>();
    		newReply = msService.loadAllRepliesByPersonId(messageID);
    		
    		int counter = 0;
    		while(counterReply<newReply.size()){
    			
    			resultList.add(newReply.get(0));
    			logger.info("size is: " + resultList.size());
    			logger.info(" recursive message id is " + newReply.get(counter).getMessageId());
    			Long replyID = newReply.get(counter).getMessageId();
    			newReply.clear();
    			replyList(replyID);
    			counter++;
    			
    		}
    		counterReply++;
    	

    	
    }

}