package hrrss.ui.message;

import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.model.impl.SavedApplicants;
import hrrss.service.impl.ApplicantService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SavedApplicantService;
import hrrss.ui.BasePage;
import hrrss.ui.employer.search.Search;
import hrrss.ui.error.Authentication;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * Class for saving a applicant to the contact list
 * has two AJAX buttons:
 * "yes" - saved the applicant
 * "no" - set response Page "Search"
 * @author Irina
 *
 */
public class SaveApplicant extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    MessagingService msService;

    @SpringBean
    ApplicantService apService;
    
    @SpringBean
    SavedApplicantService saveAppService;
    
    
	final Logger logger = LoggerFactory.getLogger(SaveApplicant.class);
	
	private Person p;
	
    private static final long serialVersionUID = 1L;

	@SuppressWarnings("null")
    public SaveApplicant() {
    	 

    	 if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    		
		final Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	p = ps.find(id);
    	String hiredBy = p.getFirstName() + " " + p.getLastName();
    	String url = RequestCycle.get().getRequest().getUrl().toString();
     	String[] idFromUrl = url.split("/");
    	final Long receiverID = Long.valueOf(idFromUrl[3]);
    	final Integer jobID = Integer.valueOf(idFromUrl[4]);
    	
    	logger.info("The job id is: " + jobID);
    	
    	
    	
    	add(new Label("title", "Save Applicant "));
    	
    	
    	
    	@SuppressWarnings("serial")
		final MessageDialog warningDialogError = new MessageDialog(
				"errorDialog", "Error",
				"You have allready this applicant in your contact list!",
				DialogButtons.OK, DialogIcon.ERROR) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
			}
		};
		add(warningDialogError);
		
		
		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfo = new MessageDialog(
				"infoDialog", "Info",
				"The applicant successfully saved!",
				DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
				setResponsePage(Search.class);
			}
		};
		add(warningDialogInfo);
		
    	
    	Applicant applicant = (Applicant) ps.find(receiverID);
    	
		String appName = "Would you like to add the applicant "
				+ applicant.getFirstName() + " " + applicant.getLastName()
				+ " in your applicants List?";
    	
		add(new Label("question", Model.of(appName)));
		
		final Form form = new Form("editForm");
		add(new Label("addComment", Model.of("You can add a cooment: ")));
		String text = "";
		IModel<String> textModel = Model.of(text);
		final TextArea<String> textArea = new TextArea<String>("comment", textModel);
		form.add(textArea);
		
		form.add(new AjaxButton("warningAdd", new Model<String>("Add")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (saveAppService.loadSavedApplicantByID(receiverID,id).size() != 0){
		    		warningDialogError.open(target);
		    	} else if (saveAppService.loadSavedApplicantByID(receiverID,id).size() == 0){
		    		
		    		Employer employer1 = (Employer) ps.find(id);
		        	
		    	    SavedApplicants savedApplicants = new SavedApplicants();		    	    
		    	    savedApplicants.setApplicantID(receiverID);
		    	    savedApplicants.setPerson((Person) employer1);
		    	    savedApplicants.setComment(textArea.getValue());
		    	    saveAppService.save(savedApplicants);
		    	    
		    	    warningDialogInfo.open(target);
		    	}
				
			}
		});
		
		form.add(new AjaxButton("No", new Model<String>("No")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(Search.class);
			}
		});

		add(form);
    }

}