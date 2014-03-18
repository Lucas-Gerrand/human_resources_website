package hrrss.ui.employer.job.edit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hrrss.model.IApplicant;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.impl.JobDescription;
import hrrss.model.impl.Person;
import hrrss.service.impl.ApplicantService;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.employer.job.JobManager;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class EditJob extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    JobDescriptionService js;
    
    @SpringBean
    ApplicantService appService;
    
    private Person p;
    
    private static final long serialVersionUID = 1L;

    public EditJob() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("a")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
		
    	if(idFromUrl.length != 4) {
			setResponsePage(JobManager.class);
			return;
		}
		
    	Integer editId;
    	Long jobId;
    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
    	try {
    		editId = Integer.parseInt(idFromUrl[3]);
	 		
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
        	
    		List<IJobDescription> list = new ArrayList<IJobDescription>();
        	
        	list = js.loadAllJobDescriptionByEmployerId(id);
        	
        	IJobDescription jsEdit = list.get(editId);
        	jobId = Long.valueOf(jsEdit.getId());
        	getSession().setAttribute("editId", editId);
        	final TextField<String> titel = new TextField<String>("title",Model.of(jsEdit.getJobTitle()));	
    		final TextArea<String> text = new TextArea<String>("text",Model.of(jsEdit.getMainPurpose()));
    		final TextField<String> qualification = new TextField<String>("qualification",Model.of(jsEdit.getQualification()));	
    		
    		Date jobDate = jsEdit.getDate();
    		List<IApplicant>hiredList = appService.loadHiredDateByJobID(jobId);
    		int days = 0;
    		String d ="";
    		
    		
    		if(hiredList.isEmpty()){
    			d+= "Nobody hired for this job yet";
    		}
    		String avg ="";
    		String hiredAmount = "";
    		String singleDays = "";
    		int averageHiring = 0;
    		int numberHired = 0;
    		
    		if(!hiredList.isEmpty()){
    			for(int i=0;i<hiredList.size();i++){
    				System.out.println("date of job: " + jobDate);
    				System.out.println("date of hired: " + hiredList.get(i).getHired());
    				Long applicantId = hiredList.get(i).getHired();
    				days = Days.daysBetween(new DateMidnight(jobDate), new DateMidnight(hiredList.get(i).getHiredDate())).getDays();
    				averageHiring += days;
    				d += String.valueOf(days) + ", ";
    			
    			}
    			hiredAmount += "You have hired: ";
    			hiredAmount += String.valueOf(hiredList.size()) + " people";
    			averageHiring = averageHiring/hiredList.size();
    			avg = "Average time to hire: " + String.valueOf(averageHiring);
    			singleDays += "The amount of days to hire per applicant was:";
    			singleDays +=d;
    		}
    		
    		add(new Label("numberHired", hiredAmount));
    		add(new Label("average", avg));
    		add(new Label("days", singleDays));
    		Form form = new Form("editForm");
    		
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
    		    		error("Description is required");
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

    		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
    		    		Long editId = Long.valueOf(getSession().getAttribute("editId").toString());
    		        	
    		    		List<IJobDescription> list = new ArrayList<IJobDescription>();
    		        	
    		        	list = js.loadAllJobDescriptionByEmployerId(id);
    		        	Integer editIdInt=null;
    		        	try {
    		        		editIdInt = Integer.valueOf(editId.toString());
    		        	} catch(Exception e) {
    		        		setResponsePage(JobManager.class);
    		        	}
    		        	
    		        	IJobDescription jsEdit = list.get(editIdInt);
    		        	
    		        	jsEdit.setJobTitle(titel.getValue());
    		        	jsEdit.setMainPurpose(text.getValue());
    		        	jsEdit.setQualification(qualification.getValue());
    		        	
    		    		js.update(jsEdit);
    		    		info("Changed Successfully");
    		    	
    		    	}
    				
    		    }
    		
    		});
    	
    		form.add(new Button("backToEdit") {
    			@Override
     		    public void onSubmit() {
    				setResponsePage(JobManager.class);
    			}
    		});
    		
    		form.add(new Button("backToProfile") {
    			@Override
     		    public void onSubmit() {
    				Long id = Long.valueOf(getSession().getAttribute("id").toString());
    		    	
    				throw new RedirectToUrlException("/employer/profile/"+id+"/");
    			}
    		});
    		
    		
    		form.add(new FeedbackPanel("feedback"));
    		
    		
    		add(form);
        	
    	} catch(Exception e) {
    		setResponsePage(JobManager.class);
			
    	}
	}

}
