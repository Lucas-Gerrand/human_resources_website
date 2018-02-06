package hrrss.ui.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public static void sendEmail(String subject, String message, String to) throws Exception {
		String from = null, sub = null, msg = null;
		String host = "smtp.gmail.com", username = "clickmatchhire@gmail.com", password = "click12345";
		Session session = null;
		MimeMessage email = null;
		Transport transport = null;

		sub = subject;
		msg = message;

		Properties props = System.getProperties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", host);
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"clickmatchhire@gmail.com", "click12345");
					}
				});
		session.setDebug(false);

		email = new MimeMessage(session);
	
		email.setSender(new InternetAddress(username));
		email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		email.setSubject(sub);
		email.setContent(msg, "text/plain");
		Transport.send(email);
		
	}
}
