package com.soprasteria.springbatchexcelfileupload.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.soprasteria.springbatchexcelfileupload.listener.JobCompletionNotificationListener;
import com.soprasteria.springbatchexcelfileupload.model.User;
import com.soprasteria.springbatchexcelfileupload.reader.ExcelUserReader;
import com.soprasteria.springbatchexcelfileupload.writer.DBWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobExecutionListener listener;
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<User> reader,
			ItemWriter<User> writer) {
		Step step = stepBuilderFactory.get("Excelstep").<User, User>chunk(100)
				.reader(reader)
				.writer(writer)
				.build();
		return jobBuilderFactory.get("Exceljob")
				.listener(listener)
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}

	@Bean
	@StepScope
    public ExcelUserReader reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
        ExcelUserReader reader = new ExcelUserReader();
        reader.setPathToFile(pathToFile);
		return reader;
    }
	
	@Bean
	public DBWriter writer(DataSource dataSource) {
		return new DBWriter();
	}
	
	@Bean
	public JobOperator jobOperator(final JobLauncher jobLauncher, final JobRepository jobRepository,
	        final JobRegistry jobRegistry, final JobExplorer jobExplorer) {
	    final SimpleJobOperator jobOperator = new SimpleJobOperator();
	    jobOperator.setJobLauncher(jobLauncher);
	    jobOperator.setJobRepository(jobRepository);
	    jobOperator.setJobRegistry(jobRegistry);
	    jobOperator.setJobExplorer(jobExplorer);
	    return jobOperator;
	}

}
