package hrrss.ui.applicant.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Global;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.IJobDescription;
import hrrss.model.impl.Applicant;
import hrrss.model.impl.Employer;
import hrrss.model.impl.Person;
import hrrss.service.impl.CVService;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.applicant.Account;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.util.CopyOfDocumentSimilarity;
import hrrss.ui.util.DocumentSimilarity;
import hrrss.ui.util.RoundUtil;

public class AutomaticSearch extends BasePage {
	@SpringBean
	CVService cvs;
	
	@SpringBean
	JobDescriptionService jbds;
	
	@SpringBean
	PersonService ps;
	final Logger logger = LoggerFactory.getLogger(AutomaticSearch.class);
	
	int show=0;
	int realJobCount=0;
    public AutomaticSearch() {
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    	
    	if(getSession().getAttribute("personTyp").equals("e")) {
    		setResponsePage(PersonTyp.class);
    		return;
    	}
    	
    	Long id = Long.valueOf(getSession().getAttribute("id").toString());
    	
    	List<ICV> cv = cvs.loadCVById(id);
    	
    	ICV mycv = null;
    	String out="";
    	if(cv.size()==0) {
    	} else if (cv.size()==1) {
    		mycv = cv.get(0);
    	} else {
    		mycv = cv.get(cv.size()-1);
    	}
    	List<Number>scores = new ArrayList<Number>();
		List<String>people = new ArrayList<String>();
    	if(mycv == null) {
    		out="<tr><td>Please fullfill your CV else the automatic matching cannot work!</td></tr>";
    	} else {
    		String url = RequestCycle.get().getRequest().getUrl().toString();
    		String[] idFromUrl = url.split("/");

    		if (idFromUrl.length == 2 || idFromUrl.length == 3) {
    			if (idFromUrl.length == 2) {
    				show = 5;
    			}
    			if (idFromUrl.length == 3) {
    				try {
    					show = Integer.parseInt(idFromUrl[2]);
    				} catch (Exception e) {
    					show = 5;
    				}
    			}

    		} else {
    			throw new RedirectToUrlException("/applicant/profile/" + id);

    		}
    		
    		
    		
    		
    		
    		String searchString = getCVtoString(mycv);
    		if(searchString.startsWith(" ")) {
    			out="<tr><td>Your CV is not fullfilled correctly</td></tr>";
    		} else {
    			
        		List<IJobDescription> allJobs = jbds.loadAllJobDescription();
        		ArrayList<String> a = new ArrayList<String>();
        		
        		a.add(searchString);
        		a.addAll(getAllJobDescriptions(allJobs));
        		
        		Map<Double, String> treeMap = new TreeMap<Double, String> (Collections.reverseOrder());
        		try {
					treeMap.putAll(new CopyOfDocumentSimilarity().mainComponent(a, false));
				} catch (IOException e) {
					
					e.printStackTrace();
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
        		
        		
            	int topNumber =show;
        		int count =0;
        		
        		
        		String shortDesc;
        		realJobCount = countFoundJobs(treeMap);
        		
        		logger.info("REAL COUNT"+realJobCount);
        		
        		for(Map.Entry entry:treeMap.entrySet()){
        			if(count<topNumber){
        				
        				double i=(Double)(entry.getKey());
        				if(i <=0.95 && i >0.05) {
        					String[] one = entry.getValue().toString().split("/");
        					Person p = ps.find(Long.valueOf(one[0]));
        					IJobDescription oneJob = jbds.getJobById(Long.valueOf(one[0])).get(0);
        					if(oneJob!=null) {
        					String str=oneJob.getMainPurpose();
            				
            			
            				
            				if(str.length() > 150) {
            					shortDesc = str.substring(0,150);
            					shortDesc+="...";
            					
            				} else {
            					shortDesc=str;
            					
            				}
            				
            				out+="<tr><td colspan=\"2\"><b>"+(count+1)+". Result &#8211; <a href=\"/job/"+one[0]+"/\">"+oneJob.getJobTitle()+"</a></b></td>";
            				out+= "<td>" + "<a href=\"/message/messages/" + id + "/" + one[0] +"\">Send Message:" + "</a></td></tr>";
            				out+="<tr><td>"+shortDesc+"</td></tr>";
            				out+="<tr><td >Matching to "+RoundUtil.round2(i*100)+"%</td></tr>";
            				out+="<tr><td id=\"small\">Created on "+oneJob.getDate()+"</td></tr>";
            				out+="<tr><td></td></tr>";
            				out+="<tr><td height=\"25\"></td></tr>";
            				shortDesc="";
            				people.add("<a href=\"/job/"+one[0]+"\">"+oneJob.getJobTitle()+"</a>");
            				scores.add(RoundUtil.round2(i*100));
            				count++;
        					}
        					
        					
        					
        					
        				logger.info("Key: " + i + " Value= " + entry.getValue());
        				
        				
        				}
        				
        			}
        		}
        		logger.info("Number of jobs: ", allJobs.size());
    			
        	}
    		if(out.equals("") || out.equals("<tr><td>Your CV is not fullfilled correctly</td></tr>")) {
    			
    			add(new Label("content", "No results found").setEscapeModelStrings(false));
    			add(new Label("chart", ""));
    		} else {
    			
        		Options options = new Options();
        		options.setTitle(new Title("Automatic Matching: Best Jobs"));
        		options.setChartOptions(new ChartOptions().setType(SeriesType.BAR));
        		options.setGlobal(new Global().setUseUTC(Boolean.TRUE));
        		options.setSubtitle(new Title("Click Match Hire"));
        		options.setxAxis(new Axis().setCategories(people));
        		options.setyAxis(new Axis().setTitle(new Title("Percentages")));
        		options.setPlotOptions(new PlotOptionsChoice()
            .setBar(new PlotOptions()
                .setDataLabels(new DataLabels()
                    .setEnabled(Boolean.TRUE))));
        		options.setLegend(new Legend()
                .setLayout(LegendLayout.VERTICAL)
                .setAlign(HorizontalAlignment.RIGHT)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setX(-100)
                .setY(100)
                .setFloating(Boolean.TRUE)
                .setBorderWidth(1)
                .setBackgroundColor(new HexColor("#ffffff"))
                .setShadow(Boolean.TRUE));
        
        		options.addSeries(new SimpleSeries()
        			.setName("Jobs")
        			.setData(scores));
        			//.setData(person1, person2, person3, person4, person5));
        		if(show >= 25) {
        			add(new Chart("chart", options).setVisible(false));
        		} else {
        			add(new Chart("chart", options));
        		}
        		
        		if (show < realJobCount) {
					out += "<tr><td colspan=\"6\" align=\"right\"><a href=\"/applicant/search/"
							+ (show + 5)
							+ "/\">Show More</a></td></tr>";
				}
        		add(new Label("content", out).setEscapeModelStrings(false));
        		
    	}
    	
			
        	
        	//js.delete(jsDel);
        	
    		//setResponsePage(EditJob.class);
		}
    	
    }
    
    private int countFoundJobs(Map<Double, String> treeMap) {
    	int count=0;
    	for(Map.Entry entry:treeMap.entrySet()){	
			double i=(Double)(entry.getKey());
			if(i <=0.95 && i >0.05) {
				String[] one = entry.getValue().toString().split("/");
				Person p = ps.find(Long.valueOf(one[0]));
				IJobDescription oneJob = jbds.getJobById(Long.valueOf(one[0])).get(0);
				if(oneJob!=null) {
					String str=oneJob.getMainPurpose();
					count++;
				}
					
				
			}
				
		}
		
    	return count;
	}

	public String getCVtoString(ICV cv) {
    	String eduForUser="";
		String expForUser="";
		String skillsForUser="";
		String titleExp = "";
		String personWrap = "[Person: ";
		String titleWrap = "[Title: ";
		String contentWrap = "[Content: ";
		String QualificationsWrap = "[Qualifications: ";
		
		List<ICVEducation> educationList = cv.getEducations();
		for (ICVEducation edu : educationList) {
			eduForUser += edu.getDescription()+" ";
		}
		
		List<ICVExperience> experiencesList = cv.getExperiences();
		for (ICVExperience exp : experiencesList) {
			expForUser += exp.getDescription()+" ";
			titleExp += exp.getTitle();
			
		}
		
		List<ICVSkill> skillsList = cv.getSkills();
		for (ICVSkill skill : skillsList) {
			skillsForUser += skill.getDescription()+" ";
		}
		
		
		
		String all = personWrap + " ]" + titleWrap + titleExp + " ]" + contentWrap + expForUser + eduForUser + " ]" + QualificationsWrap + 
				skillsForUser + " ]"; 
		
		return all;
		
    }
    
 public ArrayList<String> getAllJobDescriptions(List<IJobDescription> allJobs){
	 String all = "";
	 String id= "";
	 String title= "";
	 String content= "";
	 String Qualifications= "";
	 
	 String titleWrap = "[Title: ";
	 String contentWrap = "[Content: ";
	 String QualificationsWrap = "[Qualifications: ";

	 ArrayList<String> jobs= new ArrayList<String>();
	
	 
	 
	 for(int i=0;i<allJobs.size();i++){
		 all = "";
		 id= "";
		 title= "";
		 content= "";
		 Qualifications= "";
		 
		 id+= allJobs.get(i).getId() +"/" ;
		 		 
		 title+= allJobs.get(i).getJobTitle() + " ]";
		 
		 content+= allJobs.get(i).getMainPurpose() + " ]";
		 
		 Qualifications +=	allJobs.get(i).getQualification() +" ]";
		 
		 all+= id + titleWrap + title +  contentWrap + content + QualificationsWrap + Qualifications;
		 
		 jobs.add(all);
	 }
	 
	 return jobs;
	 
 }
}
