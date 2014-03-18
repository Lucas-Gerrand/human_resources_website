package hrrss.model;

import java.util.Date;
import java.util.List;

public interface IApplicant {

    public List<ICV> getCVs();

    public void setCVs(List<ICV> cVs);

    public void addCV(ICV cv);
    
    public List<IApplicantAnswer> getAnswers();
    
    public void setAnswers(List<IApplicantAnswer> answers);
    
    public void addAnswer(IApplicantAnswer answer);
    
    public void deleteAnswer(IApplicantAnswer answer);
    
    public List<IApplicantToSurvey> getApplicantToSurveys();
    
    public void setApplicantToSurveys(List<IApplicantToSurvey> applicantToSurveys);
    
    public void setHired(Long hired_id);
    
    public Long getHired();
    
    public Date getHiredDate();
    
    public void setHiredDate(Date hired_date);
    
//    public List<ISurvey> getServeys();
//    
//    public void setServeys(List<ISurvey> surveys);
//    
//    public void addSurvey(ISurvey survey);
//    
//    public void deleteSurvey(ISurvey survey);
}
