package hrrss.ui.home;

import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.Account;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class Home extends BasePage {
	public Home() {
	
		add(new Label("label1", "This is in the subclass Page1"));
	}
}
