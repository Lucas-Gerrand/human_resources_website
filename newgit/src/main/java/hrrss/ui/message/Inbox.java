package hrrss.ui.message;

import hrrss.model.IMessaging;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class Inbox extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	MessagingService msService;

	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(Inbox.class);
	
	@SuppressWarnings("null")
	public Inbox() {
		ModelFactory modelFactory = new ModelFactory();

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}

		add(new Label("title", "Your Inbox"));

		Form form = new Form("editForm");

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		List<IMessaging> inboxMessages = new ArrayList<IMessaging>();
		inboxMessages = msService.loadAllInboxByPersonId(id);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageableListView inboxMessagesNew = new PageableListView(
				"inboxMessagesNew", inboxMessages, 5) {
			int counter = 0;

			@Override
			protected void populateItem(ListItem item) {
				Long senderId = null;

				String userName = "";
				IMessaging messag = (IMessaging) item.getModelObject();
				// IPerson per = ps.find(((IPerson)
				// survey.getEmployer()).getId());
				senderId = messag.getSenderID();
				Person temp = ps.find(senderId);
				userName = temp.getFirstName() + " " + temp.getLastName();
				
				String messageBody = messag.getMessageBody();
				String messageBodyShort = shortenString(messageBody, 70);
				
				
				msService.update(messag);
				Long messageID = messag.getMessageId();

				int currentPage = item.getIndex();
				String text = "";

				if (messag.getReceived() == false) {
					text = "new";
				} else if (messag.getReceived() == true) {
					text = "";
				}
				final IModel<String> textModel = Model.of(text);
				
				
				item.add(new Label("newMail", textModel));
				
				String linkDelete = "<a href=\"/message/del/" + id + "/"
						+ currentPage + "\">delete</a>";
				String linkReply = "<a href=\"/message/replyNew/" + id + "/"
						+ senderId + "/" + currentPage + "\">reply</a>";
				String linkDetail = "<a href=\"/message/messagedetail/" + id + "/"
						+ currentPage + "\" style='text-decoration: none'> " + messageBodyShort + "</a>";
				
				item.add(new Label("MessageDelete", linkDelete)
						.setEscapeModelStrings(false));
				item.add(new Label("MessageReply", linkReply)
						.setEscapeModelStrings(false));
				item.add(new Label("MessageDate", messag.getDate()));
				item.add(new Label("MessageFrom", userName));
				item.add(new Label("MessageSubject", messag.getMessageSubject()));
				//item.add(new Label("MessageBody", messag.getMessageBody()));
				item.add(new Label("MessageDetail", linkDetail)
				.setEscapeModelStrings(false));
				counter++;
			};
		};
		add(inboxMessagesNew);
		add(new PagingNavigator("Inboxnavigator", inboxMessagesNew));

		List<IMessaging> sentList = msService.loadAllSentByPersonId(id);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageableListView sentMessagesNew = new PageableListView(
				"sentMessagesNew", sentList, 5) {
			@Override
			protected void populateItem(ListItem item) {
				Long senderId = null;

				String userName = "";
				IMessaging messag = (IMessaging) item.getModelObject();
				// IPerson per = ps.find(((IPerson)
				// survey.getEmployer()).getId());
				senderId = messag.getSenderID();

				Long receiverID = messag.getReceiverID();
				Person temp = ps.find(receiverID);
				userName = temp.getFirstName() + " " + temp.getLastName();
				
				msService.update(messag);
				Long messageID = messag.getMessageId();
				
				
				
				int currentPage = item.getIndex();

				String linkDelete = "<a href=\"/message/del/" + id + "/"
						+ currentPage + "\">delete</a>";
				String linkReply = "<a href=\"/message/replyNew/" + id + "/"
						+ receiverID + "/" + currentPage + "\">reply</a>";
				item.add(new Label("MessageDelete", linkDelete)
						.setEscapeModelStrings(false));
				item.add(new Label("MessageReply", linkReply)
						.setEscapeModelStrings(false));
				item.add(new Label("MessageDate", messag.getDate()));
				item.add(new Label("MessageTo", userName));
				item.add(new Label("MessageSubject", messag.getMessageSubject()));
				item.add(new Label("MessageBody", messag.getMessageBody()));

			};
 
		};
		add(sentMessagesNew);
		add(new PagingNavigator("Sentnavigator", sentMessagesNew));
		form.add(new FeedbackPanel("feedback"));

		add(form);

	}
	public String shortenString(String str, int number){
		String shortDesc;
		if(str.length() > number) {
			shortDesc = str.substring(0,number);
			shortDesc+=".....";
			
		} else {
			shortDesc=str;
		}
		return shortDesc;
	}
}