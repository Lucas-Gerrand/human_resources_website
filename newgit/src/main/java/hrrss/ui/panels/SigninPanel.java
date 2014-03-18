package hrrss.ui.panels;

import hrrss.model.impl.Applicant;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.ui.applicant.Account;
import hrrss.ui.applicant.news.News;
import hrrss.ui.error.Activation;
import hrrss.ui.error.LoginFail;
import hrrss.ui.home.Home;
import hrrss.ui.util.PasswordUtil;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class SigninPanel extends Panel {
    @SpringBean
    private PersonService ps;

    public SigninPanel(String id) {
	super(id);

	Form form = new Form("loginForm");

	final TextField<String> email = new TextField<String>("email",
		Model.of(""));
	final PasswordTextField password = new PasswordTextField("password",
		Model.of(""));
	password.setRequired(false);

	form.add(email);
	form.add(password);

	form.add(new Button("loginSubmit") {
	    @Override
	    public void onSubmit() {
		boolean error = false;

		Person loginPerson = (Person) ps.loadPersonByEmail(email
			.getValue());

		if (email.getValue().equals("")
			|| password.getValue().equals("")
			|| loginPerson == null) {
		    error = true;
		}
		if(error) {
			 setResponsePage(LoginFail.class);
			 return;
		}
		if(!loginPerson.getActivation().equals("done")) {
			setResponsePage(Activation.class);
			return;
		}

		if (error) {
		    setResponsePage(LoginFail.class);
		} else {
			
		    try {
			// PersonService ps = new PersonService();
			String hashedPw = PasswordUtil.getHashedPw(password
				.getValue());

			if (ps.login(email.getValue(), hashedPw) != null) {
			    getSession().setAttribute("isLogin", "true");
			    Integer id = Integer.parseInt(loginPerson.getId().toString());
			    getSession().setAttribute("id", id);
			    getSession().setAttribute("firstname", loginPerson.getFirstName());
			    getSession().setAttribute("lastname", loginPerson.getLastName());
			    
			    if (loginPerson instanceof Applicant) {
			    	getSession().setAttribute("personTyp", "a");
			    	setResponsePage(News.class);
			    } else {
			    	getSession().setAttribute("personTyp", "e");
			    	setResponsePage(hrrss.ui.employer.news.News.class);
			    }
			   

			} else {
			    setResponsePage(LoginFail.class);
			}

		    } catch (Exception e) {
			error = true;
		    } finally {
			if (error) {
			    setResponsePage(LoginFail.class);
			}
		    }
		}
	    }
	});
	add(form);
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

}
