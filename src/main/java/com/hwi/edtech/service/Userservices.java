package com.hwi.edtech.service;

import java.util.List;

import com.hwi.edtech.dto.UserDTO;
import com.hwi.edtech.entity.User;
import com.hwi.edtech.exception.UserException;

public interface Userservices {
	
	 public List<User> allUsers();
	public User getUserByEmail(String email) throws UserException;
	public User updateUser(UserDTO userDto) throws UserException;
	public void deleteUser(String email) throws UserException;
	public void changePassword(String email, String newPassword) throws UserException;
	public boolean userExists(String email);

}
