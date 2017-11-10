package com.edu118.bookstore.user.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String from ,String to, String subject, String emailMsg)
			throws AddressException, MessagingException {
		
		Properties props = new Properties();
		props.setProperty("mail.host", "localhost");
		props.setProperty("mail.smtp.auth", "true");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("blueDaisy", "123");
			}
		};

		Session session = Session.getInstance(props, auth);

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(from)); 

		message.setRecipient(RecipientType.TO, new InternetAddress(to)); 

		message.setSubject(subject);

		message.setContent(emailMsg, "text/html;charset=utf-8");

		Transport.send(message);
	}
	
}

