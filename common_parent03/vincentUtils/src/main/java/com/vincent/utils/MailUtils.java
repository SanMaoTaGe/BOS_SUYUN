package com.vincent.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 邮件发送工具类
 * 
 * 1. javax.mail: 1.4.7+
 * 2. 邮箱配置文件(email.properties)放在src/main/resources下
 * 
 */
public class MailUtils {

	public static void sendMail(String subject, String content, String to) {
		Properties pps = new Properties();
		InputStream email = MailUtils.class.getResourceAsStream("/email.properties");
		if (null == email) {
			System.err.println("邮箱配置文件(email.properties)不存在!");
			return;
		}
		try {
			pps.load(email);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String address = pps.getProperty("address"); // yhmpc@163.com
		String password = pps.getProperty("password");
		String smtp_host = "smtp." + address.replaceAll(".*@(.*)", "$1"); //smtp.163.com

		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtp_host);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");

		if ("smtp.qq.com".equals(smtp_host)) {
			MailSSLSocketFactory sslFactory = null;
			try {
				sslFactory = new MailSSLSocketFactory();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			sslFactory.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.socketFactory", sslFactory);
			props.put("mail.smtp.ssl.enable", "true");
		}

		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(address));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, address, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}

}
