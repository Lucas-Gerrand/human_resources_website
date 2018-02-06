package hrrss.dao.impl;

import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.impl.CVExperience;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class CVExperienceDAO extends AbstractDAO<CVExperience> {

    @SuppressWarnings("unchecked")
    public List<ICV> loadCVByUsername(String username) {

	List<ICV> listCVs = (List<ICV>) getCurrentSession()
		.getNamedQuery("loadCVByUsername")
		.setParameter("username", username).list();

	int i = 0;
	for (ICV cv : listCVs) {

	    listCVs.get(i).setSkills(loadCVSkillsByCV(cv.getId()));
	    listCVs.get(i).setEducations(loadCVEducationsByCV(cv.getId()));
	    listCVs.get(i).setExperiences(loadCVExperiencesByCV(cv.getId()));

	    i++;
	}

	return listCVs;
    }

    @SuppressWarnings("unchecked")
    public List<ICVSkill> loadCVSkillsByCV(long id) {
	return (List<ICVSkill>) getCurrentSession()
		.getNamedQuery("loadCVSkillsByCV").setParameter("id", id)
		.list();
    }

    @SuppressWarnings("unchecked")
    public List<ICVEducation> loadCVEducationsByCV(long id) {
	return (List<ICVEducation>) getCurrentSession()
		.getNamedQuery("loadCVEducationsByCV").setParameter("id", id)
		.list();

    }

    @SuppressWarnings("unchecked")
    public List<ICVExperience> loadCVExperiencesByCV(long id) {
	return (List<ICVExperience>) getCurrentSession()
		.getNamedQuery("loadCVExperiencesByCV").setParameter("id", id)
		.list();

    }
}
