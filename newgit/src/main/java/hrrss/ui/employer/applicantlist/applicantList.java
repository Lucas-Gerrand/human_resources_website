package hrrss.ui.employer.applicantlist;

import java.util.ArrayList;
import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.IPerson;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SavedApplicantService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.questionnaireform.AddSurveyForApplicant;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * list of applicants for employer
 * the employer can send a message, a questionnaire and delete the applicant
 * 
 * @author Irina
 *
 */
public class applicantList extends BasePage {
	
	@SpringBean
    SavedApplicantService saveAppService;
	
	@SpringBean
	PersonService ps;
	
	final Logger logger = LoggerFactory.getLogger(applicantList.class);
	
	public applicantList(){

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}

		add(new Label("title", "Your applicants"));

		final Form form = new Form("editForm");

		// FeedbackPanel  for the warning window//
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		logger.info("GET ID : " + id);
		List<IEmployerSavedApplicants> employerSavedApplicants = new ArrayList<IEmployerSavedApplicants>();
		employerSavedApplicants = saveAppService.loadAllSavedApplicantsByEmployerID(id);
		logger.info("SIZE of the List od applicants found: " + employerSavedApplicants.size());

		
		/*
		 *Info window with the message "Applicant deleted!" 
		 */
		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfo = new MessageDialog(
				"infoDialog", "Info",
				"Applicant deleted!",
				DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
				setResponsePage(applicantList.class);
			}
		};
		add(warningDialogInfo);
		
		
		/*
		 * Pageble list View for the List of the applicants (10 per page)
		 */
		PageableListView appView = new PageableListView<IEmployerSavedApplicants>("applicants",
				employerSavedApplicants, 10) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			/*
			 * list of applicants for the table "applicants"
			 */
			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(final ListItem<IEmployerSavedApplicants> item) {
				IEmployerSavedApplicants empAppl = (IEmployerSavedApplicants) item.getModelObject();
				IPerson per = ps.find(item.getModelObject().getApplicantID());
				String personName = per.getFirstName() + " " + per.getLastName();
				Long senderId = per.getId();
				String personProfileLink = "<a href=\"/applicant/profile/"+senderId+"\">"+personName+"</a>";
				item.add(new Label("firstName", personProfileLink).setEscapeModelStrings(false));
				
				item.add(new Label("mail", per.getEmail()));
				item.add(new Label("city", per.getAddress().getCity()));
				item.add(new Label("comment", item.getModelObject().getComment()));
				
				//AjaxLink to delete selected applicant, show the info message
				AjaxLink link = new AjaxLink("del", item.getModel()) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						IEmployerSavedApplicants selected = (IEmployerSavedApplicants) getModelObject();
						System.out.println(selected.getApplicantID());
						saveAppService.delete(selected);
						warningDialogInfo.open(target);
					}
				};
				item.add(link);
				
				
				String linkReply = "<a href=\"/message/messages/" + id + "/"
						+ senderId + "\">Send message</a>";
				item.add(new Label("send", linkReply)
					.setEscapeModelStrings(false));
				
				item.add(new Link("sendQuestions", item.getModel()){

					@Override
					public void onClick() {
						IEmployerSavedApplicants selected = (IEmployerSavedApplicants) getModelObject();
						final IApplicant applicant = (IApplicant) ps.find(selected.getApplicantID());
						setResponsePage(new AddSurveyForApplicant(((IPerson) applicant).getId()));
					}

				
				});
				
			}

		};
		

		form.add(new PagingNavigator("navigator", appView));
		form.add(appView);
		add(form);

	}

	
}
