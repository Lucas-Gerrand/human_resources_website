package hrrss.ui.about;

import hrrss.ui.BasePage;
import hrrss.ui.applicant.Account;

import org.apache.wicket.markup.html.basic.Label;

public class About extends BasePage {
	public About() {
		add(new Label("title", "About us"));
		add(new Label("content", "Welcome to Click Match Hire. The ultimate recruiting tool"));
		
	
	}
}