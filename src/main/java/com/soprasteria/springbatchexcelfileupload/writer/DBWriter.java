package com.soprasteria.springbatchexcelfileupload.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.soprasteria.springbatchexcelfileupload.model.User;
import com.soprasteria.springbatchexcelfileupload.repo.UserRepo;

public class DBWriter implements ItemWriter<User> {

	@Autowired
	private UserRepo userRepo;

	@Override
	public void write(List<? extends User> items) throws Exception {
		// TODO Auto-generated method stub
		userRepo.saveAll(items);
	}

}
