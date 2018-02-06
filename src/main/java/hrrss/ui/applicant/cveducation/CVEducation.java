package hrrss.ui.applicant.cveducation;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cv.Cv;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.util.CheckUtils;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CVEducation extends BasePage {
    @SpringBean
    private PersonService ps;

    @SpringBean
    private CVService CVService;

    public CVEducation() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	
    	
    	//EDIT EDUCATION
    	List<ICV> listCV1 = CVService.loadCVById(id);
    	CV myCV = (CV) listCV1.get(0);
    	
    	List<ICVEducation> educationList = myCV.getEducations();
    	String contentEducation="";
    	if(educationList.size() == 0) {
    		contentEducation="<tr><td>No education added</td></tr>";
    	} else {
    		for(int i=0; i<educationList.size(); i++) {
    			ICVEducation education = educationList.get(i);
    			String start = education.getStart().toString();
    			String end = education.getEnd().toString();
    			String educationPlace = education.getFacility();
    			String location = education.getLocation();
    			String description = education.getDescription();
    			
    			String oneContent = "<tr><td width=\"200\"><b>"+start+" till "+end+"<b></td><td width=\"300\"><b>"+location+" - "+educationPlace+"<b></td><td width=\"50\"><a href=\"/applicant/cveducation/edit/"+i+"/\">Edit</a></td><td width=\"50\"><a href=\"/applicant/cveducation/del/"+i+"/\">Delete</a></td></tr>";
    			contentEducation += oneContent;
    		}
    		
    	}
    	
    	add(new Label("educationContent", contentEducation).setEscapeModelStrings(false));
    	String backButton="<input type=\"button\" onclick=\"location.href='/applicant/cv/"+id+"/';\" class='button_example' value=\"Back to CV\"/>";
    	
    	add(new Label("back", backButton).setEscapeModelStrings(false));
    	
    	
    	//ADD NEW EDUCATION
    	final TextField<String> start = new TextField<String>("start",Model.of(""));	
		final TextField<String> end = new TextField<String>("end",Model.of(""));	
		final TextField<String> location = new TextField<String>("location",Model.of(""));
		final TextField<String> facility = new TextField<String>("facility",Model.of(""));
		final TextArea<String> description = new TextArea<String>("description",Model.of(""));
		
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
		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
		        	
		    		List<ICV> listCV1 = CVService.loadCVById(id);
		    		CV myCV = (CV) listCV1.get(0);
		    		
		    		List<ICVEducation> newEducationList = myCV.getEducations();
		    		
		    		hrrss.model.impl.CVEducation newEdu = new hrrss.model.impl.CVEducation();
		    		newEdu.setCv(myCV);
		    		newEdu.setStart(start.getValue());
		    		newEdu.setEnd(end.getValue());
		    		newEdu.setLocation(location.getValue());
		    		newEdu.setFacility(facility.getValue());
		    		newEdu.setDescription(description.getValue());
		    		
		    		
		    		newEducationList.add(newEdu);
		    		
		    		CVService.updateCV(myCV);
		    		
				    setResponsePage(CVEducation.class);
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}