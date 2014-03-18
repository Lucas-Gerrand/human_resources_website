package hrrss.ui.questionnairemaker;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hrrss.model.impl.Quastion;
import hrrss.model.impl.SubAnswer;
import hrrss.model.impl.Survey;
import hrrss.ui.BasePage;

/**
 * class for the Maker of closed questions
 * 
 * @author Irina
 * 
 */
public class SubAnswerPage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SubAnswer subA = new SubAnswer();
	final Logger logger = LoggerFactory.getLogger(SubAnswerPage.class);

	@SuppressWarnings({ "rawtypes", "serial" })
	public SubAnswerPage(final Quastion q, final Survey s) {
		add(new Label("title", "Add New Answer"));
		Form form = new Form("newAForm");
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
					logger.error("Value = 0");
				} else if (question.getValue().length() > 50) {
					error("Answer must have not more then 50 characters");
					error = true;
					logger.error("Lenqth is not correct");
				} else if (!error) {
					subA.setSubAnswer(question.getValue());
					subA.setQuestion(q);
					q.addSubA(subA);
					logger.info("Answer added");
					setResponsePage(new ClosedQuestion(s, q));
				}

			}

		});

		form.add(new Button("cancel") {

			@Override
			public void onSubmit() {
				logger.info("Cancel question");
				setResponsePage(new ClosedQuestion(s, q));
			}
		});

		form.add(new FeedbackPanel("feedback"));
		add(form);
	}

}
