package com.github.julioevencio.apitask.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.julioevencio.apitask.dto.security.LoginRequestDTO;
import com.github.julioevencio.apitask.dto.security.TokenResponseDTO;
import com.github.julioevencio.apitask.dto.user.UserMapperDTO;
import com.github.julioevencio.apitask.dto.user.UserRequestDTO;
import com.github.julioevencio.apitask.dto.user.UserResponseDTO;
import com.github.julioevencio.apitask.entities.UserEntity;
import com.github.julioevencio.apitask.exception.LoginException;
import com.github.julioevencio.apitask.exception.ResourceNotFoundException;
import com.github.julioevencio.apitask.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TokenJwtService tokenJwtService;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, TokenJwtService tokenJwtService,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.tokenJwtService = tokenJwtService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponseDTO register(UserRequestDTO dto) {
		UserEntity user = UserMapperDTO.fromDTO(dto);

		user.setEnabled(true);
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setAccountNonExpired(true);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		return UserMapperDTO.fromEntity(userRepository.save(user));
	}

	@Override
	public TokenResponseDTO login(LoginRequestDTO dto) {
		UserEntity user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new LoginException("Username not found"));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new LoginException("Invalid password");
		}

		List<String> roles = user.getRoles().stream().map(role -> role.toString()).toList();

		return tokenJwtService.createAccessToken(user.getUsername(), roles);
	}

	@Override
	public UserResponseDTO me() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return UserMapperDTO.fromEntity(user);
	}

}
