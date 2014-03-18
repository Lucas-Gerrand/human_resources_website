package hrrss.ui.applicant.cvcontact;

import hrrss.model.ICV;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CVContact extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    private CVService CVService;

    public CVContact() {
    	
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
		
		add(new Label("title", "Edit CV Contact"));
		
		
	
		Form form = new Form("editForm");
	
		
		
		
		List<ICV> listCV1 = CVService.loadCVById(id);
		CV myCV = (CV) listCV1.get(0);
		
		final TextField<String> nationality = new TextField<String>("nationality",Model.of(myCV.getNationality()));	
		final TextField<String> personalEmail = new TextField<String>("personalEmail",Model.of(myCV.getPersonalEmail()));	
		final TextField<String> personalWeb = new TextField<String>("personalWeb",Model.of(myCV.getPersonalWebsite()));	
		
		form.add(nationality);
		form.add(personalEmail);
		form.add(personalWeb);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	
		    	if(nationality.getValue().equals("")) {
		    		error("Nationality is required");
		    		error=true;
		    	}
		    	
		    	if(nationality.getValue().length() >= 100) {
		    		error("Maximum 100 characters");
		    		error=true;
		    	}
		    	
		    	
		    	if(personalEmail.getValue().equals("")) {
		    		error("Email is required");
		    		error=true;
		    	}
		    	
		    	if(personalEmail.getValue().length() >= 150) {
		    		error("Maximum 150 characters");
		    		error=true;
		    	}
		    	
		    	if(personalWeb.getValue().equals("")) {
		    		error("Website is required");
		    		error=true;
		    	}
		    	if(personalWeb.getValue().length() >= 150) {
		    		error("Maximum 150 characters");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
		        	
		    		List<ICV> listCV1 = CVService.loadCVById(id);
		    		CV myCV = (CV) listCV1.get(0);
		    		
		    		myCV.setNationality(nationality.getValue());
		    		myCV.setPersonalEmail(personalEmail.getValue());
		    		myCV.setPersonalWebsite(personalWeb.getValue());
		    		
		    		CVService.updateCV(myCV);
		    		
				    info("Changes Saved");
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}