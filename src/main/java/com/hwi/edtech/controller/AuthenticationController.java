package com.hwi.edtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwi.edtech.dto.LoginResponse;
import com.hwi.edtech.dto.LoginUserDTO;
import com.hwi.edtech.dto.UserDTO;
import com.hwi.edtech.entity.User;
import com.hwi.edtech.repository.Userrepository;
import com.hwi.edtech.service.AuthenticationService;
import com.hwi.edtech.utility.JwtService;

@RequestMapping("/auth")
@RestController

public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;
    
    @Autowired
    Userrepository userRepository;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String email = loginUserDto.getEmail();
        
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()).setEmail(email);

        return ResponseEntity.ok(loginResponse);
    }
}