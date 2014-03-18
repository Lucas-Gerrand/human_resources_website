package hrrss.service;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;

import java.util.List;

public interface ICVService {

    public List<ICV> loadCVByUsername(String username);

    public List<ICV> loadCVById(Long id);

    public void saveCV(ICV entity);

    public void saveCVSkill(ICVSkill skill);

    public void saveCVExperience(ICVExperience experience);

    public void saveCVEducation(ICVEducation education);

    public void updateCV(ICV entity);

    public ICV findCV(Long id);

    public void deleteCVById(Long id);

    public void deleteCV(ICV entity);

    public void deleteCVSkillById(Long id);

    public void deleteCVSkill(ICVSkill entity);

    public void deleteCVExperienceById(Long id);

    public void deleteCVExperience(ICVExperience entity);

    public void deleteCVEducation(Long id);

    public void deleteCVEducation(ICVEducation entity);

}
