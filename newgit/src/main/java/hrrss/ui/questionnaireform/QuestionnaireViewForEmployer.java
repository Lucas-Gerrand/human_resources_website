package hrrss.ui.questionnaireform;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.ISurvey;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

public class QuestionnaireViewForEmployer extends BasePage {
	@SpringBean
	SurveyService ss;

	@SpringBean
	ApplicantAnswerService as;

	@SpringBean
	PersonService ps;

	final Logger logger = LoggerFactory
			.getLogger(FragmentForOpenQuestion.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	/**
	 * receive a survey, which use choose,
	 * shows the question
	 *  
	 * @param s - an Instance of a Survey
	 * @param per - an Instance of a Person
	 */
	public QuestionnaireViewForEmployer(ISurvey s, final IPerson per) {
		List<IQuastion> questions = new ArrayList<IQuastion>();

		final Form form = new Form("form");

		final List<IApplicantToSurvey> appSur = ss
				.loadAppSurveyByApplicantAndSurvey(per.getId(), s.getId());

		logger.info("SIZE FOUND: " + appSur.size());

		add(new Label("surveyname", "' " + s.getName() + " '"));

		add(new Label("applicantname", "Applicant name: " + per.getFirstName()
				+ " " + per.getLastName()));

		String text = "";

		if (appSur.get(0).getComment() != null) {
			text = appSur.get(0).getComment();
		}

		IModel<String> textModel = Model.of(text);
		logger.info(text);
		final TextArea<String> q = new TextArea<String>("comment", textModel);
		form.add(q);

		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfo = new MessageDialog("infoDialog",
				"Info", "Comment saved!", DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
			}
		};

		form.add(warningDialogInfo);

		form.add(new AjaxButton("submit") {

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				logger.info("text: " + q.getValue());
				appSur.get(0).setComment(q.getValue());
				ss.updateAppSurvey(appSur.get(0));
				warningDialogInfo.open(target);
			}

		});

		/*
		 * get question from the survey
		 */
		questions = s.getQuastions();

		/*
		 * use for the PaqeableListView of the questions, one Page - one
		 * Question (the 3 variable)
		 */
		PageableListView questionView = new PageableListView("questions",
				questions, 6) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 17946727937293369L;

			/*
			 * List of questions,add text label type of question and into the
			 * GUI
			 */
			@Override
			protected void populateItem(ListItem item) {
				IQuastion quastion = (IQuastion) item.getModelObject();
				item.add(new Label("number", quastion.getNumber() + ". "));
				item.add(new Label("text", quastion.getQuastion()));

				logger.info("APPLICANT ANSWER: " + quastion.getId());

				// fragments

				String fragmentId = null;
				logger.info("TYPE: " + quastion.getTypeOfQuastion());

				// fragment for an open question

				if (quastion.getTypeOfQuastion().equals(new String("open"))) {

					fragmentId = "variant_open";
					item.add(new FragmentForOpenQuestion(
							"placeholderForFragmente", fragmentId, this,
							quastion.getId(), per.getId(), false));
				}

				// fragment for a closed question
				else if (quastion.getTypeOfQuastion().equals(
						new String("closed"))) {
					fragmentId = "variant_closed";

					item.add(new FragementForClosedQuestion(
							"placeholderForFragmente", fragmentId, this,
							quastion.getId(), per.getId(), false));
				}

				// fragment for an attach file question
				else if (quastion.getTypeOfQuastion().equals(
						new String("attach"))) {
					fragmentId = "variant_attach";

					item.add(new FragmentForAttachQuestion(
							"placeholderForFragmente", fragmentId, this,
							quastion.getId(), per.getId(), false, false));

				}
				
				//fragment for video
				else if (quastion.getTypeOfQuastion()
						.equals(new String("file"))) {
					fragmentId = "variant_file";
					logger.info("file");
					item.add(new FragmentForVideoUpload(
							"placeholderForFragmente", fragmentId, this, quastion.getId(), per.getId(), false, false, true));

				}


				// end of fragments
			}

		};

		form.add(questionView);
		form.add(new PagingNavigator("navigator", questionView));

		add(form);

	}

}