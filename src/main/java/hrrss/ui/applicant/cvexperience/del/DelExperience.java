package hrrss.ui.applicant.cvexperience.del;

import hrrss.model.ICV;
import hrrss.model.ICVExperience;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cvexperience.CVExperience;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class DelExperience extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    private CVService CVService;

    public DelExperience() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	add(new Label("title", "Delete Experience"));
	
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
		
    	if(idFromUrl.length != 4) {
			setResponsePage(CVExperience.class);
			return;
		}
		
    	Integer delId;
    	try {
    		delId = Integer.parseInt(idFromUrl[3]);
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
        	       	
    		List<ICV> listCV1 = CVService.loadCVById(id);
        	CV myCV = (CV) listCV1.get(0);
        	
        	List<ICVExperience> expList = myCV.getExperiences();
        	ICVExperience removeExp = expList.get(delId);
        	
        	CVService.deleteCVExperience(removeExp);
        	
    		setResponsePage(CVExperience.class);
    	} catch(Exception e) {
    		setResponsePage(CVExperience.class);
    	}
    	
    
    	
    	
    }

}