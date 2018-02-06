package hrrss.ui.questionnairemaker;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import hrrss.model.impl.Quastion;
import hrrss.model.impl.Survey;
import hrrss.ui.BasePage;

public class VideoQuestion extends BasePage{
	Quastion q = new Quastion();
	public VideoQuestion(final Survey s){
		add(new Label("title", "Open Quastion"));
		Form form = new Form("newQForm");
		final TextField<String> question = new TextField<String>("quastion",Model.of(""));
		form.add(question);

		
		
		form.add(new Button("submit") {
			  
		    @Override
		    public void onSubmit() {
		    	boolean error=false;
		    	
		    	if(question.getValue().equals("")) {
		    		error("Text is required");
		    		error=true;
		    	}
		    	
		    	else if(question.getValue().length() > 70){
		    		error("Question must have not mot then 70 characters");
		    		error = true;
		    	}
		    	
		    	else if(!error) {
		    		
		    		q.setQuastion(question.getValue());
					q.setTypeOfQuastion("file");					  
					s.addQuestion(q);
					q.setSurvey(s); 
					info("Question added");
					setResponsePage(new Questionnaire(s));
		    	}
				
				
		
		    }
		
		});
		
		form.add(new Button("cancel") {
			  
		    @Override
		    public void onSubmit() {
		   	setResponsePage(new Questionnaire(s));
		    }
		});
		
		form.add(new FeedbackPanel("feedback"));
		
		add(form);

	}

}
