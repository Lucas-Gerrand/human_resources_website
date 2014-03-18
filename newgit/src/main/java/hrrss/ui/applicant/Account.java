package hrrss.ui.applicant;

import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account extends BasePage {
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private PersonService ps;
	
	final Logger logger = LoggerFactory.getLogger(Account.class);
	    
	 
    public Account() {
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
    	
    	if(p1 instanceof Employer) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	Applicant p = (Applicant) p1;
    	byte[] a = p.getPic(); 
    	if(a==null){
    		info("There is no current picture");
    		add(new ContextImage("profile_pic", "/img/no.png"));
    	}
    	else{
    		try {
    			
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(a));
				bufferedImage.flush();
				BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
				resource.setImage(bufferedImage);
				add(new Image("profile_pic", resource));	
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
    	String editAll="";
    	String editAll1="";
    	logger.info(idFromUrl[2]+"-"+getSession().getAttribute("id"));
    	String links = "<a href=\"/applicant/cv/"
        			+ id + "/\">Show CV </a>";
    	String linkPicture="";
    	
    	
    	if(idFromUrl[2].equals(getSession().getAttribute("id")+"")) {
    		editAll="<a href=\"/applicant/edit/\">Edit</a>";
    		
    		links = "<a href=\"/applicant/cv/"
        			+ getSession().getAttribute("id") + "/\">Check your CV </a>";
        	linkPicture = "<a href=\"/applicant/pictures/\">Edit your profile picture </a><br>";
    	} else {
    		
    	}
    	
    	add(new Label("linkPicture", linkPicture).setEscapeModelStrings(false));
    	add(new Label("links", links).setEscapeModelStrings(false));
    	add(new Label("editAll",editAll).setEscapeModelStrings(false));
    	add(new Label("editAll1",editAll).setEscapeModelStrings(false));
        
    	add(new Label("firstname",p.getFirstName()));
    	add(new Label("lastname",p.getLastName()));
    	add(new Label("email",p.getEmail()));
    	add(new Label("birthday",p.getBirthday()));
    	
    	String streetString="-";
    	String cityString="-";
    	String zipString="-";
    	if(p.getAddress() != null) {
    		streetString = p.getAddress().getStreet();
    		cityString = p.getAddress().getCity();
    		zipString = p.getAddress().getZipCode();
    	}
    	add(new Label("street",streetString));
    	add(new Label("city",cityString));
    	add(new Label("zip",zipString));
    	
    	String status = p.getStatus();
    	String statusString="No i am not searching for jobs";
    	
    	if(status.equals("yes")) {
    		statusString="Yes i am searching for jobs";
    	}
    	add(new Label("status", statusString));
    	
    	
    	
    	//add(new AccountIndex("content"));
    	
    }
}
