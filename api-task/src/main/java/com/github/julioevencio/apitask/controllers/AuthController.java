package com.github.julioevencio.apitask.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.julioevencio.apitask.dto.security.LoginRequestDTO;
import com.github.julioevencio.apitask.dto.security.TokenResponseDTO;
import com.github.julioevencio.apitask.dto.user.UserRequestDTO;
import com.github.julioevencio.apitask.dto.user.UserResponseDTO;
import com.github.julioevencio.apitask.services.UserService;
import com.github.julioevencio.apitask.services.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
	}

	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.login(dto));
	}

	@GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDTO> login() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.me());
	}

}
