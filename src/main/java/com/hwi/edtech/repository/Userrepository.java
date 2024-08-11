package com.hwi.edtech.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hwi.edtech.entity.User;

public interface Userrepository extends CrudRepository<User,Integer>{

	public   Optional<User> findByEmail(String email);

}
