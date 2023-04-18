package com.github.julioevencio.apitask.services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.julioevencio.apitask.dto.security.AccountCredentialsDTO;
import com.github.julioevencio.apitask.dto.security.TokenDTO;
import com.github.julioevencio.apitask.dto.user.UserMapperDTO;
import com.github.julioevencio.apitask.dto.user.UserRequestDTO;
import com.github.julioevencio.apitask.dto.user.UserResponseDTO;
import com.github.julioevencio.apitask.entities.UserEntity;
import com.github.julioevencio.apitask.exception.ResourceNotFoundException;
import com.github.julioevencio.apitask.repositories.UserRepository;

@Service
public class AuthService {

	private JwtTokenProviderService jwtTokenProviderService;
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public AuthService(JwtTokenProviderService jwtTokenProviderService, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.jwtTokenProviderService = jwtTokenProviderService;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public TokenDTO signin(AccountCredentialsDTO dto) {
		try {
			//authenticationManager
			//		.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
			
			UserEntity user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Error no AuthService"));
			List<String> roles = user.getRoles().stream().map(role -> role.toString()).toList();
			
			TokenDTO token = jwtTokenProviderService.createAccessToken(dto.getUsername(), roles);
			
			return token;
		} catch (Exception e) {
			throw new ResourceNotFoundException("Invalid username/password supplied");
		}
	}
	
	public UserResponseDTO createUser(UserRequestDTO dto) {
		UserEntity user = UserMapperDTO.fromDTO(dto);
		
		user.setEnabled(true);
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setAccountNonExpired(true);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		user = userRepository.save(user);
		
		return UserMapperDTO.fromEntity(user);
	}

}
