package hrrss.ui.questionnairemaker;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
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

import hrrss.model.IQuastion;
import hrrss.model.impl.Quastion;
import hrrss.model.impl.Survey;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.questionnaireform.SurveyPageEmployer;

/**
 * Questionnaire maker for the employer
 * 
 * @author Irina
 * 
 */
public class Questionnaire extends BasePage {
	@SpringBean
	SurveyService ss;
	@SpringBean
	private PersonService ps;

	final Logger logger = LoggerFactory.getLogger(Questionnaire.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public Questionnaire(final Survey s) {
		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}
		if (getSession().getAttribute("personTyp").equals("a")) {
			setResponsePage(PersonTyp.class);
			return;
		}

		add(new Label("title", "Questionnaire"));

		Form form = new Form("newForm");

		add(new Label("addName", "Add survey name"));

		String text = "";

		String textName = "";

		if (s.getText() != null) {
			text = s.getText();
			logger.info("Text: " + text);
		}

		if (s.getName() != null) {
			textName = s.getName();
			logger.info("Name: " + textName);
		}

		IModel<String> textModel = Model.of(text);

		IModel<String> textModelName = Model.of(textName);

		logger.info(text);

		final TextField<String> surveyName = new TextField<String>("survey",
				textModelName);

		final TextArea<String> t = new TextArea<String>("text_survey",
				textModel);

		form.add(surveyName);
		form.add(t);

		final RadioGroup<String> questionType = new RadioGroup<String>(
				"questionType", Model.of(""));
		final Radio<String> open = new Radio("open", Model.of("open"));
		final Radio<String> choice = new Radio("choice", Model.of("choice"));
		final Radio<String> attach = new Radio("attach", Model.of("attach"));
		final Radio<String> video = new Radio("video", Model.of("video"));
		questionType.add(open);
		questionType.add(choice);
		questionType.add(attach);
		questionType.add(video);

		form.add(questionType);

		form.add(new Button("submit") {

			@Override
			public void onSubmit() {
				s.setText(t.getValue());
				s.setName(surveyName.getValue());
				logger.info(questionType.getValue());

				if (questionType.getValue().equals(open.getValue())) {
					logger.info(questionType.getValue());
					setResponsePage(new OpenQuestion(s));
				}
				if (questionType.getValue().equals(choice.getValue())) {
					logger.info("Closed: " + questionType.getValue());
					Quastion q = new Quastion();
					setResponsePage(new ClosedQuestion(s, q));
				}
				if (questionType.getValue().equals(attach.getValue())) {
					logger.info("Attach: " + questionType.getValue());
					setResponsePage(new AttachQuestion(s));
				}
				if (questionType.getValue().equals(video.getValue())) {
					logger.info("Video: " + questionType.getValue());
					setResponsePage(new VideoQuestion(s));
				}
			}

		});

		final List<IQuastion> questions = s.getQuastions();
		;

		form.add(new Button("save") {

			@Override
			public void onSubmit() {
				boolean error = false;
				if (surveyName.getValue().equals("")) {
					error("Survey name is required");
					error = true;
				} else if (surveyName.getValue().length() > 50) {
					error("Name must have not more then 50 characters");
					error = true;
				} else if (s.getQuastions().size() == 0) {
					error("Add Questions to The Survey");
					error = true;
				} else if (t.getValue().equals("")) {
					error("Add Description to The Survey (max. 150 characters)");
					error = true;
				} else if (t.getValue().length() > 150) {
					error("Description must have not more then 150 characters");
					error = true;
				}

				else if (error == false) {
					s.setName(surveyName.getValue());
					s.setText(t.getValue());
					s.setDate(new Date());
					for (int i = 1; i < (questions.size() + 1); i++) {
						s.getQuastions().get(i - 1).setNumber(i);
					}

					ss.save(s);

					setResponsePage(SurveyPageEmployer.class);
				}
			}

		});
		form.add(new Button("deleteS") {

			@Override
			public void onSubmit() {
				setResponsePage(SurveyPageEmployer.class);
			}

		});
		add(form);

		PageableListView surveyView = new PageableListView("question",
				questions, 10) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@Override
			protected void populateItem(ListItem item) {
				IQuastion question = (IQuastion) item.getModelObject();
				item.add(new Label("name", question.getTypeOfQuastion()));
				item.add(new Label("text", question.getQuastion()));
				item.add(new Link("del", item.getModel()) {

					/**
						 * 
						 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						IQuastion selected = (IQuastion) getModelObject();
						logger.info(selected.getId().toString());
						s.deleteQuestion(selected);
						setResponsePage(new Questionnaire(s));
						info("item successfully deleted!");
					}
				});
			}

		};

		form.add(new FeedbackPanel("feedback"));
		add(surveyView);
		add(new PagingNavigator("navigator", surveyView));

	}

}
