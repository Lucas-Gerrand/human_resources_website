package hrrss.ui.questionnairemaker;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hrrss.model.ISubAnswer;
import hrrss.model.impl.Quastion;
import hrrss.model.impl.Survey;
import hrrss.ui.BasePage;

/**
 * maker for closed question
 * 
 * @author Irina
 * 
 */
public class ClosedQuestion extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(Questionnaire.class);

	@SuppressWarnings({ "rawtypes", "serial" })
	public ClosedQuestion(final Survey s, final Quastion q) {
		add(new Label("title", "Closed Quastion"));
		Form form = new Form("newQForm");

		String text = "";

		if (q.getQuastion() != null) {
			text = q.getQuastion();
		}

		IModel<String> textModel = Model.of(text);

		final TextField<String> question = new TextField<String>("quastion",
				textModel);
		form.add(question);

		form.add(new Button("addAnswer") {

			@Override
			public void onSubmit() {
				q.setQuastion(question.getValue());
				setResponsePage(new SubAnswerPage(q, s));
				logger.info("answer added");
			}

		});

		final List<ISubAnswer> list = q.getSubAnswers();
		;

		final int num = list.size();

		form.add(new Button("submit") {

			@Override
			public void onSubmit() {
				boolean error = false;

				if (question.getValue().equals("")) {
					logger.error("Text is required");
					error = true;
				} else if (num < 2) {
					error("Add Answers to The Question (min. 2 answers)");
					error = true;
				} else if (question.getValue().length() > 100) {
					logger.error("Add Answers up to 100 charachters");
					error = true;
				} else if (!error) {

					q.setQuastion(question.getValue());
					q.setTypeOfQuastion("closed");
					s.addQuestion(q);
					q.setSurvey(s);
					logger.info("Question added");
					setResponsePage(new Questionnaire(s));
				}

			}

		});

		@SuppressWarnings("unchecked")
		PageableListView subAnswerView = new PageableListView("subanswers",
				list, 10) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(ListItem item) {
				ISubAnswer subAnswer = (ISubAnswer) item.getModelObject();
				item.add(new Label("showAns", subAnswer.getSubAnswer()));
				item.add(new Link("del", item.getModel()) {

					/**
						 * 
						 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						ISubAnswer selected = (ISubAnswer) getModelObject();
						System.out.println(selected.getId());
						q.deleteSubA(selected);
						setResponsePage(new ClosedQuestion(s, q));
						logger.info("item successfully deleted!");
					}
				});
			}

		};

		form.add(new Button("cancel") {

			@Override
			public void onSubmit() {
				logger.info("cancel");
				setResponsePage(new Questionnaire(s));
			}
		});

		form.add(new FeedbackPanel("feedback"));
		add(subAnswerView);
		add(new PagingNavigator("navigator", subAnswerView));

		add(form);
	}
}
