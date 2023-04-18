package com.github.julioevencio.apitask.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.github.julioevencio.apitask.dto.security.AccountCredentialsDTO;
import com.github.julioevencio.apitask.dto.security.TokenDTO;
import com.github.julioevencio.apitask.entities.UserEntity;
import com.github.julioevencio.apitask.exception.ResourceNotFoundException;
import com.github.julioevencio.apitask.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private JwtTokenProviderService jwtTokenProviderService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	public TokenDTO signin(AccountCredentialsDTO dto) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

			UserEntity user = userRepository.findByUsername(dto.getEmail()).orElseThrow();
			List<String> roles = user.getRoles().stream().map(role -> role.toString()).toList();
			
			TokenDTO token = jwtTokenProviderService.createAccessToken(dto.getEmail(), roles);
			
			return token;
		} catch (Exception e) {
			throw new ResourceNotFoundException("Invalid username/password supplied");
		}
	}

}
