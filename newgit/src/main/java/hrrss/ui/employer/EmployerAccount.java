package hrrss.ui.employer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import hrrss.model.IJobDescription;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;

public class EmployerAccount extends BasePage {
	@SpringBean
	private PersonService ps;

	@SpringBean
	private JobDescriptionService js;

	
    public EmployerAccount() {
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}

    	
    	String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
    	
    	if(idFromUrl.length != 3) {
    		setResponsePage(ProfilePageNotFound.class);
    		return;
    	}
    	
    	Long id = Long.valueOf(idFromUrl[2]);
    	
    	Person p1 = ps.find(id);
    	
    	if(p1 == null) {
    		setResponsePage(ProfilePageNotFound.class);
    		return;
    	}
    	
    	if(p1 instanceof Applicant) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	Employer p = (Employer) p1;
    	String applicantLink="<a href=\"/employer/pictures/\">Edit your profile picture</a><br>";
    	//applicantLink+="<a href=\"/applicant/cv/"+id+"/\">Check your CV</a>";
    	add(new Label("applicantLink", applicantLink).setEscapeModelStrings(false));
    	byte[] a = p.getPic(); 
    	if(a==null){
    		add(new ContextImage("profile_pic", "/img/no.png"));
    	}
    	else{
    		try {
    			
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(a));
				
				
				BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
				resource.setImage(bufferedImage);
				add(new NonCachingImage("profile_pic", resource));
				bufferedImage.flush();	
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
    	}
    	
    	String editAll="";
    	String editJob="";
    	
    	
    	if(idFromUrl[2].equals(getSession().getAttribute("id")+"")) {
    		editAll="<a href=\"/employer/edit/\">Edit</a>";
    		editJob="<a href=\"/employer/job/\">Edit</a>";
    	}
    	add(new Label("editAll",editAll).setEscapeModelStrings(false));
    	add(new Label("editJob",editJob).setEscapeModelStrings(false));
    	
    	add(new Label("company",p.getCompanyname()));
    	String address="";
    	if(p.getAddress() == null) {
    		address="-";
    	} else {
    		address=p.getAddress().getStreet()+"<br>"+p.getAddress().getZipCode()+" "+p.getAddress().getCity();
    	}
    	
    	add(new Label("address",address).setEscapeModelStrings(false));
    	String hp="";
    	if(p.getHomepage() != null) {
    		hp="<a target=\"_blank\" href=\"http://"+p.getHomepage()+"\">"+p.getHomepage()+"</a>";
    	}
    	add(new Label("homepage",hp).setEscapeModelStrings(false));
    	add(new Label("contact",p.getContact()));
    	
    	
    	List<IJobDescription> list = new ArrayList<IJobDescription>();
    	
    	list = js.loadAllJobDescriptionByEmployerId(id);
    	
    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");  
    	
    	String jobs="";
    	if(list.size() == 0) {
    		jobs="<tr width=\"500\"><td>No jobs</td></tr>";
    	} else {
    		
    		for(int i=0; i<list.size();i++) {
    			String link="";
    			if(idFromUrl[2].equals(getSession().getAttribute("id")+"")) {
    	    		link="<a href=\"/employer/search/"+i+"/\">Find applicants</a>";
    	    	}
    			
    			jobs+="<tr><td width=\"150\">"+format.format(list.get(i).getDate())+"</td><td width=\"300\"><a href=\"/job/"+list.get(i).getId()+"/\">"+list.get(i).getJobTitle()+"</a></td><td>"+link+"</td></tr>";
    		}
    	}
    	add(new Label("showJobs", jobs).setEscapeModelStrings(false));
    	
    	
    	//add(new AccountIndex("content"));
    	
    }
}
