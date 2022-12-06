package com.soprasteria.springbatchexcelfileupload.listener;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.soprasteria.springbatchexcelfileupload.repo.UserRepo;

/*
 * https://savagerose.org/it/java-batch-tutorial-italiano/
 * classe per la notifica dei Job eseguiti con successo, in particolare
 * se un Job va a buon fine viene stampato a video
 * che il Job è stato completato; inoltre, la stringa "Found a new user in the 
 * database." per ogni singolo nuovo User inserito nel database.
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("!!! JOB FINISHED! Time to verify the results");
			userRepo.findAll().forEach(phase -> System.out.println("Found a new user in the database."));
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.error("!!! JOB FAILED...");
			MimeMessage message = javaMailSender.createMimeMessage();
			
	        try {
	        	MimeMessageHelper helper = new MimeMessageHelper(message, true);//true indicates multipart message
		        helper.setFrom("3968d203-2bc1-4749-98ba-058213238d4d@mailslurp.mx");
		        helper.setSubject("Job Error: FAILED");
		        helper.setTo("3968d203-2bc1-4749-98ba-058213238d4d@mailslurp.mx");
				helper.setText("EXCEL FILE UPLOAD: The following exceptions occurred: " + jobExecution.getAllFailureExceptions().toString(), true); //true indicates body is html
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        javaMailSender.send(message);
		}
	}

}
