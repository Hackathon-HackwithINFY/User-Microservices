package com.hwi.edtech.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hwi.edtech.dto.LoginUserDTO;
import com.hwi.edtech.dto.UserDTO;
import com.hwi.edtech.entity.User;
import com.hwi.edtech.repository.Userrepository;

@Service
public class AuthenticationService {
	private final Userrepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(Userrepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User signup(UserDTO input) {
		User user = new User();
		user.setEmail(input.getEmail());
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		return userRepository.save(user);
	}

	public User authenticate(LoginUserDTO input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		Optional<User> optional = userRepository.findByEmail(input.getEmail());

		User user = optional.orElseThrow();
		return user;
	}
}