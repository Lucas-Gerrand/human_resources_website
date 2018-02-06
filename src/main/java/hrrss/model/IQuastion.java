package hrrss.model;

import java.io.File;
import java.util.List;

/**
 * Class represents a model of the question. 
 * 
 * @author Irina
 * 
 */
public interface IQuastion {
	
	public Long getId();

    public void setId(Long id);

    public ISurvey getSurvay();

    public void setSurvey(ISurvey survey);
    
    public String getQuastion();
    
    public void setQuastion(String quastion);
    
    public String getAnswer();
    
    public void setAnswer(String answer);
    
    public String getTypeOfQuastion();
    
    public void setTypeOfQuastion(String qType);
    
    public String getAdditionalInformation();
    
    public void setAdditionalInformation(String addInf);
    
    public File getFile();
    
    public void setFile(File file);
    
    public List<IApplicantAnswer> getApplicantAnswer();
    
    public void setApplicantAnswer(List<IApplicantAnswer> applicantAnswer);
    
    public void addSubA(ISubAnswer subanswer);
    
    public void deleteSubA(ISubAnswer subanswer);
    
    public void addAplAns(IApplicantAnswer applAns);
    
    public void deleteAplAns(IApplicantAnswer applAns);
    
    public void setNumber(int number);
    
    public int getNumber();
}
