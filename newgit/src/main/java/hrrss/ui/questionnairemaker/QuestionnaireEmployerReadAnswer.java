package hrrss.ui.questionnairemaker;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
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
import hrrss.model.IPerson;
import hrrss.model.ISurvey;
import hrrss.model.impl.Person;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.questionnaireform.QuestionnaireViewForEmployer;

/**
 * shows received answers from applicants
 * 
 * @author Irina
 * 
 */
public class QuestionnaireEmployerReadAnswer extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	MessagingService msService;

	@SpringBean
	SurveyService ss;

	private Person p;

	final Logger logger = LoggerFactory.getLogger(SubAnswerPage.class);

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public QuestionnaireEmployerReadAnswer() {

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}

		add(new Label("title", "Your Inbox"));

		Form form = new Form("editForm");

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);
		logger.info("Person found: " + id);

		/*
		 * Questionarrie begin
		 */

		List<IApplicantToSurvey> applicantSurveys = new ArrayList<IApplicantToSurvey>();
		applicantSurveys = ss.loadAllAnswerByEmployer(id);
		logger.info("APPLICANT SURVEY SIZE: " + applicantSurveys.size());

		@SuppressWarnings({ "unchecked" })
		PageableListView surveyView = new PageableListView("surveys",
				applicantSurveys, 15) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@Override
			protected void populateItem(ListItem item) {
				IApplicantToSurvey appSur = (IApplicantToSurvey) item
						.getModelObject();
				final IPerson per = ps.find(((IPerson) appSur.getApplicant())
						.getId());

				logger.info("PERSON :" + p.getId());

				String text = "";

				if (appSur.getNewq() == true) {
					text = "new";
				} else if (appSur.getNewq() == false) {
					text = "";
				}

				ISurvey survey = ss.find(appSur.getServey().getId());

				final IModel<String> textModel = Model.of(text);
				item.add(new Label("newq", textModel));
				item.add(new Label("date", appSur.getDate()));
				String personProfileLink = "<a href=\"/applicant/profile/"
						+ per.getId() + "\">" + per.getFirstName() + " "
						+ per.getLastName() + "</a>";
				item.add(new Label("from", personProfileLink)
						.setEscapeModelStrings(false));
				item.add(new Label("text", survey.getText()));
				item.add(new Label("comment", appSur.getComment()));
				Link linkadd = new Link("add", item.getModel()) {

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

							setResponsePage(new QuestionnaireViewForEmployer(
									survey, per));

						} catch (Exception e) {
							StackTraceElement[] stack = e.getStackTrace();
							String exception = "";
							for (StackTraceElement s : stack) {
								exception = exception + s.toString() + "\n\t\t";
							}
							logger.info(exception);
						}
					}
				};

				item.add(linkadd);
				String linkText = survey.getName();

				Label l = new Label("linktext", Model.of(linkText));
				linkadd.add(l);

				item.add(new Link("delete", item.getModel()) {

					/**
						 * 
						 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						IApplicantToSurvey selected = (IApplicantToSurvey) getModelObject();
						logger.info(selected.getId().toString());

						ss.deleteApplicantToSurvey(selected);
						setResponsePage(QuestionnaireEmployerReadAnswer.class);
					}
				});

			}

		};

		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

		/*
		 * Questionarrie end
		 */
		form.add(new FeedbackPanel("feedback"));

		add(form);

	}

}
