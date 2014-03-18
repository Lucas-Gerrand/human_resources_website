package hrrss.ui.employer.job;

import hrrss.dao.impl.AbstractDAO;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.home.Home;
import hrrss.ui.util.PasswordUtil;
import hrrss.ui.util.SendMail;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class JobManager extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    JobDescriptionService js;

    private Person p;
    
    private static final long serialVersionUID = 1L;

    public JobManager() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("a")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
		
	
		Long id = Long.valueOf(getSession().getAttribute("id").toString());
		String backButton="<input type=\"button\" onclick=\"location.href='/employer/profile/"+id+"/';\" class='button_example' value=\"Back to Employer Profile\"/>";
    	
    	add(new Label("backToEmployer", backButton).setEscapeModelStrings(false));
    	Form form = new Form("editForm");
    	p = ps.find(id);
    	
    	
    	//edit jobs
    	List<IJobDescription> jobsList = new ArrayList<IJobDescription>();
    	
    	jobsList = js.loadAllJobDescriptionByEmployerId(id);
    	
    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");  
    	
    	String contentJobs="";
    	if(jobsList.size() == 0) {
    		contentJobs="<tr width=\"500\"><td>No jobs added</td></tr>";
    	} else {
    		for(int i=0; i<jobsList.size();i++) {
    			IJobDescription job = jobsList.get(i);
    			String date = format.format(job.getDate());
    			String title = job.getJobTitle();
    			String description = job.getMainPurpose();
    			String qualification = job.getQualification();
    			
    			String oneContent = "<tr><td width=\"200\"><b>"+date+"<b></td><td width=\"300\">"+title+"</td><td width=\"50\"><a href=\"/employer/job/edit/"+i+"/\">Edit</a></td><td width=\"50\"><a href=\"/employer/job/del/"+i+"/\">Delete</a></td></tr>";
    			contentJobs += oneContent;
    		}
    	}
    	add(new Label("jobsContent", contentJobs).setEscapeModelStrings(false));
    	
    	
    	
		final TextField<String> titel = new TextField<String>("title",Model.of(""));	
		final TextArea<String> text = new TextArea<String>("text",Model.of(""));
		final TextField<String> qualification = new TextField<String>("qualification",Model.of(""));	
		
		form.add(titel);
		form.add(text);
		form.add(qualification);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	
		    	if(titel.getValue().equals("")) {
		    		error("Title is required");
		    		error=true;
		    	}
		    	
		    	if(titel.getValue().length() >= 150) {
		    		error("Title maximum 150 characters");
		    		error=true;
		    	}
		    	
		    	if(text.getValue().equals("")) {
		    		error("Main proposal is required");
		    		error=true;
		    	}
		    	
		    	if(qualification.getValue().equals("")) {
		    		error("Qualification is required");
		    		error=true;
		    	}
		    	if(qualification.getValue().length() >= 100) {
		    		error("Qualification maximum 100 characters");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		
		    		
		    		JobDescription j = new JobDescription();
		    		j.setJobTitle(titel.getValue());
		    		j.setMainPurpose(text.getValue());
		    		j.setQualification(qualification.getValue());
		    		j.setEmployer((IEmployer) p);
		    		
		    		js.save(j);
		    		 info("Job Description added");
		    		setResponsePage(JobManager.class);
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
		
		
		add(form);

    }

}