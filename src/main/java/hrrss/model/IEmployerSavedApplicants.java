package hrrss.model;

import java.util.List;

public interface IEmployerSavedApplicants {
	
	public void setApplicantID(Long applicantID);
  	
  	public Long getApplicantID();
  	
  	public void setSaveId(Long id);
  	
	public Long getMessageId();
	
	public IPerson getPerson();

	public void setPerson(IPerson person);
	
	public void setComment(String comment);
	
	public String getComment();
}
