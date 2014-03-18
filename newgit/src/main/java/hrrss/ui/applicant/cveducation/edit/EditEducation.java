package hrrss.ui.applicant.cveducation.edit;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cveducation.CVEducation;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.util.CheckUtils;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class EditEducation extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    private CVService CVService;

    public EditEducation() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
		
    	if(idFromUrl.length != 4) {
			setResponsePage(CVEducation.class);
			return;
		}
		
    	Integer editId;
    	ICVEducation editEdu=null;
    	try {
    		editId = Integer.parseInt(idFromUrl[3]);
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
	       	
    		getSession().setAttribute("editId", editId);
    		
    		List<ICV> listCV1 = CVService.loadCVById(id);
        	CV myCV = (CV) listCV1.get(0);
        	
        	List<ICVEducation> educationList = myCV.getEducations();
        	if(editId > educationList.size()) {
        		throw new RedirectToUrlException("/applicant/cveducation/");
        	}
        	editEdu = educationList.get(editId);
        	
    	
    	
    	} catch(Exception e) {
    		setResponsePage(CVEducation.class);
    		return;
    	}
    	
    	//Edit EDUCATION
    	final TextField<String> start = new TextField<String>("start",Model.of(editEdu.getStart()));	
		final TextField<String> end = new TextField<String>("end",Model.of(editEdu.getEnd()));	
		final TextField<String> location = new TextField<String>("location",Model.of(editEdu.getLocation()));
		final TextField<String> facility = new TextField<String>("facility",Model.of(editEdu.getFacility()));
		final TextArea<String> description = new TextArea<String>("description",Model.of(editEdu.getDescription()));
		
		Form form = new Form("editForm");
		
		form.add(start);
		form.add(end);
		form.add(location);
		form.add(facility);
		form.add(description);
		
		form.add(new Button("submit") {
			@Override
			
			public void onSubmit() {
				boolean error=false;
		    	
		    	if(start.getValue().equals("")) {
		    		error("Start is required");
		    		error=true;
		    	}
		    	
		    	if(end.getValue().equals("")) {
		    		error("End is required");
		    		error=true;
		    	}
		    	
		    	if(CheckUtils.checkDate(start.getValue()) == false) {
		    		error("Start date format yyyy-mm-ddd");
		    		error=true;
		    	}
		    	
		    	if(CheckUtils.checkDate(end.getValue()) == false) {
		    		error("End date format yyyy-mm-ddd");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		if(CheckUtils.checkTwoDates(start.getValue(), end.getValue()) == false) {
		    			error("Start date must be before end date");
		    			error=true;
		    		}
		    	}
		    	
		    	if(location.getValue().equals("")) {
		    		error("Location is required");
		    		error=true;
		    	}
		    	
		    	if(location.getValue().length() >= 100) {
		    		error("Location maximum 100 characters");
		    		error=true;
		    	}
		    	
		    	
		    	if(facility.getValue().equals("")) {
		    		error("University/School is required");
		    		error=true;
		    	}
		    	
		    	if(facility.getValue().length() >= 150) {
		    		error("University/School maximum 150 characters");
		    		error=true;
		    	}
		    	
		    	
		    	if(description.getValue().equals("")) {
		    		error("Description is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		Integer editId = Integer.parseInt((getSession().getAttribute("editId").toString()));
		    		
		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
			       	
		    		List<ICV> listCV1 = CVService.loadCVById(id);
		        	CV myCV = (CV) listCV1.get(0);
		        	
		        	List<ICVEducation> educationList = myCV.getEducations();
		        	ICVEducation editEduc = educationList.get(editId);
		        	educationList.remove(editEduc);
		        	
		        	editEduc.setStart(start.getValue());
		        	editEduc.setEnd(end.getValue());
		        	editEduc.setLocation(location.getValue());
		        	editEduc.setFacility(facility.getValue());
		        	editEduc.setDescription(description.getValue());
		        	educationList.add(editEduc);
		        	
		    		CVService.updateCV(myCV);
		    		info("Changed Successfully");
		    	}
	    	
			}
		});
		
		form.add(new Button("backToEdu") {
			@Override
		
			public void onSubmit() {
			    throw new RedirectToUrlException("/applicant/cveducation/");
			}
		});
		
		form.add(new Button("backToCV") {
			@Override
		
			public void onSubmit() {
				Long id = Long.valueOf(getSession().getAttribute("id").toString());
			    throw new RedirectToUrlException("/applicant/cv/"+id+"/");
			}
		});
		
		form.add(new FeedbackPanel("feedback"));
		add(form);
		
    	
    }

}