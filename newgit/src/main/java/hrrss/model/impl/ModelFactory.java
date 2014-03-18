package hrrss.model.impl;

import hrrss.model.IAddress;
import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IApplicantToSurvey;
import hrrss.model.ICV;
import hrrss.model.ICVEducation;
import hrrss.model.ICVExperience;
import hrrss.model.ICVSkill;
import hrrss.model.IEmployer;
import hrrss.model.IEmployerSavedApplicants;
import hrrss.model.IJobDescription;
import hrrss.model.IMediaAudio;
import hrrss.model.IMediaFile;
import hrrss.model.IMediaVideo;
import hrrss.model.IMessaging;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.IReply;
import hrrss.model.ISurvey;
import hrrss.ui.util.PasswordUtil;

import java.io.Serializable;
import java.util.Date;

import antlr.collections.List;

public class ModelFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    public IAddress createAddress() {
	return new Address();
    }

    public IApplicant createApplicant() {
	return new Applicant();
    }

    public IEmployer createEmployer() {
	return new Employer();
    }
    
    public IPerson createPerson() {
    	return new Person();
    }

    public ICV createCV() {
	return new CV();
    }

    public ICVEducation createCVEducation() {
	return new CVEducation();
    }

    public ICVExperience createCVExperience() {
	return new CVExperience();
    }

    public ICVSkill createCVSkill() {
	return new CVSkill();
    }

    public IJobDescription createJobDescription() {
	return new JobDescription();
    }


    public IMediaFile createMediaFile() {
	return new MediaFile();
    }

    public IMediaAudio createMediaAudio() {
	return new MediaAudio();
    }

    public IMediaVideo createMediaVideo() {
	return new MediaVideo();
    }
    
    public ISurvey createSurvey() {
    	return new Survey();
    }
    
    public IQuastion createQuastion() {
    	return new Quastion();
    }
    
    public IApplicantAnswer createApplicantAnswer(){
    	return new ApplicantAnswer();
    }
    public IMessaging createMessage(){
    	return new Messaging();
    }
    public IReply createReply(){
    	return new Reply();
    }
    
    public IEmployerSavedApplicants createSavedApplicants(){
    	return new SavedApplicants();
    }
    public IApplicantToSurvey createApplicantToSurvey(){
    	return new ApplicantToSurvey();
    }
    
    public Employer createE(String companyname, String contact, String homepage, String activation, String city, String street, String zip, String birthday, String email, String firstname, String lastname, String gender, String pw, byte[] pic) {
    	Employer e = new Employer();
    	e.setActivation(activation);
    	Address aAddr = new Address();
    	aAddr.setCity(city);
    	aAddr.setStreet(street);
    	aAddr.setZipCode(zip);
    	e.setAddress(aAddr);
    	e.setBirthday(birthday);
    	e.setEmail(email);
    	e.setFirstName(firstname);
    	e.setLastName(lastname);
    	e.setGender(gender);
    	e.setPassword(pw);
    	//e.setStatus(status);
    	e.setPic(pic);

    	e.setCompanyname(companyname);
    	e.setContact(contact);
    	e.setHomepage(homepage);
    
    	return e;
    }
    
    public Applicant createA(String activation, String city, String street, String zip, String birthday, String email, String firstname, String lastname, String gender, String pw, String status, byte[] pic) {
    	Applicant a = new Applicant();
    	
    	a.setActivation(activation);
    	Address aAddr = new Address();
    	aAddr.setCity(city);
    	aAddr.setStreet(street);
    	aAddr.setZipCode(zip);
    	a.setAddress(aAddr);
    	a.setBirthday(birthday);
    	a.setEmail(email);
    	a.setFirstName(firstname);
    	a.setLastName(lastname);
    	a.setGender(gender);
    	a.setPassword(pw);
    	a.setStatus(status);
    	a.setPic(pic);
    	
    	return a;
    }
    
    public CV createACV(Applicant a, String nationality, String pemail, String pwebsite) {
    	CV cv = new CV();
    	
    	cv.setApplicant(a);
    	cv.setNationality(nationality);
    	cv.setPersonalEmail(pemail);
    	cv.setPersonalWebsite(pwebsite);
    	cv.setEducations(null);
    	cv.setExperiences(null);
    	cv.setSkills(null);
    	
    	return cv;
    }

    public CVExperience createExp(CV cv, String start, String end, String title, String company, String description) {
    	CVExperience exp = new CVExperience();
    	exp.setCv(cv);
    	exp.setStart(start);
    	exp.setEnd(end);
    	exp.setTitle(title);
    	exp.setCompany(company);
    	exp.setDescription(description);
    	
    	return exp;
    }
    
    public CVEducation createEdu(CV cv, String start, String end, String location, String facility, String description) {
    	CVEducation edu = new CVEducation();
    	edu.setCv(cv);
    	edu.setStart(start);
    	edu.setEnd(end);
    	edu.setLocation(location);
    	edu.setFacility(facility);
    	edu.setDescription(description);
    	
    	return edu;
    }
    
    public CVSkill createSkill(CV cv, String skillType, String description) {
    	CVSkill skill = new CVSkill();
    	skill.setCv(cv);
    	skill.setSkillType(skillType);
    	skill.setDescription(description);
    	
    	return skill;
    }
    
    public Survey createSurvey(Employer employer, String name, String text){
    	Survey survey = new Survey();
    	survey.setDate(new Date());
    	survey.setEmployer(employer);
    	survey.setName(name);
    	survey.setText(text);
		return survey;
    }
    
    public Quastion createQuas(String q, int number){
    	Quastion question = new Quastion();
    	question.setQuastion(q);
    	question.setNumber(number);
		return question;
    }
    
    public SubAnswer createSubAnswer(Quastion quastion, String text){
    	SubAnswer sA = new SubAnswer();
    	sA.setQuestion(quastion);
    	sA.setSubAnswer(text);
		return sA;
    }
    
    public ApplicantToSurvey createApplToSurvey(Survey survey, Applicant applicant){
    	ApplicantToSurvey applicantToSurvey = new ApplicantToSurvey();
    	applicantToSurvey.setServeys(survey);
    	applicantToSurvey.setApplicant(applicant);
    	applicantToSurvey.setVisapp(true);
    	applicantToSurvey.setNewqa(true);
    	return applicantToSurvey;
    }
    
    public ApplicantAnswer createApAns(Applicant applicant, Quastion quastion){
    	ApplicantAnswer appAns = new ApplicantAnswer();
    	appAns.setApplicant(applicant);
    	appAns.setQuestion(quastion);
    	return appAns;
    }
    
}
