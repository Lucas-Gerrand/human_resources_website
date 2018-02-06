package hrrss.model;

import java.util.Date;

public interface IJobDescription {

    public IEmployer getEmployer();

    public void setEmployer(IEmployer employer);

    public String getJobTitle();

    public void setJobTitle(String jobTitle);

    public String getMainPurpose();

    public void setMainPurpose(String mainPurpose);

    public String getQualification();

    public void setQualification(String qualification);
    
    public Date getDate();

    public void setDate(Date date);
    
    public String getAll();

    
    public Long getId();

	public void setId(Long id);
    
}
