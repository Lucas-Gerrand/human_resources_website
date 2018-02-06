package hrrss.ui.questionnaireform;

import hrrss.model.IQuastion;
import hrrss.model.ISurvey;
import hrrss.model.impl.Person;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * use for the questionnaire, add questions from the DB according a Survey,
 * shows the questions according one of the fragment.
 * 
 * @author Irina
 * 
 */
public class Questionnaire extends BasePage {
	@SpringBean
	SurveyService ss;

	@SpringBean
	ApplicantAnswerService as;

	@SpringBean
	PersonService ps;

	final Logger logger = LoggerFactory.getLogger(Questionnaire.class);

	private Person p;

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
	 */
	public Questionnaire(ISurvey s) {
		List<IQuastion> questions = new ArrayList<IQuastion>();

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);

		add(new Label("surveyname", "' " + s.getName() + " '"));

		/*
		 * get question from the survey
		 */
		questions = s.getQuastions();
		final Form form = new Form("form");
		
		// Enable multipart mode (need for uploads file)
				form.setMultiPart(true);
		 
				// max upload size, 10k
				form.setMaxSize(Bytes.megabytes(50));

		form.add(new Button("inbox") {

			@Override
			public void onSubmit() {
				setResponsePage(QuestionReceived.class);
			}

		});

		/*
		 * use for the PaqeableListView of the questions, one Page - one
		 * Question (the 3 variable)
		 */
		PageableListView questionView = new PageableListView("questions",
				questions, 1) {
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
							quastion.getId(), p.getId(), false));
				}

				// fragment for a closed question
				else if (quastion.getTypeOfQuastion().equals(
						new String("closed"))) {
					fragmentId = "variant_closed";
					logger.info("fragment closed");
					item.add(new FragementForClosedQuestion(
							"placeholderForFragmente", fragmentId, this,
							quastion.getId(), p.getId(), false));
				}

				// fragment for an attach file question
				else if (quastion.getTypeOfQuastion().equals(
						new String("attach"))) {
					fragmentId = "variant_attach";
					logger.info("fragment attach");
					item.add(new FragmentForAttachQuestion(
							"placeholderForFragmente", fragmentId, this,
							quastion.getId(), p.getId(), false, true));

				}

				else if (quastion.getTypeOfQuastion()
						.equals(new String("file"))) {
					fragmentId = "variant_file";
					logger.info("file");
					item.add(new FragmentForVideoUpload(
							"placeholderForFragmente", fragmentId, this, quastion.getId(), p.getId(), true, false, false));

				}

				// end of fragments
			}

		};

		form.add(questionView);
		form.add(new PagingNavigator("navigator", questionView));
		form.add(new FeedbackPanel("feedback"));
		add(form);

	}

}
