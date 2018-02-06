package hrrss.ui.employer.pictures;


import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hpsf.Thumbnail;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.IMultipartWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.upload.DiskFileItem;
import org.apache.wicket.util.upload.FileItem;
import org.apache.wicket.markup.html.image.NonCachingImage;


import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;

import hrrss.model.impl.Person;
import hrrss.service.impl.PersonService;
import hrrss.service.impl.CVService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;

public class Pictures extends BasePage {
	@SpringBean
	private PersonService ps;
	private FileUploadField fileUpload;
	
    public Pictures() {
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
    	final Long id = Long.valueOf(getSession().getAttribute("id").toString());

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
				add(new NonCachingImage("profile_pic", resource));
				bufferedImage.flush();	
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
    	}
    	
    	String editAll="";	
    	
    	editAll="<p> Change your profile picture</p>";
    	
    	//add(new Label("editAll",editAll).setEscapeModelStrings(false));
    	add(new Label("editAll",editAll).setEscapeModelStrings(false));
    	String applicantLink="<a href=\"/employer/pictures/\">Edit your profile picture</a><br>";
    	//applicantLink+="<a href=\"/applicant/cv/"+id+"/\">Check your CV</a>";
    	add(new Label("applicantLink", applicantLink).setEscapeModelStrings(false));
    	
    	Form form = new Form("editForm");
    	form.add(fileUpload = new FileUploadField("fileUpload"));
    	//setMaxSize(Bytes.kilobytes(100));
    	form.add(new Button("submit") {
  		  
		    @Override
		    public void onSubmit() {
		    	
		    
    		final FileUpload uploadedFile = fileUpload.getFileUpload();
    		
    		if(uploadedFile != null) {
		 
    			try {
    				Person p = ps.find(id);
    				
    				ByteArrayOutputStream baos = new ByteArrayOutputStream();
    				// to temp file
    				String tDir = System.getProperty("java.io.tmpdir");
    				java.io.File tempFile = uploadedFile.writeToTempFile();
    				// resize the temp file
    				BufferedImage bufferedImage = ImageIO.read(tempFile);
    				
    				BufferedImage newImage =  resize(bufferedImage, 270, 270);
    				bufferedImage.flush();
    				//temp file to byte array
    				ImageIO.write(newImage, "jpg", baos);
    				byte[] b = baos.toByteArray();
    				
    				newImage.flush();
    				baos.close();
    				
    				p.setPic(b);
    				ps.update(p);
    				
    				
			 
    				info("saved file: " +
    					uploadedFile.getClientFileName());
    				throw new RedirectToUrlException("/employer/pictures/");
    				
    			} catch (Exception e) {
    				throw new RedirectToUrlException("/employer/pictures/");
    			}
    		}
		    }
    	});
		
		form.setMultiPart(true); 
		form.add(new FeedbackPanel("feedback"));
    	add(form);
    	
    	//add(new AccountIndex("content"));
    	
    }
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        if(w > 270) {
        	  double verhaltnis=(double)w/(double)newW;
              double height=h/(double)verhaltnis;
              BufferedImage dimg = dimg = new BufferedImage(newW, (int)height, img.getType());  
              Graphics2D g = dimg.createGraphics();  
              g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
              g.drawImage(img, 0, 0, newW, (int)height, 0, 0, w, h, null);  
              g.dispose();  
              return dimg; 
        } else {
        	return img;
        }
      
        
    }  
    
    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) throws IOException {  
        return Thumbnails.of(img).forceSize(newW, newH).asBufferedImage();  
    
    }
}
