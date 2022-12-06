package com.soprasteria.springbatchexcelfileupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprasteria.springbatchexcelfileupload.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
