package hrrss.ui.employer.news;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.IEmployer;
import hrrss.model.IPerson;
import hrrss.model.ISurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.questionnaireform.QuestionReceived;
import hrrss.ui.questionnaireform.Questionnaire;
import hrrss.ui.questionnaireform.QuestionnaireViewForEmployer;

public class News extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	MessagingService msService;

	@SpringBean
	SurveyService ss;

	@SpringBean
	ApplicantAnswerService as;

	final Logger logger = LoggerFactory.getLogger(QuestionReceived.class);

	private Person p;

    public News() {
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    	}
    	
    	if(getSession().getAttribute("personTyp").equals("a")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);
		ModelFactory mf = new ModelFactory();

		add(new Label("name", "Hi, " + p.getFirstName() + " " + p.getLastName()));
		add(new Label("text", "You have some new messeges and new answers on questionnaire from applicants"));

		List<IApplicantToSurvey> applicantSurveys = new ArrayList<IApplicantToSurvey>();
		applicantSurveys = ss.loadNewAnswerByEmployer(true, id);
		logger.info("APPLICANT SURVEY SIZE: " + applicantSurveys.size());
		
		@SuppressWarnings({ "unchecked" })
		PageableListView surveyView = new PageableListView("surveys", applicantSurveys,
				3) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@Override
			protected void populateItem(ListItem item) {
				IApplicantToSurvey appSur = (IApplicantToSurvey) item.getModelObject();
				final IPerson per = ps.find(((IPerson) appSur.getApplicant()).getId());

				logger.info("PERSON :" + p.getId());

				String text = "";

				if (appSur.getNewq() == true) {
					text = "new";
				} else if (appSur.getNewq() == false) {
					text = "";
				}
				
				
				ISurvey survey = ss.find(appSur.getServey().getId());
				
				final IModel<String> textModel = Model.of(text);
				
				item.add(new Label("date", "["
						+ appSur.getDate()
						+ "] New answer on questionnaire "));
				
				Link linkAdd = new Link("add", item.getModel()) {

					/**
						 * 
						 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						IApplicantToSurvey selected = (IApplicantToSurvey) getModelObject();
						logger.info(selected.getId().toString());
						
						selected.setNewq(false);
						ss.updateAppSurvey(selected);

						ISurvey survey = ss.find(selected.getServey().getId());

						try {
							
							survey.setQuastions(ss
									.loadAllQuestionBySurvey(survey.getId()));

							setResponsePage(new QuestionnaireViewForEmployer(survey,per));

						} catch (Exception e) {
							logger.info(e.toString());
						}
					}
				};
				item.add(linkAdd);
				String linkText = survey.getName();
				Label l = new Label("linktext", Model.of(linkText));
				linkAdd.add(l);
				
				item.add(new Label("personname", " from '"
						+  per.getFirstName() + " " + per.getLastName() + " '. "));
				
			}

		};

		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

       
    }
}
