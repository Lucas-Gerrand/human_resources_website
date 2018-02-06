package hrrss.service.impl;

import hrrss.dao.impl.CVDAO;
import hrrss.dao.impl.CVEducationDAO;
import hrrss.dao.impl.CVExperienceDAO;
import hrrss.dao.impl.CVSkillDAO;
import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.impl.CV;
import hrrss.model.impl.CVEducation;
import hrrss.model.impl.CVExperience;
import hrrss.model.impl.CVSkill;
import hrrss.service.ICVService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CVService implements ICVService {

    private CVDAO CVDao;

    private CVSkillDAO skillDAO;

    private CVExperienceDAO experienceDAO;

    private CVEducationDAO educationDAO;

    public CVDAO getCVDao() {
	return CVDao;
    }

    public void setCVDao(CVDAO cVDao) {
	CVDao = cVDao;
    }

    public CVSkillDAO getSkillDAO() {
	return skillDAO;
    }

    public void setSkillDAO(CVSkillDAO skillDAO) {
	this.skillDAO = skillDAO;
    }

    public CVExperienceDAO getExperienceDAO() {
	return experienceDAO;
    }

    public void setExperienceDAO(CVExperienceDAO experienceDAO) {
	this.experienceDAO = experienceDAO;
    }

    public CVEducationDAO getEducationDAO() {
	return educationDAO;
    }

    public void setEducationDAO(CVEducationDAO educationDAO) {
	this.educationDAO = educationDAO;
    }

    public void saveCV(ICV entity) {
	//entity.setCreated(new Date());
	CVDao.save((CV) entity);
    }

    public void updateCV(ICV entity) {
	CVDao.update((CV) entity);
    }

    public ICV findCV(Long id) {
	return (ICV) CVDao.findOne(id);
    }

    public void deleteCVById(Long id) {
	CVDao.setClazz(CV.class);
	CVDao.deleteById(id);
    }

    public List<ICV> findAllCV() {
	CVDao.setClazz(CV.class);
	List<ICV> CVs = new ArrayList<ICV>();
	for (CV cv : CVDao.findAll()) {
	    CVs.add(cv);
	}
	return CVs;
    }

    public void deleteCV(ICV entity) {
	CVDao.delete((CV) entity);
    }

    @Override
    public List<ICV> loadCVByUsername(String username) {
	return CVDao.loadCVByUsername(username);
    }

    @Override
    public void deleteCVSkillById(Long id) {
	skillDAO.setClazz(CVSkill.class);
	skillDAO.deleteById(id);

    }

    @Override
    public void deleteCVSkill(ICVSkill entity) {
	skillDAO.delete((CVSkill) entity);
    }

    @Override
    public void deleteCVExperienceById(Long id) {
	experienceDAO.setClazz(CVExperience.class);
	experienceDAO.deleteById(id);
    }

    @Override
    public void deleteCVExperience(ICVExperience entity) {
	experienceDAO.delete((CVExperience) entity);
    }

    @Override
    public void deleteCVEducation(Long id) {
	educationDAO.setClazz(CVEducation.class);
	educationDAO.deleteById(id);
    }

    @Override
    public void deleteCVEducation(ICVEducation entity) {
	educationDAO.delete((CVEducation) entity);
    }

    @Override
    public List<ICV> loadCVById(Long id) {
	return CVDao.loadCVById(id);

    }

    @Override
    public void saveCVSkill(ICVSkill skill) {
	skillDAO.save((CVSkill) skill);

    }

    @Override
    public void saveCVExperience(ICVExperience experience) {
	experienceDAO.save((CVExperience) experience);
    }

    @Override
    public void saveCVEducation(ICVEducation education) {
	educationDAO.save((CVEducation) education);

    }
}