package com.soprasteria.springbatchexcelfileupload.utils.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.soprasteria.springbatchexcelfileupload.utils.EmailUtils;

@Component
public class EmailUtilsImpl implements EmailUtils {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void send(String subject, String body) {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);// true indicates multipart message
			helper.setFrom("3968d203-2bc1-4749-98ba-058213238d4d@mailslurp.mx");
			helper.setSubject(subject);
			helper.setTo("3968d203-2bc1-4749-98ba-058213238d4d@mailslurp.mx");
			helper.setText(body, true); // true indicates body is html
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
