package hrrss.ui;

import hrrss.ui.applicant.Account;
import hrrss.ui.panels.ApplicantLoggedinPanel;
import hrrss.ui.panels.EmployerLoggedinPanel;
import hrrss.ui.panels.LoginNavigation;
import hrrss.ui.panels.LogoutNavigation;
import hrrss.ui.panels.SigninPanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

public abstract class BasePage extends WebPage {
	public BasePage() {
		if(getSession().getAttribute("isLogin") == null) {
			getSession().setAttribute("isLogin", "false");
		} 
		
		if(getSession().getAttribute("personTyp") == null) {
			getSession().setAttribute("personTyp", "");
		}
		if(getSession().getAttribute("id") == null) {
			getSession().setAttribute("id", "0");
		}
		
		LoginNavigation loginNavigation = new LoginNavigation("loginNavigation");
		LogoutNavigation logoutNavigation = new LogoutNavigation("logoutNavigation");
		
		ApplicantLoggedinPanel applicantLoggedinPanel = new ApplicantLoggedinPanel("applicantLoggedinPanel");
		EmployerLoggedinPanel employerLoggedinPanel = new EmployerLoggedinPanel("employerLoggedinPanel");
		SigninPanel signinPanel = new SigninPanel("signinPanel");
		
		if (getSession().getAttribute("isLogin").equals("true")) {
			loginNavigation.setVisible(true);
			logoutNavigation.setVisible(false);
			if(getSession().getAttribute("personTyp").equals("a")) {
				applicantLoggedinPanel.setVisible(true);
				employerLoggedinPanel.setVisible(false);
				signinPanel.setVisible(false);
			} else if(getSession().getAttribute("personTyp").equals("e")) {
				applicantLoggedinPanel.setVisible(false);
				employerLoggedinPanel.setVisible(true);
				signinPanel.setVisible(false);
			}
		} else {
			loginNavigation.setVisible(false);
			logoutNavigation.setVisible(true);
			applicantLoggedinPanel.setVisible(false);
			employerLoggedinPanel.setVisible(false);
			signinPanel.setVisible(true);
		}
		add(loginNavigation);
		add(logoutNavigation);
		add(signinPanel);
		add(applicantLoggedinPanel);
		add(employerLoggedinPanel);
		
		add(new Label("footer","Click Match Hire"));
	}
}
