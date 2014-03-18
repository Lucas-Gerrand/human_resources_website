package hrrss.model;

import java.util.Date;

public interface IApplicantToSurvey {
	
	public Long getId();
	
	public void setId(Long id);
	
	public IApplicant getApplicant();
	
	public void setApplicant(IApplicant applicant);
	
//	public void addApplicant(IApplicant applicant);
//	
//	public void deleteApplicant(IApplicant applicant);
//	
	public ISurvey getServey();
    
    public void setServeys(ISurvey survey);
    
//    public void addSurvey(ISurvey survey);
//    
//    public void deleteSurvey(ISurvey survey);
//    
    public boolean getVis();
    
    public void setVis(boolean vis);
    
    public boolean getNewq();
    
    public void setNewq(boolean newq);
    
    public boolean getNewqa();
    
    public void setNewqa(boolean newqa);
    
    public Date getDate();
    
    public void setDate(Date date);
    
    public void setComment(String comment);
    
    public String getComment();
    
    public boolean getVisapp();
    
	public void setVisapp(boolean visapp);

}
