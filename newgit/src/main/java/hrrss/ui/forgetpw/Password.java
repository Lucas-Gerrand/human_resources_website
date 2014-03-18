package hrrss.ui.forgetpw;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.employer.search.Search;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Password extends BasePage {
    @SpringBean
    PersonService ps;

    final Logger logger = LoggerFactory.getLogger(Password.class);
    private static final long serialVersionUID = 1L;

    public Password() {
    	
    	if(getSession().getAttribute("isLogin").equals("true")) {
    		setResponsePage(Home.class);
    	}
		add(new Label("title", "Forget your Password?"));
	
		Form form = new Form("pwForm");
	

		final TextField<String> email = new TextField<String>("email",Model.of(""));	
		
		form.add(email);
		
		form.add(new Button("submit") {
		    String hashedPw = "";
	
		    @Override
		    public void onSubmit() {
		    	Person newPerson=null;
				boolean error = false;
		
				if (email.getValue().equals("")) {
				    error("Email required");
				    error = true;
				} else {
					newPerson = (Person) ps.loadPersonByEmail(email.getValue());
					if (newPerson == null) {
					    error = true;
					    error("Email does not exist");
					}
					
				}
				
				if(!error) {
					try {
						String newPassword = PasswordUtil.generatePW();
						hashedPw = PasswordUtil.getHashedPw(newPassword);
						newPerson.setPassword(hashedPw);
					
						String msg="Hi "+ newPerson.getFirstName()+"\n\nYour new password: "+newPassword+"\n\nYour Click Hire Match Team";
						
						ps.update(newPerson);
						logger.info("NEW PASSWORD "+newPassword);
						SendMail.sendEmail("Click Hire Match - Forget Password", msg, newPerson.getEmail());
						info("New password is sent to you");
						
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (Exception e) {
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