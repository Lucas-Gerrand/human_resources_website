package hrrss.ui.applicant.edit.email;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.home.Home;
import hrrss.ui.logout.Logout;
import hrrss.ui.util.ActivationUtil;
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

public class EditEmail extends BasePage {
    @SpringBean
    PersonService ps;

    private Person person;
   
    private static final long serialVersionUID = 1L;

    public EditEmail() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
		add(new Label("title", "Change Email"));
	
		Form form = new Form("editMailForm");
	
		Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	person = ps.find(id);

		final TextField<String> email = new TextField<String>("email",Model.of(person.getEmail()));	
		final TextField<String> reemail = new TextField<String>("reemail",Model.of(person.getEmail()));	
		
		form.add(email);
		form.add(reemail);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	if(email.getValue().equals("")) {
		    		error("Email is required");
		    		error=true;
		    	}
		    	if(reemail.getValue().equals("")) {
		    		error("ReEmail is required");
		    		error=true;
		    	} 
		    	
		    	if(!email.getValue().equals(reemail.getValue())) {
	    			error("Email and Reemail is not equal");
	    			error=true;
		    	}
		    	Person a=(Person) ps.loadPersonByEmail(email.getValue());
		    	if(a != null) {
		    		error("Email already exists");
	    			error=true;
		    	}
		    	
		    	Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = p.matcher(email.getValue());
				boolean matchFound = m.matches();
				if (!error && !matchFound) {
				    error("No Valid Email");
				    error = true;
				}
		    	
		    	
		    	if(!error) {
		    		String activation = ActivationUtil.getActivation();
		    		
		    		person.setEmail(email.getValue());
		    		person.setActivation(activation);
		    		
		    		ps.update(person);
		    		String msg="Welcome "+ person.getFirstName()+"!\n\nYour activation key is: "+activation+"\nGo to localhost:8080/activation/ and enter your key\n\nYour Click Match Hire Team";
					
					try {
						SendMail.sendEmail("Click Match Hire - Activation Key", msg, person.getEmail());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    		info("Email changed");
		    		setResponsePage(Logout.class);
		    		
		    	}
		    	
		    	
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}