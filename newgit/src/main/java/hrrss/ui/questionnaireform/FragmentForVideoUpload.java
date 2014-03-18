package hrrss.ui.questionnaireform;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.SurveyService;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.youtube.YouTube;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

public class FragmentForVideoUpload extends Fragment{
	@SpringBean
	ApplicantAnswerService as;

	@SpringBean
	SurveyService ss;
	
	final Logger logger = LoggerFactory.getLogger(FragmentForOpenQuestion.class);

	public FragmentForVideoUpload(String id, String markupId,
			MarkupContainer markupProvider, Long qId, Long appId, boolean appl, boolean details, boolean seeAnswer) {
		super(id, markupId, markupProvider); 
		String VideoId = "";
		String out = "";
		
		
		final ModelFactory mf = new ModelFactory();
		final IApplicant applicant = mf.createApplicant();
		((IPerson) applicant).setId(appId);
		final IQuastion quastion = mf.createQuastion();
		quastion.setId(qId);

		/*
		 * a variable for the Model of the TextArea, is needed to put the answer
		 * in the TextArea
		 */
		String text = null;
		

		@SuppressWarnings("serial")
		final MessageDialog warningDialogInfo = new MessageDialog(
				"infoDialog", "Info",
				"Answer saved!",
				DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
			}
		};
		
		add(warningDialogInfo);

		/*
		 * find the answers of an applicant, return list of the answers
		 */
		final List<IApplicantAnswer> appl1 = as.findByQuestion(qId, appId);

		logger.info("SIZE: " + appl1.size());

		/*
		 * put text into the Model -> -> into the TextArea
		 */
		if (appl1.size() != 0 && appl) {
			VideoId = appl1.get(0).getAnswer();
			text=appl1.get(0).getAnswer();
			out += "<iframe id="
					+ "\"ytplayer\" type=\"text/html\" width=\"640\" height=\"390\"  src=\"http://www.youtube.com/embed/"
					+ VideoId
					+ "?autoplay=1&origin=http://example.com\" frameborder=\"0\"/>";
		} else if (appl1.size() == 0) {
			VideoId = "";
		}
		if (seeAnswer) {
			VideoId = appl1.get(0).getAnswer();
			out += "<iframe id="
					+ "\"ytplayer\" type=\"text/html\" width=\"640\" height=\"390\"  src=\"http://www.youtube.com/embed/"
					+ VideoId
					+ "?autoplay=1&origin=http://example.com\" frameborder=\"0\"/>";
		}
		add(new Label("content", out).setEscapeModelStrings(false));

		/*
		 * create the new Model
		 */
		IModel<String> textModel = Model.of(text);
		logger.info(text);
		final TextField<String> q = new TextField<String>("answer", textModel);
		
		add(q);
		

		/**
		 * save/update the answer
		 */
		add(new Button("submit") {

			@Override
			public void onSubmit() {

				if (appl1.size() == 0) {
					IApplicantAnswer applA = mf.createApplicantAnswer();
					applA.setApplicant(applicant);
					applA.setQuestion(quastion);
					applA.setAnswer(q.getValue());
					as.save(applA);
				} else if (appl1.size() != 0) {
					appl1.get(0).setAnswer(q.getValue());
					as.update(appl1.get(0));
					
				}
				
				//warningDialogInfo.open(target);
			}

		});

				
		
//		YouTubeService service = new YouTubeService("ir.pershina@gmail.com", "07032003");
//		//service.setUserCredentials("ir.pershina@gmail.com", "password");
//		
//		VideoEntry newEntry = new VideoEntry();
//
//		YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
//		
//		mg.setTitle(new MediaTitle());
//		mg.getTitle().setPlainTextContent("My Test Movie");
//		mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));
//		mg.setKeywords(new MediaKeywords());
//		mg.getKeywords().addKeyword("cars");
//		mg.getKeywords().addKeyword("funny");
//		mg.setDescription(new MediaDescription());
//		mg.getDescription().setPlainTextContent("My description");
//		mg.setPrivate(false);
//		mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
//		mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));
//
//		//newEntry.setGeoCoordinates(new GeoRssWhere(37.0,-122.0));
//		// alternatively, one could specify just a descriptive string
//		 newEntry.setLocation("Mountain View, CA");
//
//		try {
//			URL uploadUrl = new URL("http://gdata.youtube.com/action/GetUploadToken");
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	//	FormUploadToken token = service.getFormUploadToken(uploadUrl, newEntry);
//
////		System.out.println(token.getUrl());
////		System.out.println(token.getToken());
		
		
		
		
			}
}
