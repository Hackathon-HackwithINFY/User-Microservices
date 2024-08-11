package com.hwi.edtech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hwi.edtech.dto.UserDTO;
import com.hwi.edtech.entity.User;
import com.hwi.edtech.exception.UserException;
import com.hwi.edtech.repository.Userrepository;

@Service
public class UserservicesImpl implements Userservices {

	private final Userrepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	@Autowired
	public UserservicesImpl(Userrepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
	}

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();

		userRepository.findAll().forEach(users::add);

		return users;
	}

	@Override
	public User getUserByEmail(String email) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> optional = userRepository.findByEmail(email);

		User user = optional.orElseThrow(() -> new UserException("User does not exist"));
		return user;
	}

	@Override
	public User updateUser(UserDTO userDto) throws UserException {
		Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
		if (existingUser.isEmpty())
			throw new UserException("USer does not exist");
		User user = existingUser.get();
		modelMapper.map(userDto, user);
		if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}

	@Override
	public void deleteUser(String email) throws UserException {
		// TODO Auto-generated method stub

		userRepository.delete(this.getUserByEmail(email));

	}

	@Override

	public void changePassword(String email, String newPassword) throws UserException {
		Optional<User> optional = userRepository.findByEmail(email);
		User user = null;
		if (optional.isEmpty())
			throw new UserException("User does not exist");
		if (optional.isPresent())
			user = optional.get();

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

	}

	@Override
	public boolean userExists(String email) {
		// TODO Auto-generated method stub
		Optional<User> optional = userRepository.findByEmail(email);

		if (optional.isPresent())
			return true;
		else
			return false;

	}

}
