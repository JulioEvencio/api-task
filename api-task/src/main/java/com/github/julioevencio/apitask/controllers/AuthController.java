package com.github.julioevencio.apitask.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.julioevencio.apitask.dto.security.AccountCredentialsDTO;
import com.github.julioevencio.apitask.dto.security.TokenDTO;
import com.github.julioevencio.apitask.dto.user.UserRequestDTO;
import com.github.julioevencio.apitask.dto.user.UserResponseDTO;
import com.github.julioevencio.apitask.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.createUser(dto));
	}

	@PostMapping("/signin")
	public ResponseEntity<TokenDTO> signin(@RequestBody @Valid AccountCredentialsDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.signin(dto));
	}

}
