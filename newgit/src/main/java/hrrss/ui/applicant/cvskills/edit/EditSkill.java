package hrrss.ui.applicant.cvskills.edit;

import hrrss.model.ICV;
import hrrss.model.ICVSkill;
import hrrss.model.impl.CV;
import hrrss.service.impl.CVService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.cvskills.CVSkills;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;

import java.util.List;

import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class EditSkill extends BasePage {
    @SpringBean
    PersonService ps;

    @SpringBean
    private CVService CVService;

    public EditSkill() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	
		String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
		
    	if(idFromUrl.length != 4) {
			setResponsePage(CVSkills.class);
			return;
		}
		
    	Integer editId;
    	ICVSkill editSkill=null;
    	try {
    		editId = Integer.parseInt(idFromUrl[3]);
    		
    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
	       	
    		getSession().setAttribute("editId", editId);
    		
    		List<ICV> listCV1 = CVService.loadCVById(id);
        	CV myCV = (CV) listCV1.get(0);
        	
        	List<ICVSkill> skillList = myCV.getSkills();
        	if(editId > skillList.size()) {
        		throw new RedirectToUrlException("/applicant/cvskills/");
        	}
        	editSkill = skillList.get(editId);
        	
    	
    	
    	} catch(Exception e) {
    		setResponsePage(CVSkills.class);
    		return;
    	}
    	
    	//Edit Skill
    	String selectItem = editSkill.getSkillType();
    	if(selectItem.startsWith("Computer")) {
    		selectItem = "skill1";
    	}
    	
    	if(selectItem.startsWith("Driving")) {
    		selectItem = "skill2";
    	}
    	
    	if(selectItem.startsWith("Job")) {
    		selectItem = "skill3";
    	}
    	
    	if(selectItem.startsWith("Manager")) {
    		selectItem = "skill4";
    	}
    	
    	
    	if(selectItem.startsWith("Other")) {
    		selectItem = "skill5";
    	}
    	final Select skillType = new Select("skillType", Model.of(selectItem));
    	
    	skillType.add(new SelectOption<String>("skill1", Model.of("skill1")));
    	skillType.add(new SelectOption<String>("skill2", Model.of("skill2")));
    	skillType.add(new SelectOption<String>("skill3", Model.of("skill3")));
    	skillType.add(new SelectOption<String>("skill4", Model.of("skill4")));
    	skillType.add(new SelectOption<String>("skill5", Model.of("skill5")));
    	
    	final TextField<String> description = new TextField<String>("description",Model.of(editSkill.getDescription()));	
		
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
		    		Integer editId = Integer.parseInt((getSession().getAttribute("editId").toString()));
		    		
		    		Long id = Long.valueOf(getSession().getAttribute("id").toString());
			       	
		    		List<ICV> listCV1 = CVService.loadCVById(id);
		        	CV myCV = (CV) listCV1.get(0);
		        	
		        	List<ICVSkill> skillList = myCV.getSkills();
		        	ICVSkill editSkill = skillList.get(editId);
		        	skillList.remove(editSkill);
		        
		        	editSkill.setDescription(description.getValue());
		        	editSkill.setSkillType(skillTypeFromDrop);
		        	
		        	skillList.add(editSkill);
		        	
		    		CVService.updateCV(myCV);
		    		info("Changed Successfully");
		    	}
	    	
			}
		});
		
		form.add(new Button("backToSkill") {
			@Override
		
			public void onSubmit() {
			    throw new RedirectToUrlException("/applicant/cvskills/");
			}
		});
		
		form.add(new Button("backToCV") {
			@Override
		
			public void onSubmit() {
				Long id = Long.valueOf(getSession().getAttribute("id").toString());
			    throw new RedirectToUrlException("/applicant/cv/"+id+"/");
			}
		});
		
		form.add(new FeedbackPanel("feedback"));
		add(form);
		
    	
    }

}