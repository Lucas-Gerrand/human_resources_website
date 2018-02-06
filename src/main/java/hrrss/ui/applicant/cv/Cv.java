package hrrss.ui.applicant.cv;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.CV;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.Account;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;
import hrrss.ui.util.ActivationUtil;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.hibernate.cfg.CreateKeySecondPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RomanList;
import com.itextpdf.text.pdf.PdfWriter;

public class Cv extends BasePage {

    private static final long serialVersionUID = -8670938515487163091L;

    @SpringBean
    private PersonService ps;

    @SpringBean
    private CVService CVService;
    
   
      // fonts for the CV extract
      private Font bigFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
	  private Font subtitle = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	  private Font mediumFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	  private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
	      Font.BOLD);
	  private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	      Font.BOLD);
	  
    public Cv() {
	if (getSession().getAttribute("isLogin").equals("false")) {
	    setResponsePage(Authentication.class);
	    return;
	}

	String url = RequestCycle.get().getRequest().getUrl().toString();
	String[] idFromUrl = url.split("/");

	final Long id = Long.valueOf(idFromUrl[2]);

	if (idFromUrl.length != 3) {
	    setResponsePage(ProfilePageNotFound.class);
	    return;
	}

	Person p = ps.find(id);

	if (p == null) {
	    setResponsePage(ProfilePageNotFound.class);
	    return;
	}
	
	
	//MAIN
	String mainTitle=p.getFirstName()+" "+p.getLastName();
	String editGeneral="";
	String editContact="";
	String skillEdit="";
	String educationEdit="";
	String experienceEdit="";
	
	//EIGENER CV?
	if(getSession().getAttribute("id").toString().equals(id.toString())) {
		mainTitle="Your CV";
		editGeneral="(<a href=\"/applicant/edit/\">Edit</a>)";
		editContact="(<a href=\"/applicant/cvcontact/\">Edit</a>)";
		skillEdit="(<a href=\"/applicant/cvskills/\">Edit</a>)";
		educationEdit="(<a href=\"/applicant/cveducation/\">Edit</a>)";
		experienceEdit="(<a href=\"/applicant/cvexperience/\">Edit</a>)";
	}
	
	
	add(new Label("mainTitle", mainTitle).setEscapeModelStrings(false));
	
	//GENERAL
	add(new Label("editGeneral", editGeneral).setEscapeModelStrings(false));
	add(new Label("firstname", p.getFirstName()));
	add(new Label("lastname", p.getLastName()));
	add(new Label("email", p.getEmail()));
	add(new Label("birthday", p.getBirthday()));
	List<ICV> listCV1 = CVService.loadCVById(id);
	
	// if null then add an empty file
	add(new DownloadLink("downloadButton", new AbstractReadOnlyModel<File>()
	        {	
	            private static final long serialVersionUID = 1L;
	            
	            @Override
	            
	            public File getObject()
	            {
	                
	            	File tempFile = null;
	                try
	                {
	                    tempFile = extract(id);

	                }
	                catch (IOException e)
	                {
	                    throw new RuntimeException(e);
	                } catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	             
	                	return tempFile;
	                
	            }
	        }).setCacheDuration(Duration.NONE).setDeleteAfterDownload(true));
	//add(new AccountIndex("content"));
	//CONTACT
	
	CV myCV = (CV) listCV1.get(0);
	
	add(new Label("editContact", editContact).setEscapeModelStrings(false));
	add(new Label("nationality", myCV.getNationality()));
	add(new Label("personalEmail", myCV.getPersonalEmail()));
	add(new Label("personalWebsite", myCV.getPersonalWebsite()));
	
		
	//EDUCATION
	List<ICVEducation> educationList = myCV.getEducations();
	String contentEducation="";
	if(educationList.size() == 0) {
		contentEducation="<tr><td>No education added</td></tr>";
	} else {
		for(int i=0; i<educationList.size(); i++) {
			ICVEducation education = educationList.get(i);
			String start = education.getStart().toString();
			String end = education.getEnd().toString();
			String educationPlace = education.getFacility();
			String location = education.getLocation();
			String description = education.getDescription();
			
			String oneContent = "<tr><td><b>"+start+" till "+end+"<b></td><td><b>"+location+" - "+educationPlace+"<b></td></tr><tr><td colspan=\"2\">"+description+"</td></tr>";
			contentEducation += oneContent;
		}
		
	}
	
	add(new Label("editEducation", educationEdit).setEscapeModelStrings(false));
	add(new Label("contentEducation", contentEducation).setEscapeModelStrings(false));
	
	//SKILLS
	List<ICVSkill> skillList = myCV.getSkills();
	String contentSkill="";
	if(skillList.size() == 0) {
		contentSkill="<tr><td>No skills added</td></tr>";
	} else {
		for(int i=0; i<skillList.size(); i++) {
			ICVSkill skill = skillList.get(i);
			
			String skillTyp = skill.getSkillType();
			String skillDescription = skill.getDescription();
			
			String oneSkillContent = "<tr><td><b>"+skillTyp+"<b><td>"+skillDescription+"</td></tr>";
			contentSkill += oneSkillContent;
		}
		
	}
	
	add(new Label("editSkills", skillEdit).setEscapeModelStrings(false));
	add(new Label("contentSkills", contentSkill).setEscapeModelStrings(false));
	
	//EXPERIENCE
	List<ICVExperience> experienceList = myCV.getExperiences();
	String contentExperience="";
	
	if(experienceList.size() == 0) {
		contentExperience="<tr><td>No skills added</td></tr>";
	} else {
		for(int i=0; i<experienceList.size(); i++) {
			ICVExperience experience = experienceList.get(i);
			String end = experience.getEnd();
			String start = experience.getStart();
			String company = experience.getCompany();
			String title = experience.getTitle();
			String description = experience.getDescription();
			
			String oneEducationContent = "<tr><td><b>"+start+" till "+end+"</b></td><td><b>"+company+"</b></td></tr><tr><td colspan=\"2\">"+description+"</td></tr>";
			contentExperience += oneEducationContent;
		}
	}
			
	add(new Label("editExperience", experienceEdit).setEscapeModelStrings(false));
		
	add(new Label("contentExperience", contentExperience).setEscapeModelStrings(false));
		
	
    }
