package hrrss.ui.register;

import hrrss.model.impl.Applicant;
import hrrss.model.impl.CV;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.home.Home;
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

public class Register extends BasePage {
	@SpringBean
	PersonService ps;

	@SpringBean
	private CVService CVService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Register() {

		if (getSession().getAttribute("isLogin").equals("true")) {
			setResponsePage(Home.class);
		}
		add(new Label("title", "Register"));

		Form form = new Form("registerForm");

		final RadioGroup<String> personTyp = new RadioGroup<String>("personTyp", Model.of(""));
		final Radio<String> applicant = new Radio("applicant", Model.of("applicant"));
		final Radio<String> employer = new Radio("employer", Model.of("employer"));
		personTyp.add(applicant);
		personTyp.add(employer);

		final TextField<String> firstname = new TextField<String>("firstname", Model.of(""));
		final TextField<String> lastname = new TextField<String>("lastname", Model.of(""));
		final TextField<String> email = new TextField<String>("email", Model.of(""));
		final TextField<String> reemail = new TextField<String>("reemail", Model.of(""));
		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		password.setRequired(false);


		form.add(personTyp);
		form.add(firstname);
		form.add(lastname);
		form.add(email);
		form.add(reemail);
		form.add(password);

		form.add(new Button("submit") {
			String hashedPw = "";

			@Override
			public void onSubmit() {
				boolean error = false;

				if (firstname.getValue().equals("")) {
					error("Firstname required");
					error = true;
				}
				if(!error) {
					if(firstname.getValue().length() >= 100) {
						error("Firstname not longer than 100 chars");
						error = true;
					}
				}
				
				if (lastname.getValue().equals("")) {
					error("Lastname required");
					error = true;
				}
				
				if(!error) {
					if(lastname.getValue().length() >= 100) {
						error("Lastname not longer than 100 chars");
						error = true;
					}
				}
				if (email.getValue().equals("")) {
					error("Email required");
					error = true;
				}
				
				if(!error) {
					if(email.getValue().length() >= 100) {
						error("Email not longer than 100 chars");
						error = true;
					}
				}
				if (reemail.getValue().equals("")) {
					error("Reenter Email required");
					error = true;
				} else {

					if (!email.getValue().equals(reemail.getValue())) {
						error("Email and Reenter Email not equal");
						error = true;
					} else {
						Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
						Matcher m = p.matcher(email.getValue());
						boolean matchFound = m.matches();
						if (!matchFound) {
							error("No Valid Email");
							error = true;
						}
					}

				}
				if (password.getValue().equals("")) {
					error("Password required");
					error = true;
				} else {
					if (password.getValue().length() < 6 && password.getValue().length() >= 30) {
						error("Password must have at least 6 and maximum 30 characters");
						error = true;
					}
				}

				try {
					hashedPw = PasswordUtil.getHashedPw(password.getValue());
				} catch (NoSuchAlgorithmException e1) {
					error = true;
				}

				Person newPerson = (Person) ps.loadPersonByEmail(email
						.getValue());
				if (newPerson != null) {
					error = true;
					error("Email exists already");
				}
				
				if(personTyp.getValue().equals("")) {
					error("Choose Applicant or Employer");
					error = true;
				}
			
				
				if (!error) {
					String activation = ActivationUtil.getActivation();
					if(personTyp.getValue().equals(applicant.getValue())) {
						Applicant a = new Applicant();
						a.setFirstName(firstname.getValue());
						a.setLastName(lastname.getValue());
						a.setEmail(email.getValue());
						a.setPassword(hashedPw);
						a.setActivation(activation);
						
						String msg="Welcome "+ a.getFirstName()+"!\n\nYour activation key is: "+activation+"\nGo to localhost:8080/activation/ and enter your key\n\nYour Click Match Hire Team";
						
						try {
							//SendMail.sendEmail("Click Hire Match - Activation Key", msg, a.getEmail());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ps.save(a);
						CV mycv = new CV();
						
						mycv.setApplicant(a);
						mycv.setNationality("-");
						mycv.setPersonalEmail("-");
						mycv.setPersonalWebsite("-");
						mycv.setEducations(null);
						mycv.setExperiences(null);
						mycv.setSkills(null);
						
						
						CVService.saveCV(mycv);
						
						info("Applicant successfully created!");

					} else {
						Employer e = new Employer();
						e.setFirstName(firstname.getValue());
						e.setLastName(lastname.getValue());
						e.setEmail(email.getValue());
						e.setPassword(hashedPw);
						e.setActivation(activation);
						
						String msg="Welcome "+ e.getFirstName()+"!\n\nYour activation key is: "+activation+"\nGo to localhost:8080/activation/ and enter your key\n\nYour Click Match Hire Team";
						
						try {
							//SendMail.sendEmail("Click Hire Match - Activation Key", msg, e.getEmail());
						} catch (Exception ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					
						ps.save(e);
						info("Employer successfully created!");
					}
					
					System.out.println(activation);
					
					firstname.setDefaultModel(Model.of(""));
					lastname.setDefaultModel(Model.of(""));
					email.setDefaultModel(Model.of(""));
					reemail.setDefaultModel(Model.of(""));
					password.setDefaultModel(Model.of(""));

				}

			}

		});

		form.add(new FeedbackPanel("feedback"));

		add(form);

	}

}