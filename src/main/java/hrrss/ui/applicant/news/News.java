package hrrss.ui.applicant.news;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
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

@SuppressWarnings("serial")
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

	@SuppressWarnings("rawtypes")
	public News() {
		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
		}

		if (getSession().getAttribute("personTyp").equals("e")) {
			setResponsePage(PersonTyp.class);
			return;
		}

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);
		ModelFactory mf = new ModelFactory();

		add(new Label("name", "Hi, " + p.getFirstName() + " " + p.getLastName()));
		add(new Label("text", "You have some new messeges and quastionneries"));

		List<IApplicantToSurvey> applicantSurveys = new ArrayList<IApplicantToSurvey>();
		applicantSurveys = ss.loadNewSurveysByApplicant(id);

		logger.info("APPLICANT SURVEY SIZE: " + applicantSurveys.size());

		List<ISurvey> surveys = new ArrayList<ISurvey>();
		ISurvey survey = mf.createSurvey();

		try {
			for (IApplicantToSurvey appSuv : applicantSurveys) {
				logger.info("APPLICANT SURVEY ID: "
						+ appSuv.getServey().getId());
				survey = ss.find(appSuv.getServey().getId());
				logger.info("SURVEY LOAD: " + survey.getName());
				surveys.add(survey);
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}

		PageableListView surveyView = new PageableListView<ISurvey>("surveys",
				surveys, 5) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@SuppressWarnings({ "unchecked" })
			@Override
			protected void populateItem(final ListItem<ISurvey> item) {
				ISurvey survey = (ISurvey) item.getModelObject();
				IPerson per = ps.find(((IPerson) survey.getEmployer()).getId());
				item.add(new Label("name", "["
						+ item.getModelObject().getDate()
						+ "] New questionnaire from ' "
						+ ((IEmployer) per).getCompanyname() + " ': "
						+ item.getModelObject().getName()));

				Link linkadd = new Link("add", item.getModel()) {

					/**
						 * 
						 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						ISurvey selected = (ISurvey) getModelObject();
						logger.info(selected.getId().toString());

						List<IApplicantToSurvey> a = ss
								.loadAppSurveyByApplicantAndSurvey(p.getId(),
										selected.getId());
						a.get(0).setNewqa(false);
						ss.updateAppSurvey(a.get(0));

						try {
							selected.setQuastions(ss
									.loadAllQuestionBySurvey((Long) selected
											.getId()));

							setResponsePage(new Questionnaire(selected));

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
				String linkText = "";
				if (!item.getModelObject().isDelete()) {
					linkText = item.getModelObject().getName();
				}

				Label l = new Label("linktext", Model.of(linkText));
				linkadd.add(l);

			}

		};

		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

	}
}
