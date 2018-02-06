package hrrss.ui.questionnaireform;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hrrss.model.IApplicant;
import hrrss.model.IPerson;
import hrrss.model.ISurvey;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

/**
 * The UI for adding a survey for the applicant. It is important that the class
 * receive an instance of the applicant with his/her id. The class uses
 * services: "PersonService" and "SurveyService" for saving information about
 * the sending survey into the Table "ApplicantToSurvey" (saves ID of the Survey
 * and ID of the applicant, sets also variables: Newqa, Visapp on true. It means
 * that the applicant can see the new questionnaire and the identifier "true"
 * will be appear
 * 
 * @author Irina
 * 
 */
@SuppressWarnings("serial")
public class AddSurveyForApplicant extends BasePage {

	@SpringBean
	PersonService ps;

	@SpringBean
	SurveyService ss;

	final Logger logger = LoggerFactory.getLogger(AddSurveyForApplicant.class);

	private Person p;

	/**
	 * the classes constructor applicant - an instance of e applicant for the
	 * survey
	 * 
	 * @param applicant
	 */
	@SuppressWarnings("rawtypes")
	public AddSurveyForApplicant() {
		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}
		if (getSession().getAttribute("personTyp").equals("a")) {
			setResponsePage(PersonTyp.class);
			return;
		}

		Long id = Long.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);

		String url = RequestCycle.get().getRequest().getUrl().toString();
		String[] idFromUrl = url.split("/");
		final Long receiverID = Long.valueOf(idFromUrl[3]);
		final IApplicant applicant = (IApplicant) ps.find(receiverID);

		// final IApplicant applicant
		List<ISurvey> surveys = new ArrayList<ISurvey>();
		surveys = ss.loadAllServeyByEmployer(id);
		logger.info("surveyes loaded, size: " + surveys.size());

		@SuppressWarnings("unchecked")
		PageableListView surveyView = new PageableListView("surveys", surveys,
				6) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@Override
			protected void populateItem(ListItem item) {
				ISurvey survey = (ISurvey) item.getModelObject();
				item.add(new Label("date", "Date: " + survey.getDate()));
				item.add(new Label("name", "Survey: " + survey.getName()));
				item.add(new Label("text", "Info: " + survey.getText()));
				String fragmentId = null;
				if (ss.loadAppSurveyByApplicantAndSurvey(
						((IPerson) applicant).getId(), survey.getId()).size() == 0) {
					fragmentId = "variant_add";
					item.add(new FragmentForAddSurvey(
							"placeholderForFragmente", fragmentId, this,
							applicant, survey));
					logger.info("Varinat add, size = 0");
				}
				if (ss.loadAppSurveyByApplicantAndSurvey(
						((IPerson) applicant).getId(), survey.getId()).size() > 0) {
					fragmentId = "variant_has";
					logger.info("Varinat has, size > 0");
					item.add(new FragmentAlreadySend("placeholderForFragmente",
							fragmentId, this));
				}

			}

		};

		add(new FeedbackPanel("feedback"));
		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

	}

	@SuppressWarnings("rawtypes")
	public AddSurveyForApplicant(Long applicantID) {
		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}
		if (getSession().getAttribute("personTyp").equals("a")) {
			setResponsePage(PersonTyp.class);
			return;
		}

		Long id = Long.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);

		final IApplicant applicant = (IApplicant) ps.find(applicantID);
		logger.info("Applicant found, id: " + applicantID);
		// final IApplicant applicant
		List<ISurvey> surveys = new ArrayList<ISurvey>();
		surveys = ss.loadAllServeyByEmployer(id);

		@SuppressWarnings("unchecked")
		PageableListView surveyView = new PageableListView("surveys", surveys,
				6) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@Override
			protected void populateItem(ListItem item) {
				ISurvey survey = (ISurvey) item.getModelObject();
				item.add(new Label("date", "Date: " + survey.getDate()));
				item.add(new Label("name", "Survey: " + survey.getName()));
				item.add(new Label("text", "Info: " + survey.getText()));
				String fragmentId = null;
				if (ss.loadAppSurveyByApplicantAndSurvey(
						((IPerson) applicant).getId(), survey.getId()).size() == 0) {
					fragmentId = "variant_add";
					item.add(new FragmentForAddSurvey(
							"placeholderForFragmente", fragmentId, this,
							applicant, survey));
				}
				if (ss.loadAppSurveyByApplicantAndSurvey(
						((IPerson) applicant).getId(), survey.getId()).size() > 0) {
					fragmentId = "variant_has";
					item.add(new FragmentAlreadySend("placeholderForFragmente",
							fragmentId, this));
				}

			}

		};

		add(new FeedbackPanel("feedback"));
		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

	}

}
