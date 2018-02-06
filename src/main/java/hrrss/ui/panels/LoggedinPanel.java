package hrrss.ui.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class LoggedinPanel extends Panel {

	public LoggedinPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		
		
		
		Label accText = new Label("logged_in");
		add(accText);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

