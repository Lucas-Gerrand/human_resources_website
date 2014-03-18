package hrrss.ui.message;

import hrrss.model.IMessaging;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboxDetail extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	MessagingService msService;

	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(InboxDetail.class);
	
	@SuppressWarnings("null")
	public InboxDetail() {
		ModelFactory modelFactory = new ModelFactory();

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}
		
    	
		add(new Label("title", "Inbox"));

		Form form = new Form("editForm");

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		List<IMessaging> inboxMessages = new ArrayList<IMessaging>();
		inboxMessages = msService.loadAllInboxByPersonId(id);
		
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
     	int messageID = Integer.valueOf(idFromUrl[3]);
     	logger.info("message ID is: " + messageID);
     	
		IMessaging message = inboxMessages.get(messageID);
		message.setReceived(true);
		msService.update(message);
		
		String senderNameLink ="";
    	String senderName = "";
		Long receiverID = message.getReceiverID();
		Person sendPerson = ps.find(receiverID);
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
		String backButton="<input type=\"button\" onclick=\"location.href='/message/inbox/"+id+"/';\" class='button_example' value=\"Back to Inbox\"/>";
		add(new Label("back", backButton).setEscapeModelStrings(false));
		
    	add(new Label("sendTo",senderNameLink).setEscapeModelStrings(false));
		
		String messageSubect = message.getMessageSubject();
		String messageBody = "<tr><td colspan=\"2\">" + StringUtil.stringWithNewline(message.getMessageBody()) + "</td></tr>";
		
		add(new Label("subject", messageSubect));
		add(new Label("body", messageBody).setEscapeModelStrings(false));
	}

}