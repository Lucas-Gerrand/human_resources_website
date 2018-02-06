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

/**
 * class represent maker for attach question
 * 
 * @author Irina
 * 
 */
@SuppressWarnings("serial")
public class AttachQuestion extends BasePage {
	@SpringBean
	SurveyService ss;

	final Logger logger = LoggerFactory.getLogger(AttachQuestion.class);

	Quastion q = new Quastion();

	@SuppressWarnings("rawtypes")
	public AttachQuestion(final Survey s) {

		add(new Label("title", "Quastion"));
		Form form = new Form("attForm");
		final TextField<String> question = new TextField<String>("quastion",
				Model.of(""));
		form.add(question);

		form.add(new Button("submit") {

			@Override
			public void onSubmit() {
				boolean error = false;

				if (question.getValue().equals("")) {
					error("Question is required");
					error = true;
				} else if (question.getValue().length() > 70) {
					error("Question must have not mot then 70 characters");
					error = true;
				} else if (!error) {
					q.setQuastion(question.getValue());
					q.setTypeOfQuastion("attach");
					s.addQuestion(q);
					q.setSurvey(s);
					info("Question added");
					setResponsePage(new Questionnaire(s));
				}
			}

		});

		form.setMultiPart(true);

		form.add(new Button("cancel") {

			@Override
			public void onSubmit() {
				setResponsePage(new Questionnaire(s));
			}
		});

		form.add(new FeedbackPanel("feedback"));

		add(form);
	}
}