public File extract(Long id) throws DocumentException, IOException {
		
		Person p = ps.find(id);
    	List<ICV> listCV1 = CVService.loadCVById(id);
    	ICV cv = null;
    	if ((null != listCV1) && (0 != listCV1.size())) {

    	    cv = (ICV) listCV1.get(0);
    	}
    	
    	String name ="";
    	if((! p.getFirstName().isEmpty()) && p.getFirstName()!="" ){
    		name = name + p.getFirstName() + " " + p.getLastName(); 
    	}
    	String nationality ="";
    	if((! cv.getNationality().isEmpty()) && cv.getNationality()!="" ){
    		nationality += cv.getNationality();
    	}
    	
    	String address = "";
    	String streetString="";
		String cityString="";
		String zipString="";
		if(p.getAddress() != null) {
			streetString = p.getAddress().getStreet();
			cityString = p.getAddress().getCity();
			zipString = p.getAddress().getZipCode();
		}
    	
    		if(cityString != null){ 
    			address += cityString;
    			
    		}
    		if(streetString != null){
    			address += " " + streetString;
    			
    		}
    		
    		if(zipString != null){
    			address += " " + zipString;
    		}
    		
		
    	String email = ""; 
		
    	if((! cv.getPersonalEmail().isEmpty()) && cv.getPersonalEmail()!="" ){
			email += " " + cv.getPersonalEmail();
		}
    	String website = ""; //cv.getPersonalWebsite(); 
    	if((! cv.getPersonalWebsite().isEmpty()) && cv.getPersonalWebsite()!="" ){
			website += " " + cv.getPersonalWebsite();
		}
		
		// CREATE TEMP FILE
		String fileName = ""; //ActivationUtil.getActivation();
		fileName = fileName + name + "CMH";
		File tempFile = File.createTempFile(fileName, ".pdf");
	    OutputStream out = new FileOutputStream(tempFile);
	    
		//
		
		Document document = new Document();
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     PdfWriter.getInstance(document, out).setInitialLeading(16);
	     document.open();
	     
	     com.itextpdf.text.Image img = null;
	     byte[] pictureArray = p.getPic(); 
	    if(!(pictureArray ==null)){
	    	java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(pictureArray);
	    	img = com.itextpdf.text.Image.getInstance(awtImage, null);	
	    	 img.scaleAbsolute(200, 200);
	 	    img.setAbsolutePosition(375, 550);
		} 
	   
 
 	
	    
	    	
	     RomanList skillRomanList = new RomanList();
	     if(!(cv.getSkills().isEmpty() ) && cv.getSkills().size()!= 0){
	    	List<ICVSkill> skillList = cv.getSkills();
			for(int i=0;i<skillList.size();i++){
				skillRomanList.add(skillList.get(i).getSkillType().toString()+		
				" \n"+ (skillList.get(i).getDescription())+ " \n");
				
			}            
	     }
	    
	    RomanList educationRomanList = new RomanList();
	    if(!(cv.getEducations().isEmpty() ) && cv.getEducations().size()!= 0){ 
	    	
	    	List<ICVEducation>educationList = cv.getEducations();
	    	for(int j=0;j<educationList.size();j++){
	    		educationRomanList.add(educationList.get(j).getLocation() + 
					" \n" + educationList.get(j).getFacility() +
					" \n" + educationList.get(j).getDescription() +
					" \n" + educationList.get(j).getStart().toString() + 
					" - " + educationList.get(j).getEnd().toString()+ " \n");
	    	}
	    }
	    
	    RomanList experienceRomanList =  new RomanList();
	    if(!(cv.getExperiences().isEmpty() ) && cv.getExperiences().size()!= 0){
		
		List<ICVExperience>experienceList = cv.getExperiences();
			for(int k=0;k<experienceList.size();k++){
				experienceRomanList.add(experienceList.get(k).getTitle() + 
					" " + experienceList.get(k).getCompany() +
					" \n" + experienceList.get(k).getDescription() +
					" \n" + experienceList.get(k).getStart().toString() +
					" - " + experienceList.get(k).getEnd().toString() + " \n");
			}
	    }
	    if(img!=null) {
		     document.add(img);

	    }
	     document.addTitle("Resume");
		    document.addSubject("Resume");
		    document.addKeywords("Java, PDF, iText");
		    document.addAuthor("Click Match Hire");
		    document.addCreator("Click Match Hire");
		    
		    //document.setMargins(72, 72, 72, 72);
		    document.setMarginMirroring(true);
		    Paragraph preface = new Paragraph();
		    Paragraph nameTitle = new Paragraph();
		    Paragraph educationSection = new Paragraph();
		    Paragraph skillsSection = new Paragraph();
		    Paragraph experienceSection = new Paragraph();
		    // We add one empty line
		    
		    addEmptyLine(preface, 1);
		    // Lets write a big header
		    nameTitle.add(new Paragraph(name, bigFont));
		    nameTitle.setAlignment(Element.ALIGN_CENTER);
		    //preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			 //       smallBold)); 
		    preface.add(new Paragraph("Nationality: " + nationality, mediumFont));
		    preface.add(new Paragraph("Address: " + address, mediumFont));
		    preface.add(new Paragraph("Email: " + email, mediumFont));
		    preface.add(new Paragraph("Website: " + website, mediumFont));
		    addEmptyLine(preface, 1);
		    
		    educationSection.add(new Paragraph("Education" ,subtitle));
		    addEmptyLine(educationSection, 1);
		    
		    addEmptyLine(skillsSection, 1);
		    skillsSection.add(new Paragraph("Skills" ,subtitle));
		    addEmptyLine(skillsSection, 1);
		   
		    
		    addEmptyLine(experienceSection, 1);
		    experienceSection.add(new Paragraph("Experience", subtitle));
		    addEmptyLine(experienceSection, 1);
		    preface.setAlignment(Element.ALIGN_LEFT);
		    educationSection.setAlignment(Element.ALIGN_LEFT);
		    skillsSection.setAlignment(Element.ALIGN_LEFT);
		    experienceSection.setAlignment(Element.ALIGN_LEFT);
		    
		    document.add(nameTitle);
		    document.add(preface);
		    document.add(educationSection);
		    document.add(educationRomanList);
		    document.add(skillsSection);
		    document.add(skillRomanList);
		    document.add(experienceSection);
		    document.add(experienceRomanList);
		    
		    document.close();
		 
		    out.close();
		    return tempFile;
            
	}
	  private static void addEmptyLine(Paragraph paragraph, int number) {
		    for (int i = 0; i < number; i++) {
		      paragraph.add(new Paragraph(" "));
		    }
		  }
}
