package hrrss.ui.applicant.edit;

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
import hrrss.ui.util.CheckUtils;
import hrrss.ui.util.PasswordUtil;
import hrrss.ui.util.SendMail;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
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

    private Applicant p;
    
    private static final long serialVersionUID = 1L;

    public Edit() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
		add(new Label("title", "Edit personal informations"));
		
		
		Form form = new Form("editForm");
	
		Long id = Long.valueOf(getSession().getAttribute("id").toString());
		String backButton="<input type=\"button\" onclick=\"location.href='/applicant/cv/"+id+"/';\" class='button_example' value=\"Back to CV\"/>";
		add(new Label("back", backButton).setEscapeModelStrings(false));
    	
    	p = (Applicant) ps.find(id);
    	String stat="yes";
    	if(p.getStatus() != null) {
    		stat=p.getStatus();
    	}
    	final Select searching = new Select("searching", Model.of(stat));
    	final SelectOption<String> yes = new SelectOption<String>("yes", Model.of("yes"));
    	final SelectOption<String> no = new SelectOption<String>("no", Model.of("no"));
    	
    	searching.add(yes);
    	searching.add(no);
    	
		final TextField<String> firstname = new TextField<String>("firstname",Model.of(p.getFirstName()));	
		final TextField<String> lastname = new TextField<String>("lastname",Model.of(p.getLastName()));	
		final TextField<String> birthday = new TextField<String>("birthday",Model.of(p.getBirthday()));
		
		String streetString="";
		String cityString="";
		String zipString="";
		if(p.getAddress() != null) {
			streetString = p.getAddress().getStreet();
			cityString = p.getAddress().getCity();
			zipString = p.getAddress().getZipCode();
		}
		
		final TextField<String> street = new TextField<String>("street",Model.of(streetString));	
		final TextField<String> zip = new TextField<String>("zip",Model.of(zipString));	
		final TextField<String> city = new TextField<String>("city",Model.of(cityString));	
		
		final TextField<String> email = new TextField<String>("email",Model.of(p.getEmail()));	
		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		password.setRequired(false);
		
		form.add(searching);
		form.add(firstname);
		form.add(lastname);
		form.add(birthday);
		form.add(street);
		form.add(zip);
		form.add(city);
		form.add(email);
		form.add(password);
		
		form.add(new Button("submit") {
			String status="";
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	
		    	if(searching.getValue().equals("")) {
		    		error("Searching is required");
		    		
		    		error=true;
		    	} else {
		    		if(searching.getValue().equals(yes.getValue())) {
		    			status="yes";
			    	} else {
			    		status="no";
			    	}
		    	
		    	}
		    	
		    	if(firstname.getValue().equals("")) {
		    		error("Firstname is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
					if(firstname.getValue().length() >= 100) {
						error("Firstname not longer than 100 chars");
						error = true;
					}
				}
		    	
		    	if(lastname.getValue().equals("")) {
		    		error("Lastname is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
					if(lastname.getValue().length() >= 100) {
						error("Lastname not longer than 100 chars");
						error = true;
					}
				}
		    	
		    	if(birthday.getValue().equals("")) {
		    		error("Birthday is required");
		    		error=true;
		    	}
		    	if(CheckUtils.checkDate(birthday.getValue()) == false) {
		    		error("Birthday format invalid (yyyy-mm-dd). Please use the calender");
		    		error=true;
		    	}
		    	if(street.getValue().equals("")) {
		    		error("Street is required");
		    		error=true;
		    	}
		    	if(!error) {
					if(street.getValue().length() >= 100) {
						error("Street not longer than 100 chars");
						error = true;
					}
				}
		    	
		    	if(zip.getValue().equals("")) {
		    		error("ZIP is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
					if(zip.getValue().length() >= 100) {
						error("ZIP not longer than 100 chars");
						error = true;
					}
				}
		    	
		    	if(city.getValue().equals("")) {
		    		error("City is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
					if(city.getValue().length() >= 100) {
						error("City not longer than 100 chars");
						error = true;
					}
				}
		    	
		    	if(!error) {
		    		p.setFirstName(firstname.getValue());
		    		p.setLastName(lastname.getValue());
		    		p.setBirthday(birthday.getValue());
		    		
		    		Address a = new Address();
		    		a.setCity(city.getValue());
		    		a.setStreet(street.getValue());
		    		a.setZipCode(zip.getValue());
		    		
		    		p.setAddress(a);
		    		p.setStatus(status);
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