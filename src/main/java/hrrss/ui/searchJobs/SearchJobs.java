package hrrss.ui.searchJobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import hrrss.ui.BasePage;
import hrrss.ui.util.DocumentSimilarity;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.codehaus.plexus.util.StringUtils;

public class SearchJobs extends BasePage {
    /**
     * 
     */
    @SpringBean
    //JobDescriptionService jobDescriptionService;

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("deprecation")
	public SearchJobs() {

    add(new Label("title", "Search"));

	Form form = new Form("searchAllJobs");

	final TextField<String> searchDescription = new TextField<String>("search",
		Model.of(""));
	form.add(searchDescription);
	
	
	form.add(new Button("submit") {

	    @Override
	    public void onSubmit() {
		boolean error = false;

		if (!error) {
			
			String searchResult = searchDescription.getValue();
			if(StringUtils.isNotBlank(searchResult)){
			ArrayList<String> list = new ArrayList<String>();
	    	
			try {
				// note that you need to have an index already created
				list.addAll(new DocumentSimilarity().search(searchResult));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
	    	
	    	System.out.println(list.size());
	    	String jobs="";
	    	if(list.size() == 0) {
	    		info("No jobs");
	    	} else {
	    		
	    		for(int i=0; i<list.size();i++) {
	    			info(list.get(i));
	    		}
	    	}
			}
	    	
			else{
				info("Please type a keyword into the box to search");
			}
			
		}
		
	    }

	});

	add(form);
	String styleAttr = "color:red;";
	form.add(new FeedbackPanel("feedback"));
	//add(new AttributeModifier("style", true, new Model<String>("color:red")));
    }
    
}
