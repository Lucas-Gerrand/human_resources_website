package hrrss.ui.applicant.cv.document;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.util.TempFile;
import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RomanList;
import com.itextpdf.text.pdf.PdfWriter;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.impl.Person;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cv.Cv;
import hrrss.ui.applicant.pictures.Pictures;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;

public class Download extends BasePage {
	@SpringBean
	private PersonService ps;
	
	@SpringBean
	private CVService service;
	
	  private Font bigFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
	  private Font subtitle = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	  private Font mediumFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	  private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
	      Font.BOLD);
	  private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	      Font.BOLD);

	  final Logger logger = LoggerFactory.getLogger(Download.class);
	  
    public Download() {
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
    	
    	final Long id = Long.valueOf(idFromUrl[2]);
    	Person p = ps.find(id);
    	
    	if(p == null) {
    		setResponsePage(ProfilePageNotFound.class);
    		return;
    	}
    	byte[] a = p.getPic(); 
    	if(a==null){
    		info("There is no current picture");
    		add(new ContextImage("profile_pic", "/img/no.png"));
    	}
    	else{
    		try {
    			
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(a));
				BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
				resource.setImage(bufferedImage);
				add(new Image("profile_pic", resource));
				bufferedImage.flush();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
    	String editAll="";
    	
    	
    	if(idFromUrl[2].equals(getSession().getAttribute("id")+"")) {
    		editAll="<a href=\"/applicant/edit/\">Edit</a>";
    	}
    	String links = "<a href=\"/applicant/cv/"
    			+ getSession().getAttribute("id") + "/\">Check your CV </a>";
    	String linkPicture = "<a href=\"/applicant/pictures/"
    			+ getSession().getAttribute("id") + "/\">Edit your profile picture </a>";
    	add(new Label("linkPicture", linkPicture).setEscapeModelStrings(false));
    	add(new Label("links", links).setEscapeModelStrings(false));
    	add(new Label("editAll",editAll).setEscapeModelStrings(false));
    	
    	
    	add(new Label("firstname",p.getFirstName()));
    	add(new Label("lastname",p.getLastName()));
    	add(new Label("email",p.getEmail()));
    	add(new Label("birthday","birthday"));
    	
    	String fileDownload ="C:/test/test.doc";
    	String filename ="C:/test/testDoc.doc";
    	
    	String extractedText = "";
    	
    	
    	File newFile = new File(fileDownload);
    	
    	
    	
    	add(new Label("extract",extractedText));
    	
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
    }
    
    
    public String getDocToString (String fileName) throws IOException {
		File docFile=new File(fileName);  
		FileInputStream finStream=new FileInputStream(docFile.getAbsolutePath()); 
		HWPFDocument doc=new HWPFDocument(finStream);
		WordExtractor wordExtract=new WordExtractor(doc); 
		String [] dataArray =wordExtract.getParagraphText();
		String docContents = "";
		
		for(int i=0;i<dataArray.length;i++){
			
			docContents = docContents + dataArray[i];
		}
		
		finStream.close();
		return docContents;

	}
	public String extractStuff(String text){
		
		Scanner scanner = new Scanner(text);
		Pattern skills = Pattern.compile("\\[Skills:(.*?)\\]", Pattern.DOTALL);
		Pattern education = Pattern.compile("\\[Education:(.*?)\\]", Pattern.DOTALL);
		Pattern experience = Pattern.compile("\\[Experience:(.*?)\\]", Pattern.DOTALL);
		Pattern test = Pattern.compile("\\[Test:(.*?)\\]", Pattern.DOTALL);
		Matcher m = skills.matcher(text);
		Matcher n = education.matcher(text);
		Matcher o = experience.matcher(text);
		Matcher p = test.matcher(text);
		String combined = "";
		if(m.find() && n.find() && o.find() && p.find()){
			combined = combined + m.group(1) + " " + n.group(1) + " " + o.group(1)+ " " + p.group(1);
		}
		return combined;
	}

public File extract(Long id) throws DocumentException, IOException {
		
		Person p = ps.find(id);
    	List<ICV> listCV1 = service.loadCVById(id);
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
    	try{
    		
    		if((! p.getAddress().getCity().isEmpty()) && !p.getAddress().getCity().equals("") ){
    			address += p.getAddress().getCity();
    		}
    		
    		if((! p.getAddress().getStreet().isEmpty()) && !p.getAddress().getStreet().equals("") ){
			address += " " + p.getAddress().getStreet();
    		}
    		
    		if((! p.getAddress().getZipCode().isEmpty()) && !p.getAddress().getZipCode().equals("") ){
			address += " " + p.getAddress().getZipCode();
    		}
    	}
    	catch(Exception e){
    		
    		StackTraceElement[] stack = e.getStackTrace();
    	    String exception = "";
    	    for (StackTraceElement s : stack) {
    	        exception = exception + s.toString() + "\n\t\t";
    	    }
    	}
    	String email = ""; 
		
    	if((! cv.getPersonalEmail().isEmpty()) && cv.getPersonalEmail()!="" ){
			email += " " + cv.getPersonalEmail();
		}
    	String website = ""; 
    	if((! cv.getPersonalWebsite().isEmpty()) && cv.getPersonalWebsite()!="" ){
			website += " " + cv.getPersonalWebsite();
		}
		
		// CREATE TEMP FILE
		String fileName = "";
		fileName = fileName + name + "CMH";
		File tempFile = File.createTempFile(fileName, ".pdf");
	    OutputStream out = new FileOutputStream(tempFile);
	    
		//
		
		Document document = new Document();
		
	     PdfWriter.getInstance(document, out).setInitialLeading(16);
	     document.open();
	     
	     com.itextpdf.text.Image img = null;
	     byte[] pictureArray = p.getPic(); 
	    if(!(pictureArray ==null)){
	    	java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(pictureArray);
	    	img = com.itextpdf.text.Image.getInstance(awtImage, null);		
		} 
	    img.scaleAbsolute(200, 200);
	    img.setAbsolutePosition(375, 550);
 
 	
	    
	    	
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
	     document.add(img);
	     document.addTitle("Resume");
		    document.addSubject("Resume");
		    document.addKeywords("Java, PDF, iText");
		    document.addAuthor("Click Match Hire");
		    document.addCreator("Click Match Hire");
		    
		   
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
		    
		    preface.add(new Paragraph("Nationality: " + nationality, mediumFont));
		    preface.add(new Paragraph("Address: " + address, mediumFont));
		    preface.add(new Paragraph("E: " + email, mediumFont));
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
