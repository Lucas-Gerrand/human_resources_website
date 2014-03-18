package hrrss.ui.employer.job.del;

import hrrss.model.IJobDescription;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.employer.job.JobManager;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class DelJob extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    JobDescriptionService js;

    private Person p;
    
    private static final long serialVersionUID = 1L;

    public DelJob() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("a")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
		add(new Label("title", "Delete Jobs"));
	
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
		System.out.println(url);
		System.out.println(idFromUrl.length);
		if(idFromUrl.length != 4) {
			setResponsePage(JobManager.class);
			return;
		}
		
    	Integer delId;
    	try {
    		delId = Integer.parseInt(idFromUrl[3]);
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
        	
    		List<IJobDescription> list = new ArrayList<IJobDescription>();
        	
        	list = js.loadAllJobDescriptionByEmployerId(id);
        	
        	IJobDescription jsDel = list.get(delId);
        	
        	js.delete(jsDel);
        	
    		setResponsePage(JobManager.class);
    	} catch(Exception e) {
    		setResponsePage(JobManager.class);
    	}
    	
    
    	
    	
    }

}