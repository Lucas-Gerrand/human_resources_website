package hrrss.ui.questionnaireform;

import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.SurveyService;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * Class FragmentForOpenQuestion includes a Fragments with Text area. Tha
 * applicant can read/save/update his/her answers
 * 
 * @author Irina
 * 
 */
public class FragmentForOpenQuestion extends Fragment {
	@SpringBean
	ApplicantAnswerService as;

	@SpringBean
	SurveyService ss;

	final Logger logger = LoggerFactory.getLogger(FragmentForOpenQuestion.class);
	
	/**
	 * Receive ids of the applicant and the question, show the answer in the
	 * text area, if the answer is already exist, create/update the answer
	 * 
	 * @param id
	 * @param markupId
	 * @param markupProvider
	 * @param qId
	 *            - the question ID
	 * @param appId
	 *            - the Applicant ID
	 */
	@SuppressWarnings("serial")
	public FragmentForOpenQuestion(String id, String markupId,
			MarkupContainer markupProvider, Long qId, Long appId, boolean surveyForEmployer) {
		super(id, markupId, markupProvider);
		final ModelFactory mf = new ModelFactory();
		final IApplicant applicant = mf.createApplicant();
		((IPerson) applicant).setId(appId);
		final IQuastion quastion = mf.createQuastion();
		quastion.setId(qId);

		/*
		 * a variable for the Model of the TextArea, is needed to put the answer
		 * in the TextArea
		 */
		String text = null;
		

		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfo = new MessageDialog(
				"infoDialog", "Info",
				"Answer saved!",
				DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
			}
		};
		
		add(warningDialogInfo);

		/*
		 * find the answers of an applicant, return list of the answers
		 */
		final List<IApplicantAnswer> appl = as.findByQuestion(qId, appId);

		logger.info("SIZE: " + appl.size());

		/*
		 * put text into the Model -> -> into the TextArea
		 */
		if (appl.size() != 0) {
			text = appl.get(0).getAnswer();
		} else if (appl.size() == 0) {
			text = "";
		}else if (surveyForEmployer == true) {
			text = "";
		}

		/*
		 * create the new Model
		 */
		IModel<String> textModel = Model.of(text);
		logger.info(text);
		final TextArea<String> q = new TextArea<String>("answer", textModel);
		
		add(q);
		

		/**
		 * save/update the answer
		 */
		add(new AjaxButton("submit") {

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (appl.size() == 0) {
					IApplicantAnswer applA = mf.createApplicantAnswer();
					applA.setApplicant(applicant);
					applA.setQuestion(quastion);
					applA.setAnswer(q.getValue());
					as.save(applA);
				} else if (appl.size() != 0) {
					appl.get(0).setAnswer(q.getValue());
					as.update(appl.get(0));
					
				}
				
				warningDialogInfo.open(target);
			}

		});

	}

}