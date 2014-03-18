package hrrss.ui.applicant.cvskills;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVSkill;
import hrrss.model.impl.CV;
import hrrss.model.impl.CVSkill;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import java.util.List;

import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CVSkills extends BasePage {
    @SpringBean
    private PersonService ps;

    @SpringBean
    private CVService CVService;

    public CVSkills() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	String backButton="<input type=\"button\" onclick=\"location.href='/applicant/cv/"+id+"/';\" class='button_example' value=\"Back to CV\"/>";
		
    	add(new Label("back", backButton).setEscapeModelStrings(false));
	
    	//EDIT SKILLS
    	List<ICV> listCV1 = CVService.loadCVById(id);
    	CV myCV = (CV) listCV1.get(0);
    	
    	List<ICVSkill> skillList = myCV.getSkills();
    	String contentSkill="";
    	if(skillList.size() == 0) {
    		contentSkill="<tr><td>No skills added</td></tr>";
    	} else {
    		for(int i=0; i<skillList.size(); i++) {
    			ICVSkill skill = skillList.get(i);
    			String skillType = skill.getSkillType();
    			String description = skill.getDescription();
    			
    			String oneContent = "<tr><td width=\"200\"><b>"+skillType+"<b></td><td width=\"300\">"+description+"</td><td width=\"50\"><a href=\"/applicant/cvskills/edit/"+i+"/\">Edit</a></td><td width=\"50\"><a href=\"/applicant/cvskills/del/"+i+"/\">Delete</a></td></tr>";
    			contentSkill += oneContent;
    			
    		}
    	}
    	
    	add(new Label("skillContent", contentSkill).setEscapeModelStrings(false));
    	
    	
    	
    	//ADD NEW EDUCATION
        
    	final Select skillType = new Select("skillType", Model.of(""));
    	skillType.add(new SelectOption<String>("skill1", Model.of("skill1")));
    	skillType.add(new SelectOption<String>("skill2", Model.of("skill2")));
    	skillType.add(new SelectOption<String>("skill3", Model.of("skill3")));
    	skillType.add(new SelectOption<String>("skill4", Model.of("skill4")));
    	skillType.add(new SelectOption<String>("skill5", Model.of("skill5")));
    	
    	final TextField<String> description = new TextField<String>("description",Model.of(""));	
		
		Form form = new Form("editForm");
		
		
		form.add(skillType);
		form.add(description);
		
		form.add(new Button("submit") {
		  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	String skillTypeFromDrop="";
		    	if(skillType.getValue().equals("")) {
		    		error("Skilltype is required");
		    		error=true;
		    	} else {
		    		if(skillType.getValue().equals("option16")) {
		    			skillTypeFromDrop="Computer Skills";
		    		}
		    		
		    		if(skillType.getValue().equals("option17")) {
		    			skillTypeFromDrop="Driving Skills";
		    		}
		    		
		    		if(skillType.getValue().equals("option18")) {
		    			skillTypeFromDrop="Job Related Skills";
		    		}
		    		
		    		if(skillType.getValue().equals("option19")) {
		    			skillTypeFromDrop="Manager Skills";
		    		}
		    		
		    		if(skillType.getValue().equals("option20")) {
		    			skillTypeFromDrop="Other Skills";
		    		}
		    	}
		    	
		    	if(description.getValue().equals("")) {
		    		error("Description is required");
		    		error=true;
		    	}
		    	
		    	if(!error) {
		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
		        	
		    		List<ICV> listCV1 = CVService.loadCVById(id);
		    		CV myCV = (CV) listCV1.get(0);
		    		
		    		List<ICVSkill> newSkillList = myCV.getSkills();
		    		
		    		CVSkill newSkill = new CVSkill();
		    		newSkill.setCv(myCV);
		    		newSkill.setDescription(description.getValue());
		    		newSkill.setSkillType(skillTypeFromDrop);
		    		
		    		newSkillList.add(newSkill);
		    		
		    		CVService.updateCV(myCV);
		    		
				    setResponsePage(CVSkills.class);
		    		
		    	}
				
				
		
		    }
		
		});
	
		form.add(new FeedbackPanel("feedback"));
	
		add(form);

    }

}