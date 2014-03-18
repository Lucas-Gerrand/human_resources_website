package hrrss.ui.questionnaireform;

import hrrss.model.IEmployer;
import hrrss.model.ISurvey;
import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.Survey;
import hrrss.ui.questionnairemaker.Questionnaire;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.Fragment;

/**
 * Fragment with button "send a survey to employer", appears if the questionnaire not already send 
 * @author Irina
 *
 */
public class FragmentAddButtonForEmpolyer extends Fragment {

	public FragmentAddButtonForEmpolyer(String id, String markupId,
			MarkupContainer markupProvider, final IEmployer emp) {
		super(id, markupId, markupProvider);
	
		add(new Button("add_survey") {

			@Override
			public void onSubmit() {
				ModelFactory mf = new ModelFactory();
				ISurvey s = mf.createSurvey();
				s.setEmployer(emp);
				setResponsePage(new Questionnaire((Survey) s));
			}

		});

	}

}
