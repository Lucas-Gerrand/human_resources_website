package hrrss.ui.employer.edit;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.impl.Address;
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

public class Edit extends BasePage {
    @SpringBean
    PersonService ps;

    
    private static final long serialVersionUID = 1L;

    public Edit() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("a")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
		add(new Label("title", "Edit personal informations"));
	
		Form form = new Form("editForm");
	
		Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
		
		String backButton="<input type=\"button\" onclick=\"location.href='/employer/profile/"+id+"/';\" class='button_example' value=\"Back to Employer Profile\"/>";
    	
    	add(new Label("backToEmployer", backButton).setEscapeModelStrings(false));
    	
    	
    	final Employer p = (Employer) ps.find(id);
    	
		final TextField<String> firstname = new TextField<String>("firstname",Model.of(p.getFirstName()));	
		final TextField<String> lastname = new TextField<String>("lastname",Model.of(p.getLastName()));	
		final TextField<String> company = new TextField<String>("company",Model.of(p.getCompanyname()));	
		final TextField<String> contact = new TextField<String>("contact",Model.of(p.getContact()));	
		final TextField<String> homepage = new TextField<String>("homepage",Model.of(p.getHomepage()));	
		
		String streetString="";
		String zipString="";
		String cityString="";
		if(p.getAddress() != null) {
			streetString = p.getAddress().getStreet();
			zipString = p.getAddress().getZipCode();
			cityString = p.getAddress().getCity();
		}
		final TextField<String> street = new TextField<String>("street",Model.of(streetString));	
		final TextField<String> zip = new TextField<String>("zip",Model.of(zipString));	
		final TextField<String> city = new TextField<String>("city",Model.of(cityString));	
		
		
		
		
		final TextField<String> email = new TextField<String>("email",Model.of(p.getEmail()));	
		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		password.setRequired(false);
		form.add(firstname);
		form.add(lastname);
		form.add(company);
		form.add(contact);
		form.add(street);
		form.add(zip);
		form.add(city);
		form.add(homepage);
		form.add(email);
		form.add(password);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	
		    	if(firstname.getValue().equals("")) {
		    		error("Firstname is required");
		    		error=true;
		    	}
		    	
		    	if(lastname.getValue().equals("")) {
		    		error("Lastname is required");
		    		error=true;
		    	}
		    	
		    	if(company.getValue().equals("")) {
		    		error("Company is required");
		    		error=true;
		    	}

		    	if(contact.getValue().equals("")) {
		    		error("Contact is required");
		    		error=true;
		    	}
		    	
		    	if(city.getValue().equals("")) {
		    		error("City is required");
		    		error=true;
		    	}
		    	
		    	if(street.getValue().equals("")) {
		    		error("Street is required");
		    		error=true;
		    	}
		    	
		    	if(lastname.getValue().equals("")) {
		    		error("Zip Code is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		p.setFirstName(firstname.getValue());
		    		p.setLastName(lastname.getValue());
		    		Address a = new Address();
		    		a.setCity(city.getValue());
		    		a.setStreet(street.getValue());
		    		a.setZipCode(zip.getValue());
		    		p.setAddress(a);
		    		p.setContact(contact.getValue());
		    		p.setCompanyname(company.getValue());
		    		p.setHomepage(homepage.getValue());
		    		ps.update(p);
		    		getSession().setAttribute("firstname", p.getFirstName());
				    getSession().setAttribute("lastname", p.getLastName());
				    
				    info("Changes Saved");
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}