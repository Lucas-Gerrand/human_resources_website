package hrrss.ui.questionnaireform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

import hrrss.model.IApplicantToSurvey;
import hrrss.model.IEmployer;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.ISurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Person;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.MessagingService;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.SurveyService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;

/**
 * received surveys from the employer matching to an applicant
 * 
 * @author Irina
 * 
 */
public class QuestionReceived extends BasePage {
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

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes" })
	public QuestionReceived() {

		ModelFactory modelFactory = new ModelFactory();

		if (getSession().getAttribute("isLogin").equals("false")) {
			setResponsePage(Authentication.class);
			return;
		}

		add(new Label("title", "Your Questionnaries"));

		final Form form = new Form("editForm");

		// window

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		final Long id = Long
				.valueOf(getSession().getAttribute("id").toString());

		p = ps.find(id);

		/*
		 * Questionarrie begin
		 */

		List<IApplicantToSurvey> applicantSurveys = new ArrayList<IApplicantToSurvey>();
		applicantSurveys = ss.loadAllServeyByUser(id);
		logger.info("APPLICANT SURVEY SIZE: " + applicantSurveys.size());

		List<ISurvey> surveys = new ArrayList<ISurvey>();
		ISurvey survey = modelFactory.createSurvey();

		try {
			for (IApplicantToSurvey appSuv : applicantSurveys) {
				logger.info("APPLICANT SURVEY ID: "
						+ appSuv.getServey().getId());
				survey = ss.find(appSuv.getServey().getId());
				logger.info("SURVEY LOAD: " + survey.getName());
				surveys.add(survey);
			}
		} catch (Exception e) {
			StackTraceElement[] stack = e.getStackTrace();
			String exception = "";
			for (StackTraceElement s : stack) {
				exception = exception + s.toString() + "\n\t\t";
			}
			logger.info(exception);
		}

		List<Long> listSurveyModel = new ArrayList<Long>();
		final IModel<Collection<Long>> selectedModel = new CollectionModel<Long>(
				listSurveyModel);
		final CheckGroup<Long> surveysCheck = new CheckGroup<Long>(
				"survey_group", selectedModel);

		PageableListView surveyView = new PageableListView<ISurvey>("surveys",
				surveys, 15) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 53780096350876440L;

			@SuppressWarnings({ "unchecked" })
			@Override
			protected void populateItem(final ListItem<ISurvey> item) {
				ISurvey survey = (ISurvey) item.getModelObject();
				Check<Long> cb = new Check<Long>("choice", Model.of(item
						.getModelObject().getId()));
				cb.setDefaultModelObject(item.getModelObject().getId());
				item.add(cb);

				IPerson per = ps.find(((IPerson) survey.getEmployer()).getId());

				logger.info("PERSON :" + p.getId());
				logger.info("SURVEY :" + survey.getId());
				List<IApplicantToSurvey> a = ss
						.loadAppSurveyByApplicantAndSurvey(p.getId(),
								survey.getId());
				logger.info("APP SURVEY SIZE :" + a.size());

				String text = "";

				if (a.get(0).getNewqa() == true) {
					text = "new";
				} else if (a.get(0).getNewqa() == false) {
					text = "";
				}
				final IModel<String> textModel = Model.of(text);

				String perc = "";

				logger.info("SURVEY SIZE: "
						+ ss.loadAllQuestionBySurvey((Long) survey.getId())
								.size());

				List<IQuastion> q2 = ss.loadAllQuestionBySurvey((Long) survey
						.getId());
				int c = 0;

				for (IQuastion q : q2) {
					if (as.findByQuestion(q.getId(), id).size() == 1) {
						c++;
					}
				}
				logger.info("ANSWERS SIZE: " + c);
				int number = (((c * 100) / ss.loadAllQuestionBySurvey(
						(Long) survey.getId()).size()));
				perc = Integer.toString(number) + "%";
				logger.info(perc);
				final IModel<String> textModelPercentage = Model.of(perc);

				item.add(new Label("percent", textModelPercentage));
				item.add(new Label("newq", textModel));
				item.add(new Label("date", item.getModelObject().getDate()));
				item.add(new Label("company", ((IEmployer) per)
						.getCompanyname()));
				item.add(new Label("text", item.getModelObject().getText()));
				item.add(new Label("from", per.getLastName()));
				
				
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
				if (!item.getModelObject().isDelete()){
					linkText = item.getModelObject().getName();
				}
				
				Label l = new Label("linktext", Model.of(linkText));
				linkadd.add(l);

			}

		};

