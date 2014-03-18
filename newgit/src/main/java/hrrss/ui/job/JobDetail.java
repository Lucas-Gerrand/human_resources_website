package hrrss.ui.job;

import hrrss.model.IApplicant;
import hrrss.model.IEmployer;
import hrrss.model.IJobDescription;
import hrrss.model.IPerson;
import hrrss.model.impl.Person;
import hrrss.service.impl.ApplicantService;
import hrrss.service.impl.JobDescriptionService;
import hrrss.service.impl.PersonService;
import hrrss.ui.BasePage;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.NotFound;
import hrrss.ui.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.CreditOptions;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.Global;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.Overflow;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;

public class JobDetail extends BasePage {
    @SpringBean
    private PersonService ps;

    @SpringBean
    private JobDescriptionService jobService;

    @SpringBean
    ApplicantService appService;
    
    final Logger logger = LoggerFactory.getLogger(JobDetail.class);
    
    public JobDetail() {
    	
    	if(getSession().getAttribute("isLogin").equals("false")) {
    		setResponsePage(Authentication.class);
    		return;
    	}
    
    	String url = RequestCycle.get().getRequest().getUrl().toString();
    	String[] idFromUrl = url.split("/");
    	
    	if(idFromUrl.length != 2) {
    		setResponsePage(NotFound.class);
    		return;
    	}
    	String companyName = "";
    	
    	String title="";
    	String content="";
    	String avg ="";
    	String add = "";
		String hiredAmount = "";
		String singleDays = "";
		String adress = "";
		String adress2 = "";
		String personN = "";
		String mail = "";
		String tel = "";
		int averageHiring = 0;
		int numberHired = 0;
		int days = 0;
		String d ="";
		Long hiredID = null;
		String applicantName = "";
    	try {
    		
    		Long id = Long.valueOf(idFromUrl[1]);
    		List<IJobDescription> jobsList = jobService.loadAllJobDescription();
    		IJobDescription jobById = null;
    		
    		for(int i=0; i<jobsList.size(); i++) {
    			IJobDescription job = jobsList.get(i);
    			if(job.getId().toString().equals(id.toString())) {
    				jobById = job;
    				Long jobRealID = jobById.getId();
    				List<IApplicant>hiredList = appService.loadHiredDateByJobID(jobRealID);
    				if(hiredList.isEmpty()){
    					d+= "Nobody hired for this job yet";
    					add(new Label("chart", ""));
    				}
    				
    				else{
    					Date jobDate = job.getDate();
    					List<String> people = new ArrayList<String>();
    					List<Number>peopleDays = new ArrayList<Number>();
    					
    					for(int j=0;j<hiredList.size();j++){ 
    						
    	    				logger.info("date of job: " + jobDate);
    	    				logger.info("date of hired: " + hiredList.get(j).getHired());
    	    				Long applicantId = hiredList.get(j).getHired();
    	    				Person pHired = (Person) hiredList.get(j);
    	    				
    	    				
    	    				days = Days.daysBetween(new DateMidnight(jobDate), 
    	    						new DateMidnight(hiredList.get(j).getHiredDate())).getDays();
    	    				averageHiring += days;
    	    				d += String.valueOf(days) + ", ";
    	    				hiredID = hiredList.get(j).getHired();
    	    				
    	    				applicantName = pHired.getFirstName() + " " + pHired.getLastName();
    	    				people.add(applicantName);
    	    				peopleDays.add(days);
    	    			
    	    				
    	    			}
    					averageHiring = averageHiring/hiredList.size();
    					people.add("average");
    					peopleDays.add(averageHiring);
    	    			hiredAmount += "You have hired: ";
    	    			hiredAmount += String.valueOf(hiredList.size()) + " people";
    	    			
    	    			avg = "Average time to hire: " + String.valueOf(averageHiring);
    	    			singleDays += "The amount of days to hire per applicant was:";
    	    			
    	    			Options options = new Options();
    	    			
    	    	    	options.setChartOptions(new ChartOptions()
    	    	        .setType(SeriesType.BAR));
    	    	 
    	    	    options.setGlobal(new Global()
    	    	        .setUseUTC(Boolean.TRUE));
    	    	 
    	    	    options.setTitle(new Title("Hiring statistics"));
    	    	 
    	    	    options.setSubtitle(new Title("Click Match Hire"));
    	    	 
    	    	    options.setxAxis(new Axis()
    	    	        .setCategories(people)
    	    	        .setTitle(new Title(null)));
    	    	 
    	    	    options.setyAxis(new Axis()
    	    	        .setTitle(
    	    	            new Title("Time to hire (days)")
    	    	                .setAlign(HorizontalAlignment.HIGH))
    	    	        .setLabels(new Labels().setOverflow(Overflow.JUSTIFY)));
    	    	 
    	    	    options.setTooltip(new Tooltip()
    	    	        .setFormatter(new Function(
    	    	            "return ''+this.series.name +': '+ this.y +' millions';")));
    	    	 
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
    	    	 
    	    	    options.setCredits(new CreditOptions()
    	    	        .setEnabled(Boolean.FALSE));
    	    	 
    	    	    options.addSeries(new SimpleSeries()
    	    	        .setName("People")
    	    	        .setData(peopleDays));
    	    	    
    	    	    /*options.addSeries(new SimpleSeries()
    	    	        .setName("Year 1900")
    	    	        .setData(133, 156, 947, 408, 6));
    	    	 
    	    	    options. addSeries(new SimpleSeries()
    	    	        .setName("Year 2008")
    	    	        .setData(973, 914, 4054, 732, 34));*/
    				
    	    	    add(new Chart("chart", options));
    	    	    
    				}
    				singleDays +=d;
    			
    			}
    		}
    		if(jobById == null) {
    			setResponsePage(NotFound.class);
    			return;
    		}
    		
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");  
        	title = jobById.getJobTitle();
        	IEmployer emp = jobById.getEmployer();
        	companyName = emp.getCompanyname();
        	adress = ((IPerson) emp).getAddress().getZipCode() + " " + ((IPerson) emp).getAddress().getCity();
        	adress2 = ((IPerson) emp).getAddress().getStreet();
        	personN = " " + ((IPerson) emp).getFirstName() + " " + ((IPerson) emp).getLastName();
        	tel = " " + emp.getContact();
        	mail = " " + ((IPerson) emp).getEmail();
    		content="<tr><td width=\"130\"><b>Description:</b></td><td colspan=\"2\">"+StringUtil.stringWithNewline(jobById.getMainPurpose())+"</td></tr><tr><td height=\"30\"></td></tr><tr><td width=\"130\"><b>Qualification:</b></td><td>"+jobById.getQualification()+"</td></tr>";
    		
    		add = "<tr><td colspan=\"2\"><br><br><div id=\"small\">This job was added on "+format.format(jobById.getDate())+"</div></td></tr>";
    	
    	} catch(Exception e) {
    		setResponsePage(NotFound.class);
    	}
    	
    	add(new Label("personn", personN));
    	add(new Label("tel", tel));
    	add(new Label("mail", mail));
    	add(new Label("adress", adress));
    	add(new Label("adress2", adress2));
    	add(new Label("company", companyName));
    	
    	add(new Label("numberHired", hiredAmount));
		add(new Label("average", avg));
		add(new Label("days", singleDays));
		
    	add(new Label("title",title));
    	add(new Label("jobContent", content).setEscapeModelStrings(false));
    	add(new Label("add", add).setEscapeModelStrings(false));
    	String backButton="<input type=\"button\" onclick=\"window.history.back();\" value=\"Back\" class='button_example'/>";
    	
    	add(new Label("back", backButton).setEscapeModelStrings(false));
    	
    	
    	
    }

}