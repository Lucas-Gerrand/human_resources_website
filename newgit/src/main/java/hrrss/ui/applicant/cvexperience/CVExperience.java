package hrrss.ui.applicant.cvexperience;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
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

public class CVExperience extends BasePage {
    @SpringBean
    private PersonService ps;

    @SpringBean
    private CVService CVService;

    public CVExperience() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	String backButton="<input type=\"button\" onclick=\"location.href='/applicant/cv/"+id+"/';\" class='button_example' value=\"Back to CV\"/>";
		
    	add(new Label("back", backButton).setEscapeModelStrings(false));
	
    	
    	//EDIT Experience
    	List<ICV> listCV1 = CVService.loadCVById(id);
    	CV myCV = (CV) listCV1.get(0);
    	
    	List<ICVExperience> expList = myCV.getExperiences();
    	
    	String contentExp="";
    	if(expList.size() == 0) {
    		contentExp="<tr><td>No experience added</td></tr>";
    	} else {
    		for(int i=0; i<expList.size(); i++) {
    			ICVExperience exp = expList.get(i);
    			
    			String start = exp.getStart().toString();
    			String end = exp.getEnd().toString();
    			String title = exp.getTitle();
    			String company = exp.getCompany();
    			String description = exp.getDescription();
    			
    			String oneContent = "<tr><td width=\"200\"><b>"+start+" till "+end+"<b></td><td width=\"300\"><b>"+company+"</b> - "+title+"</td><td width=\"50\"><a href=\"/applicant/cvexperience/edit/"+i+"/\">Edit</a></td><td width=\"50\"><a href=\"/applicant/cvexperience/del/"+i+"/\">Delete</a></td></tr>";
    			contentExp += oneContent;
    		}
    		
    	}
    	
    	add(new Label("experienceContent", contentExp).setEscapeModelStrings(false));
    	
    	
    	
    	//ADD NEW EDUCATION
    	final TextField<String> start = new TextField<String>("start",Model.of(""));	
		final TextField<String> end = new TextField<String>("end",Model.of(""));	
		final TextField<String> company = new TextField<String>("company",Model.of(""));
		final TextField<String> title = new TextField<String>("title",Model.of(""));
		final TextArea<String> description = new TextArea<String>("description",Model.of(""));
		
		Form form = new Form("editForm");
		
		form.add(start);
		form.add(end);
		form.add(company);
		form.add(title);
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
		    	
		    	if(company.getValue().equals("")) {
		    		error("Company is required");
		    		error=true;
		    	}
		    	
		    	if(company.getValue().length() >= 100) {
		    		error("Company maximum 100 characters");
		    		error=true;
		    	}
		    	
		    	if(title.getValue().equals("")) {
		    		error("Title is required");
		    		error=true;
		    	}
		    	
		    	if(title.getValue().length() >= 150) {
		    		error("Title maximum 150 characters");
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
		    		
		    		List<ICVExperience> expList = myCV.getExperiences();
		    		
		    		hrrss.model.impl.CVExperience newExp = new hrrss.model.impl.CVExperience();
		    		newExp.setCv(myCV);
		    		newExp.setStart(start.getValue());
		    		newExp.setEnd(end.getValue());
		    		newExp.setCompany(company.getValue());
		    		newExp.setTitle(title.getValue());
		    		newExp.setDescription(description.getValue());
		    		
		    		
		    		expList.add(newExp);
		    		
		    		CVService.updateCV(myCV);
		    		
				    setResponsePage(CVExperience.class);
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}