package hrrss.ui.questionnaireform;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantToSurvey;
import hrrss.model.ISurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.SurveyService;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fragment for sending a questionnaire to an applicant
 * @author Irina
 *
 */
@SuppressWarnings("serial")
public class FragmentForAddSurvey extends Fragment {
	@SpringBean
	SurveyService ss;
	
	final Logger logger = LoggerFactory.getLogger(FragmentForAddSurvey.class);
	
	@SuppressWarnings("rawtypes")
	public FragmentForAddSurvey(String id, String markupId,
			MarkupContainer markupProvider, final IApplicant applicant, final ISurvey selected) {
		super(id, markupId, markupProvider);
		
		final ModelFactory modelFactory = new ModelFactory();
		
		add(new Link("submit") {

			@Override
			public void onClick() {
				IApplicantToSurvey appToS1 = modelFactory
						.createApplicantToSurvey();
				appToS1.setApplicant(applicant);
				appToS1.setServeys(selected);
				appToS1.setNewqa(true);
				appToS1.setVisapp(true);
				ss.saveAppSurvey((IApplicantToSurvey) appToS1);
				logger.info("The questionnaire has been saved");
			}

		});

	}

}
