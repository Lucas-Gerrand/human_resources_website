package hrrss.ui.questionnaireform;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;

/**
 * Fragment with label "Already send", appears if the questionnaire already send 
 * @author Irina
 *
 */
@SuppressWarnings("serial")
public class FragmentAlreadySend extends Fragment {

	public FragmentAlreadySend(String id, String markupId,
			MarkupContainer markupProvider) {
		super(id, markupId, markupProvider);
		add(new Label("add", "Questionnaire was successfully sent."));
	}

}