		surveyView.setReuseItems(true);
		surveysCheck.add(surveyView);
		form.add(surveysCheck);

		@SuppressWarnings("serial")
		final MessageDialog warningDialogError = new MessageDialog(
				"errorDialog", "Error", "No surveys are selected!",
				DialogButtons.OK, DialogIcon.ERROR) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
			}
		};
		add(warningDialogError);

		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfo = new MessageDialog("infoDialog",
				"Info", "Survey(s) deleted!", DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
				setResponsePage(QuestionReceived.class);
			}
		};
		add(warningDialogInfo);

		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfoSend = new MessageDialog(
				"infoSendDialog", "Info", "Survey(s) successfully sent!",
				DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
				setResponsePage(QuestionReceived.class);
			}
		};
		add(warningDialogInfoSend);

		final MessageDialog warningDialogDelete = new MessageDialog(
				"warningDialog", "Warning",
				"Are you sure you want to delete the selected surveys: ",
				DialogButtons.YES_NO, DialogIcon.WARN) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
				if (button != null && button.equals(LBL_YES)) {
					String textSave = selectedModel.getObject().toString();
					logger.info("MODEL: " + textSave);
					textSave = textSave.replaceAll("\\[", "");
					textSave = textSave.replaceAll("\\]", "");
					textSave = textSave.replaceAll(" ", "");
					logger.info("TEXT: " + textSave);
					List<Long> listForDelete = new ArrayList<Long>();
					List<IApplicantToSurvey> a = new ArrayList<IApplicantToSurvey>();

					try {
						String[] split = textSave.split(",");

						for (int i = 0; i < split.length; i++) {
							listForDelete.add(Long.valueOf(split[i]));

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					for (Long i : listForDelete) {
						a.add(ss.loadAppSurveyByApplicantAndSurvey(p.getId(), i)
								.get(0));
					}

					for (IApplicantToSurvey as : a) {
						ss.deleteApplicantToSurvey(as);
					}

					warningDialogInfo.open(target);

				}

			}
		};
		add(warningDialogDelete);

		final MessageDialog warningDialogSend = new MessageDialog(
				"warningDialogSend", "Warning",
				"Are you sure you want to send the selected surveys: ",
				DialogButtons.YES_NO, DialogIcon.WARN) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
				if (button != null && button.equals(LBL_YES)) {
					String textSave = selectedModel.getObject().toString();
					logger.info("MODEL: " + textSave);
					textSave = textSave.replaceAll("\\[", "");
					textSave = textSave.replaceAll("\\]", "");
					textSave = textSave.replaceAll(" ", "");
					logger.info("TEXT: " + textSave);
					List<Long> listForDelete = new ArrayList<Long>();
					List<IApplicantToSurvey> a = new ArrayList<IApplicantToSurvey>();

					try {
						String[] split = textSave.split(",");

						for (int i = 0; i < split.length; i++) {
							listForDelete.add(Long.valueOf(split[i]));
							System.out.println(Long.valueOf(split[i]));

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					for (Long i : listForDelete) {
						IApplicantToSurvey as = ss.loadAppSurveyByApplicantAndSurvey(p.getId(), i)
								.get(0);
						as.setVis(true);
						as.setNewq(true);
						as.setVisapp(false);
						as.setDate(new Date());
						a.add(as);
						
					}

					for (IApplicantToSurvey as : a) {
						ss.updateAppSurvey(as);
					}

					warningDialogInfoSend.open(target);
				}

			}
		};
		add(warningDialogSend);

		form.add(new AjaxButton("warningDelete", new Model<String>("Delete")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (selectedModel.getObject().toString().equals("[]")) {
					logger.info("MODEL_NO_SELECT: "
							+ selectedModel.getObject().toString());
					warningDialogError.open(target);

				} else if (!selectedModel.getObject().toString().equals("[]")) {
					warningDialogDelete.open(target);
				}

			}
		});

		form.add(new AjaxButton("warningSend", new Model<String>("Send")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (selectedModel.getObject().toString().equals("[]")) {
					logger.info("MODEL_NO_SELECT: "
							+ selectedModel.getObject().toString());
					warningDialogError.open(target);

				} else if (!selectedModel.getObject().toString().equals("[]")) {
					warningDialogSend.open(target);
				}
			}
		});

		form.add(new PagingNavigator("navigator", surveyView));

		add(form);

	}
}