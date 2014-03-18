package hrrss.ui.contact;

import hrrss.ui.BasePage;
import hrrss.ui.applicant.Account;

import org.apache.wicket.markup.html.basic.Label;

public class HelpContact extends BasePage {
	public HelpContact() {
		add(new Label("title", "Contact and Help"));
		add(new Label("content", "Contact and Help page"));
		
	}
}
