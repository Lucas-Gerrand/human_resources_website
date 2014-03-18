package hrrss.model;

import java.util.List;

public interface IEmployer {

    public List<ISurvey> getSurveys();

    public void setSurveys(List<ISurvey> surveys);

    public List<IJobDescription> getJobDescriptions();

    public void setJobDescriptions(List<IJobDescription> jobDescriptions);
    
    public String getCompanyname();

  	public void setCompanyname(String companyname);
  	
  	public String getContact();
  	
  	public void setContact(String contact);
}
