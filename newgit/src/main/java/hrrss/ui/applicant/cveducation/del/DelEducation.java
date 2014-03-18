package hrrss.ui.applicant.cveducation.del;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cveducation.CVEducation;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class DelEducation extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    private CVService CVService;

    public DelEducation() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	add(new Label("title", "Delete Education"));
	
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
		
    	if(idFromUrl.length != 4) {
			setResponsePage(CVEducation.class);
			return;
		}
		
    	Integer delId;
    	try {
    		delId = Integer.parseInt(idFromUrl[3]);
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
        	       	
    		List<ICV> listCV1 = CVService.loadCVById(id);
        	CV myCV = (CV) listCV1.get(0);
        	
        	List<ICVEducation> educationList = myCV.getEducations();
        	ICVEducation removeEdu = educationList.get(delId);
        	CVService.deleteCVEducation(removeEdu);
        	
    		setResponsePage(CVEducation.class);
    	} catch(Exception e) {
    		setResponsePage(CVEducation.class);
    	}
    	
    
    	
    	
    }

}