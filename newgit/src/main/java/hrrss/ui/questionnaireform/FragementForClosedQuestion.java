package hrrss.ui.questionnaireform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.ISubAnswer;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.SurveyService;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * Class FragmentForClosedQuestion includes a Fragments with a Radio Group.
 * The namber of the answer depends on a question 
 * The applicant can read/save/update his/her answers
 * 
 * @author Irina
 * 
 */
public class FragementForClosedQuestion extends Fragment {
	@SpringBean
	SurveyService ss;
	
	@SpringBean
	ApplicantAnswerService as;
	
	final Logger logger = LoggerFactory.getLogger(FragementForClosedQuestion.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8070281411760655051L;
	/**
	 * Receive ids of the applicant and the question, show the answer in the
	 * text area, if the answer is already exist, create/update the answer
	 * 
	 * @param id
	 * @param markupId
	 * @param markupProvider
	 * @param qId - the Question Id
	 * @param appId - the Applicant Id
	 */
	@SuppressWarnings({ "rawtypes" })
	public FragementForClosedQuestion(String id, String markupId,
			MarkupContainer markupProvider, Long qId, Long appId, boolean emp) {
		super(id, markupId, markupProvider);
		
		final ModelFactory mf = new ModelFactory();
		final IApplicant applicant = mf.createApplicant();
		((IPerson) applicant).setId(appId);
		final IQuastion quastion = mf.createQuastion();
		quastion.setId(qId);
		
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

		/*
		 * find the subanswers for the question
		 */
		List<ISubAnswer> subanswers = new ArrayList<ISubAnswer>(); 
		
		List<Long> listTest = new ArrayList<Long>();
		
		if (appl.size() != 0) {
			String a = appl.get(0).getAnswer();
			try {
				String[] split = a.split(",");

				for (int i = 0; i < split.length; i++) {
					listTest.add(Long.valueOf(split[i]));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (appl.size() == 0 || emp == true){
			listTest = new ArrayList<Long>();
		}

		subanswers = ss.loadAllSubAnswerByQuestion(qId);
		
		
		final IModel<Collection<Long>> selectedModel = new CollectionModel<Long>(listTest);
		
		final CheckGroup<Long> answers = new CheckGroup<Long>("answers", selectedModel);
				
	
		final ListView subanswerView =new ListView<ISubAnswer>("subanswers", subanswers){
			/**
			 * 
			 */
			private static final long serialVersionUID = -4660584614729365768L;

			@Override
			protected void populateItem(ListItem<ISubAnswer> item) {
				Check<Long> cb = new Check<Long>("choice", Model.of(item.getModelObject().getId()));
				cb.setDefaultModelObject(item.getModelObject().getId());
				item.add(cb);
				item.add(new Label("answer", item.getModelObject().getSubAnswer()));
			}
		};
		
		subanswerView.setReuseItems(true);
		answers.add(subanswerView);
		add(answers);
		
		add(new AjaxButton("submit") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 739204106125301262L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					
				logger.info("SELECTED: " + selectedModel.getObject());
				
				if (appl.size() == 0) {
					IApplicantAnswer applA = mf.createApplicantAnswer();
					applA.setApplicant(applicant);
					applA.setQuestion(quastion);
					String textSave =selectedModel.getObject().toString();
					textSave = textSave.replaceAll("\\[", "");
					textSave = textSave.replaceAll("\\]", "");
					textSave = textSave.replaceAll(" ", "");
					logger.info("TEXT: " + textSave);
					applA.setAnswer(textSave);
					as.save(applA);
					logger.info("Applicants answer saved");
						
				} else if (appl.size() != 0) {
					appl.get(0).setAnswer(selectedModel.getObject().toString());
					String textSave =selectedModel.getObject().toString();
					textSave = textSave.replaceAll("\\[", "");
					textSave = textSave.replaceAll("\\]", "");
					textSave = textSave.replaceAll(" ", "");
					logger.info("TEXT: " + textSave);
					appl.get(0).setAnswer(textSave);
					as.update(appl.get(0));
					logger.info("Applicants answer updated");
				}

				
				} catch (Exception e){
		    		StackTraceElement[] stack = e.getStackTrace();
		    	    String exception = "";
		    	    for (StackTraceElement s : stack) {
		    	        exception = exception + s.toString() + "\n\t\t";
		    	  }
		    	    logger.info(exception);
				}
				
				warningDialogInfo.open(target);
			}
			
		});

		
	}

}