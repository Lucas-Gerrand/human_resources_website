package hrrss.model;

import java.util.Date;
import java.util.List;

public interface ICV {

    public Long getId();

    public void setId(Long id);

    public List<ICVEducation> getEducations();

    public void setEducations(List<ICVEducation> educations);

    public void addEducation(ICVEducation education);

    public List<ICVSkill> getSkills();

    public void setSkills(List<ICVSkill> skills);

    public void addSkill(ICVSkill skill);

    public List<ICVExperience> getExperiences();

    public void setExperiences(List<ICVExperience> experiences);

    public void addExperience(ICVExperience experience);

    public String getPersonalEmail();

    public void setPersonalEmail(String personalEmail);

    public String getPersonalWebsite();

    public void setPersonalWebsite(String personalWebsite);

    public String getNationality();

    public void setNationality(String nationality);

    public IApplicant getApplicant();

    public void setApplicant(IApplicant applicant);

    public Date getCreated();

    public void setCreated(Date created);
}
