package hrrss.ui.questionnairemaker;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hrrss.model.impl.Quastion;
import hrrss.model.impl.Survey;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

/**
 * maker open question
 * @author Irina
 *
 */
public class OpenQuestion extends BasePage {
	@SpringBean
	SurveyService ss;

	final Logger logger = LoggerFactory.getLogger(OpenQuestion.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Quastion q = new Quastion();

	@SuppressWarnings({ "rawtypes", "serial" })
	public OpenQuestion(final Survey s) {

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}
		if (getSession().getAttribute("personTyp").equals("a")) {
			setResponsePage(PersonTyp.class);
			return;
		}

		add(new Label("title", "Open Quastion"));
		Form form = new Form("newQForm");
		final TextField<String> question = new TextField<String>("quastion",
				Model.of(""));
		form.add(question);

		form.add(new Button("submit") {

			@Override
			public void onSubmit() {
				boolean error = false;

				if (question.getValue().equals("")) {
					error("Text is required");
					error = true;
				}

				else if (question.getValue().length() > 70) {
					error("Question must have not mot then 70 characters");
					error = true;
					logger.error("False length");
				}

				else if (!error) {
					q.setQuastion(question.getValue());
					q.setTypeOfQuastion("open");
					s.addQuestion(q);
					q.setSurvey(s);
					logger.info("Question added");
					setResponsePage(new Questionnaire(s));
				}

			}

		});

		form.add(new Button("cancel") {

			@Override
			public void onSubmit() {
				logger.info("cancel");
				setResponsePage(new Questionnaire(s));
			}
		});

		form.add(new FeedbackPanel("feedback"));

		add(form);
	}
}
