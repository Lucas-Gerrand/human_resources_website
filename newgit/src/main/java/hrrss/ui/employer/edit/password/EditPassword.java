package hrrss.ui.employer.edit.password;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
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

public class EditPassword extends BasePage {
    @SpringBean
    PersonService ps;

    private Person person;
    
    private static final long serialVersionUID = 1L;

    public EditPassword() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
    	if(getSession().getAttribute("personTyp").equals("a")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
		add(new Label("title", "Change Password"));
		
		Long id = Long.valueOf(getSession().getAttribute("id").toString());
		String backButton="<input type=\"button\" onclick=\"location.href='/employer/profile/"+id+"/';\" class='button_example' value=\"Back to Employer Profile\"/>";
    	
    	add(new Label("backToEmployer", backButton).setEscapeModelStrings(false));
    	
		Form form = new Form("editPasswordForm");
	

		final PasswordTextField oldpassword = new PasswordTextField("oldpassword", Model.of(""));
		oldpassword.setRequired(false);
		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		password.setRequired(false);
		final PasswordTextField repassword = new PasswordTextField("repassword", Model.of(""));
		repassword.setRequired(false);
		
		
		form.add(oldpassword);
		form.add(password);
		form.add(repassword);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	if(oldpassword.getValue().equals("")) {
		    		error("Old Password is required");
		    		error=true;
		    	}
		    	
		    	if(password.getValue().equals("")) {
		    		error("Password is required");
		    		error=true;
		    	}
		    	if(repassword.getValue().equals("")) {
		    		error("RePassword is required");
		    		error=true;
		    	} 
		    	
		    	if(!password.getValue().equals(repassword.getValue())) {
	    			error("Password and rePassword is not equal");
	    			error=true;
		    	}
		    	
				if (!error && password.getValue().length()<6) {
				    error("Password need at least 6 characters");
				    error = true;
				}
		    	
				Long id = Long.valueOf(getSession().getAttribute("id").toString());
	        	
	        	person = ps.find(id);
	        	String oldpwhash="";
	        	
	        	try {
					oldpwhash = PasswordUtil.getHashedPw(oldpassword.getValue());
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	
	        	if(!person.getPassword().equals(oldpwhash)) {
	        		error("Old password wrong");
	        		error=true;
	        	}
				
		    	if(!error) {
		    		
		        	
		        	String hashedPw;
					try {
						hashedPw = PasswordUtil.getHashedPw(password.getValue());
						person.setPassword(hashedPw);
						
						ps.update(person);
						
						info("Password changed");
						
						
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		    		
		    	}
		    	
		    	
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}