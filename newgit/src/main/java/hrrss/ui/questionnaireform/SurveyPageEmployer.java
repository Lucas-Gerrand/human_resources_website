package hrrss.ui.questionnaireform;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hrrss.model.IEmployer;
import hrrss.model.ISurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.model.impl.Survey;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.questionnairemaker.Questionnaire;

/**
 * list of saved surveys
 * @author Irina
 *
 */
public class SurveyPageEmployer extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	SurveyService ss;

	final Logger logger = LoggerFactory.getLogger(SurveyPageEmployer.class);

	private Person p;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SurveyPageEmployer() {

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

		List<ISurvey> surveys = new ArrayList<ISurvey>();
		surveys = ss.loadAllServeyByEmployer(id);

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
				item.add(new Link("add", item.getModel()) {

					/**
						 * 
						 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						ISurvey selected = (ISurvey) getModelObject();
						logger.info(selected.getId().toString());

						try {
							selected.setQuastions(ss
									.loadAllQuestionBySurvey((Long) selected
											.getId()));

							setResponsePage(new SurveyShowDetails(selected));

						} catch (Exception e) {
							StackTraceElement[] stack = e.getStackTrace();
							String exception = "";
							for (StackTraceElement s : stack) {
								exception = exception + s.toString() + "\n\t\t";
							}
							logger.info(exception);
						}
					}
				});
				item.add(new Link("delete",item.getModel()) {

					@Override
					public void onClick() {
						ISurvey selected = (ISurvey) getModelObject();
						logger.info(selected.getId().toString());

						try {
							selected.setDelete(true);
							selected.setName(" ");
							selected.setText("SURVEY EXPIRED!");
							ss.update(selected);
							
							setResponsePage(SurveyPageEmployer.class);

						} catch (Exception e) {
							StackTraceElement[] stack = e.getStackTrace();
							String exception = "";
							for (StackTraceElement s : stack) {
								exception = exception + s.toString() + "\n\t\t";
							}
							logger.info(exception);
						}
						
					}
					
				});

			}

		};
		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

		add(new Link("addsurvey") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5123657881605187127L;

			@Override
			public void onClick() {
				ModelFactory mf = new ModelFactory();
				ISurvey s = mf.createSurvey();
				s.setEmployer((IEmployer) p);
				setResponsePage(new Questionnaire((Survey) s));
			}

		});

	}

}