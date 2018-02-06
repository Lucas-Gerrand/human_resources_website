package hrrss.ui.message;

import hrrss.model.IMessaging;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class Delete extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;

    private Person p;
    
    private static final long serialVersionUID = 1L;

    public Delete() {
    	 ModelFactory modelFactory = new ModelFactory();

    	 if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
		add(new Label("title", "Deleting.. "));
	
		
		String url = RequestCycle.get().getRequest().getUrl().toString();
     	String[] idFromUrl = url.split("/");
     	
     	Integer delId;
    	try {
    		delId = Integer.parseInt(idFromUrl[3]);
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
    		List<IMessaging>messageList = msService.loadAllInboxByPersonId(id);
    		IMessaging messageToDelete = messageList.get(delId);
    		msService.delete(messageToDelete);
    		setResponsePage(Inbox.class);
    } catch(Exception e) {
		setResponsePage(Inbox.class);
	}
	
     	
     	
    	
    		
    }

}