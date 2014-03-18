package hrrss.ui;

import hrrss.service.impl.PersonService;
import hrrss.ui.about.About;
import hrrss.ui.activation.Activation;
import hrrss.ui.applicant.Account;
import hrrss.ui.applicant.cv.Cv;
import hrrss.ui.applicant.cv.document.Download;
import hrrss.ui.applicant.cvcontact.CVContact;
import hrrss.ui.applicant.cveducation.CVEducation;
import hrrss.ui.applicant.cveducation.del.DelEducation;
import hrrss.ui.applicant.cveducation.edit.EditEducation;
import hrrss.ui.applicant.cvexperience.CVExperience;
import hrrss.ui.applicant.cvexperience.del.DelExperience;
import hrrss.ui.applicant.cvexperience.edit.EditExperience;
import hrrss.ui.applicant.cvskills.CVSkills;
import hrrss.ui.applicant.cvskills.del.DelSkill;
import hrrss.ui.applicant.cvskills.edit.EditSkill;
import hrrss.ui.applicant.edit.Edit;
import hrrss.ui.applicant.edit.email.EditEmail;
import hrrss.ui.applicant.edit.password.EditPassword;
import hrrss.ui.applicant.news.News;
import hrrss.ui.applicant.pictures.Pictures;
import hrrss.ui.applicant.search.AutomaticSearch;
import hrrss.ui.contact.HelpContact;
import hrrss.ui.employer.EmployerAccount;
import hrrss.ui.employer.applicantlist.applicantList;
import hrrss.ui.employer.job.JobManager;
import hrrss.ui.employer.job.del.DelJob;
import hrrss.ui.employer.job.edit.EditJob;
import hrrss.ui.error.Authentication;
import hrrss.ui.error.LoginFail;
import hrrss.ui.error.NotFound;
import hrrss.ui.error.PersonTyp;
import hrrss.ui.error.ProfilePageNotFound;
import hrrss.ui.forgetpw.Password;
import hrrss.ui.format.Format;
import hrrss.ui.home.Home;
import hrrss.ui.job.JobDetail;
import hrrss.ui.logout.Logout;
import hrrss.ui.message.CopyOfReply;
import hrrss.ui.message.Delete;
import hrrss.ui.message.Hire;
import hrrss.ui.message.Inbox;
import hrrss.ui.message.InboxDetail;
import hrrss.ui.message.Message;
import hrrss.ui.message.Reply;
import hrrss.ui.message.SaveApplicant;
import hrrss.ui.questionnaireform.AddSurveyForApplicant;
import hrrss.ui.questionnaireform.QuestionReceived;
import hrrss.ui.questionnaireform.SurveyPageEmployer;
import hrrss.ui.questionnairemaker.QuestionnaireEmployerReadAnswer;
import hrrss.ui.register.Register;
import hrrss.ui.searchJobs.SearchJobs;
import hrrss.ui.team.Team;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web applicati-on. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see hrrss.ui.layer.testModel.Start#main(String[])
 */
	
public class WicketApplication extends WebApplication {

    @SpringBean
    private PersonService personService;
    /**
     * @see org.apache.wicket.Application#getHomePage()	
     */

    @Override
    public Class<? extends WebPage> getHomePage() {	
	
	return Home.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */	
    @Override

    public void init() {
	super.init();
	
	mountPage("/home/", Home.class);
	mountPage("/about/", About.class);
	mountPage("/contact/", HelpContact.class);
	mountPage("/team/", Team.class);
	mountPage("/searchjobs/", SearchJobs.class);
	mountPage("/register/", Register.class);
	mountPage("/forgetpw/", Password.class);	
	mountPage("/activation/", Activation.class);
	mountPage("/logout/", Logout.class);
	mountPage("/job/${id}/", JobDetail.class);
	
	
	//basic applicant stuff
	mountPage("/applicant/news/", News.class);	
	mountPage("/applicant/profile/${id}", Account.class);
	//CV
	mountPage("/applicant/cv/${id}", Cv.class);
	mountPage("/applicant/cvcontact/", CVContact.class);
	mountPage("/applicant/cveducation/", CVEducation.class);
	mountPage("/applicant/cveducation/del/${id}/", DelEducation.class);
	mountPage("/applicant/cveducation/edit/${id}/", EditEducation.class);
	mountPage("/applicant/cvskills/", CVSkills.class);
	mountPage("/applicant/cvskills/del/${id}", DelSkill.class);
	mountPage("/applicant/cvskills/edit/${id}", EditSkill.class);
	mountPage("/applicant/cvexperience/", CVExperience.class);
	mountPage("/applicant/cvexperience/del/${id}", DelExperience.class);
	mountPage("/applicant/cvexperience/edit/${id}", EditExperience.class);
	//download CV
	mountPage("/applicant/download/${id}", Download.class);
	//profile picture
	mountPage("/applicant/pictures/", Pictures.class);
	//applicant edit profile
	mountPage("/applicant/edit/", Edit.class);
	mountPage("/applicant/edit/email/", EditEmail.class);
	mountPage("/applicant/edit/password/", EditPassword.class);
	//automatic search applicant
	mountPage("/applicant/search/${id}", AutomaticSearch.class);
	
	
	//basic employer stuff
	mountPage("/employer/news/", News.class);	
	mountPage("/employer/profile/${id}", EmployerAccount.class);
	//employer edit
	mountPage("/employer/edit/", hrrss.ui.employer.edit.Edit.class);
	mountPage("/employer/edit/email/",hrrss.ui.employer.edit.email.EditEmail.class);
	mountPage("/employer/edit/password/",hrrss.ui.employer.edit.password.EditPassword.class);
	//employer search
	mountPage("/employer/search/${id}",	hrrss.ui.employer.search.Search.class);
	//employer jobdescription
	mountPage("/employer/job/", JobManager.class);
	mountPage("/employer/job/del/${id}/", DelJob.class);
	mountPage("/employer/job/edit/${id}/", EditJob.class);
	//employer picture
	mountPage("/employer/pictures/", hrrss.ui.employer.pictures.Pictures.class);
	
	mountPage("/message/saveapplicant/${id}", SaveApplicant.class);
	mountPage("/contacts/${id}", applicantList.class);
	
	//messenger
	
	mountPage("/message/messages/${id}", Message.class);	
	mountPage("/message/messagedetail/${id}", InboxDetail.class);
	mountPage("/message/reply/${id}", Reply.class);
	mountPage("/message/replyNew/${id}", CopyOfReply.class);
	mountPage("/message/inbox/${id}", Inbox.class);
	mountPage("/message/del/${id}", Delete.class);
	
	
	mountPage("/format/", Format.class);

	
	//error pages
	mountPage("/error/login/", LoginFail.class);
	mountPage("/error/authentication/", Authentication.class);
	mountPage("/error/persontyp/", PersonTyp.class);
	mountPage("/error/profilenotfound/", ProfilePageNotFound.class);
	mountPage("/error/activation/", hrrss.ui.error.Activation.class);
	mountPage("/error/pagenotfound/", NotFound.class);
	
	// mountPage("/questionnaire/", Questionnaire.class);
	mountPage("/message/hire/${id}", Hire.class);
	mountPage("/message/questionnaire/${id}", AddSurveyForApplicant.class);
	mountPage("/questionslist/${id}", QuestionReceived.class);
	mountPage("/questionnaire/", SurveyPageEmployer.class);
	mountPage("/answers/", QuestionnaireEmployerReadAnswer.class);

	getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	
	// add your configuration here
	
    }

}
