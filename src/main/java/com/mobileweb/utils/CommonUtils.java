package com.mobileweb.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CommonUtils {
	public static boolean isEmptyString(String s) {
		if (null == s || "".equals(s.trim()))
			return true;
		return false;
	}

	public static void sendMail(String user, String receipt, String url) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("david227000", "Abc123!!");
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("david227000@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipt));
			message.setSubject("Confirmation email from Mobile Web Application");
			String content = String.format("Dear %s \n Thanks for your regist to our site. \n Please enter below url for confirmation \n %s", user, url);
			message.setText(content);
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
