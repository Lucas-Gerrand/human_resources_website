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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * receive a instance of survey, which is choose, shows the question. This class
 * is created for employers view (the employer can read details of a survey). It
 * is not possible to edit the text Fields, choose correct answers, upload the
 * file.
 * 
 * @param s
 *            - an Instance of a Survey
 */
public class SurveyShowDetails extends BasePage{
	@SpringBean
	SurveyService ss;

	@SpringBean
	ApplicantAnswerService as;
	
	
    @SpringBean
    PersonService ps;

    final Logger logger = LoggerFactory.getLogger(SurveyShowDetails.class);
    
    private Person p;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * receive a survey, which use choose,
	 * shows the question
	 *  
	 * @param s - an Instance of a Survey
	 */
	public SurveyShowDetails(ISurvey s) {
		List<IQuastion> questions = new ArrayList<IQuastion>();
	
		final Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	p = ps.find(id);
   	
		add(new Label("surveyname", "' " + s.getName() + " '"));

		/*
		 * get question from the survey
		 */
		questions = s.getQuastions();
		final Form form = new Form("form");

		/*
		 * use for the PaqeableListView of the questions, one Page - one
		 * Question (the 3 variable)
		 */
		PageableListView questionView = new PageableListView("questions",
				questions, 10) {
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
							quastion.getId(), p.getId(), true));
				}

				// fragment for a closed question
				else if (quastion.getTypeOfQuastion().equals(
						new String("closed"))) {
					fragmentId = "variant_closed";

					item.add(new FragementForClosedQuestion(
							"placeholderForFragmente", fragmentId, this,
							quastion.getId(), p.getId(), true));
				}

				// fragment for an attach file question
				else if (quastion.getTypeOfQuastion().equals(
						new String("attach"))) {
					fragmentId = "variant_attach";
		
						item.add(new FragmentForAttachQuestion(
								"placeholderForFragmente", fragmentId, this,
								quastion.getId(), p.getId(), true, false));
				}
				//fragment for video
				else if (quastion.getTypeOfQuastion()
						.equals(new String("file"))) {
					fragmentId = "variant_file";
					logger.info("file");
					item.add(new FragmentForVideoUpload(
							"placeholderForFragmente", fragmentId,  this, quastion.getId(), p.getId(), false, true, false));

				}

				// end of fragments
			}

		};

		form.add(questionView);
		form.add(new PagingNavigator("navigator", questionView));

		add(form);

	}

}