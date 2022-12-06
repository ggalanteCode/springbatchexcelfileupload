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
import com.soprasteria.springbatchexcelfileupload.utils.EmailUtils;

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
	private EmailUtils emailUtils;

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("!!! JOB FINISHED! Time to verify the results");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.error("!!! JOB FAILED...");
			emailUtils.send("Job Error: FAILED", "EXCEL FILE UPLOAD: The following exceptions occurred: "
					+ jobExecution.getAllFailureExceptions().toString());
		}
	}

}
