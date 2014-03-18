package hrrss.ui.questionnaireform;

import hrrss.model.IApplicant;
import hrrss.model.IApplicantAnswer;
import hrrss.model.IPerson;
import hrrss.model.IQuastion;
import hrrss.model.impl.ModelFactory;
import hrrss.service.impl.ApplicantAnswerService;
import hrrss.service.impl.SurveyService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * fragment for attach questions. The applicant can upload a file into the DB
 * and download the uploaded file The employer can download this file
 * 
 * @author Irina
 * 
 */
@SuppressWarnings("serial")
public class FragmentForAttachQuestion extends Fragment {
	@SpringBean
	ApplicantAnswerService as;

	@SpringBean
	SurveyService ss;

	final Logger logger = LoggerFactory
			.getLogger(FragmentForAttachQuestion.class);

	public FragmentForAttachQuestion(String id, String markupId,
			MarkupContainer markupProvider, Long qId, Long appId, boolean e,
			boolean showUploaDField) {
		super(id, markupId, markupProvider);

		final ModelFactory mf = new ModelFactory();
		final IApplicant applicant = mf.createApplicant();
		((IPerson) applicant).setId(appId);
		final IQuastion quastion = mf.createQuastion();
		quastion.setId(qId);

		final List<IApplicantAnswer> appl = as.findByQuestion(qId, appId);
		logger.info("LOAD applicants size: " + appl.size());
		String text = "";

		final FileUploadField fField = new FileUploadField("fileUpload");

		if (appl.size() != 0) {
			if (appl.get(0).getApplicantFile() == null) {
				text = "No file";
				logger.info("No file");
			} else {
				text = " ";
			}

		} else if (appl.size() == 0) {
			text = " ";
		}

		Label fileLabel = new Label("file", Model.of(text));
		add(fileLabel);

		add(fField);
		

		final MessageDialog warningDialogInfo = new MessageDialog("infoDialog",
				"Info", "File uploaded!", DialogButtons.OK, DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {

			}
		};

		add(warningDialogInfo);

		final MessageDialog warningDialogDelete = new MessageDialog(
				"infoDialogDelete", "Info", "File deleted!", DialogButtons.OK,
				DialogIcon.INFO) {
			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button) {
			}
		};

		add(warningDialogDelete);

		add(new Button("submit") {

			@Override
			public void onSubmit() {
				FileUpload fileUpload = fField.getFileUpload();

				try {
					File file = fileUpload.writeToTempFile();
					System.out.println("Name :"
							+ fileUpload.getClientFileName());
					if (appl.size() == 0) {
						IApplicantAnswer applA = mf.createApplicantAnswer();
						applA.setApplicant(applicant);
						applA.setQuestion(quastion);
						applA.setApplicantFile(file);
						applA.setFileName(fileUpload.getClientFileName());
						as.save(applA);
					} else if (appl.size() != 0) {
						appl.get(0).setApplicantFile(file);
						appl.get(0).setFileName(fileUpload.getClientFileName());
						as.update(appl.get(0));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				logger.info("File saved");

			}

		});

		add(new Button("del") {

			@Override
			public void onSubmit() {
				if (appl.size() != 0) {
					appl.get(0).setApplicantFile(null);
					appl.get(0).setFileName("");
					as.update(appl.get(0));
					logger.info("File deleted");
				}

			}

		});

		File file = new File(text);

		String name = "";
		String linkText = "";

		if (appl.size() != 0 && appl.get(0).getApplicantFile() != null
				&& e == false) {
			name = appl.get(0).getFileName();
			logger.info("File name: " + name);
			file = appl.get(0).getApplicantFile();
			linkText = "downloud file";
		}

		if (showUploaDField == false) {
			fField.setVisible(false);
		}

		Label fileNameLabel = new Label("filename", Model.of(name));
		add(fileNameLabel);

		DownloadLink d = new DownloadLink("link", file, name);
		Label l = new Label("linktext", Model.of(linkText));
		d.add(l);
		add(d);

	}
}
