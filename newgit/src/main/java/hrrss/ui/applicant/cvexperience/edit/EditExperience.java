package hrrss.ui.applicant.cvexperience.edit;

import hrrss.model.ICV;
import hrrss.model.ICVExperience;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cveducation.CVEducation;
import hrrss.ui.applicant.cvexperience.CVExperience;
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

public class EditExperience extends BasePage {

	@SpringBean
    PersonService ps;

    @SpringBean
    private CVService CVService;

    public EditExperience() {
    	
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
    	ICVExperience editExp=null;
    	try {
    		editId = Integer.parseInt(idFromUrl[3]);
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
	       	
    		getSession().setAttribute("editId", editId);
    		
    		List<ICV> listCV1 = CVService.loadCVById(id);
        	CV myCV = (CV) listCV1.get(0);
        	
        	List<ICVExperience> expList = myCV.getExperiences();
        	
        	if(editId > expList.size()) {
        		throw new RedirectToUrlException("/applicant/cvexperience/");
        	}
        	editExp = expList.get(editId);
        	
    	
    	
    	} catch(Exception e) {
    		setResponsePage(CVExperience.class);
    		return;
    	}
    	
    	//Edit EDUCATION
    	final TextField<String> start = new TextField<String>("start",Model.of(editExp.getStart()));	
		final TextField<String> end = new TextField<String>("end",Model.of(editExp.getEnd()));	
		final TextField<String> company = new TextField<String>("company",Model.of(editExp.getCompany()));
		final TextField<String> title = new TextField<String>("title",Model.of(editExp.getTitle()));
		final TextArea<String> description = new TextArea<String>("description",Model.of(editExp.getDescription()));
		
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
		    		Integer editId = Integer.parseInt((getSession().getAttribute("editId").toString()));
		    		
		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
			       	
		    		List<ICV> listCV1 = CVService.loadCVById(id);
		        	CV myCV = (CV) listCV1.get(0);
		        	
		        	List<ICVExperience> expList = myCV.getExperiences();
		        	ICVExperience editExp = expList.get(editId);
		        	
		        	expList.remove(editExp);
		        	
		        	editExp.setStart(start.getValue());
		        	editExp.setEnd(end.getValue());
		        	editExp.setCompany(company.getValue());
		        	editExp.setTitle(title.getValue());
		        	editExp.setDescription(description.getValue());
		        	
		        	expList.add(editExp);
		        	
		    		CVService.updateCV(myCV);
		    		info("Changed Successfully");
		    	}
	    	
			}
		});
		
		form.add(new Button("backToExp") {
			@Override
		
			public void onSubmit() {
			    throw new RedirectToUrlException("/applicant/cvexperience/");
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