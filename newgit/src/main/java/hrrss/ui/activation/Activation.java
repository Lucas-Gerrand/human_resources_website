package hrrss.ui.activation;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.home.Home;
import hrrss.ui.util.PasswordUtil;
import hrrss.ui.util.SendMail;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class Activation extends BasePage {
    @SpringBean
    PersonService ps;

 
    private static final long serialVersionUID = 1L;

    public Activation() {
    	
    	if(getSession().getAttribute("isLogin").equals("true")) {
    		setResponsePage(Home.class);
    	}
		add(new Label("title", "Activate your account"));
	
		Form form = new Form("actForm");
	

		final TextField<String> key = new TextField<String>("key",Model.of(""));	
		
		form.add(key);
		
		form.add(new Button("submit") {
		    @Override
		    public void onSubmit() {
		    	Person newPerson=null;
				boolean error = false;
		
				if (key.getValue().equals("")) {
				    error("Activation Key required");
				    error = true;
				} else {
					newPerson = (Person) ps.loadPersonByActivation(key.getValue());
					if (newPerson == null) {
					    error = true;
					    error("Key does not exist");
					}
					
				}
				
				if(!error) {
					newPerson.setActivation("done");
					ps.update(newPerson);
					
					info("Activation done, you can now login");
				}
				
		
		
				
		
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}