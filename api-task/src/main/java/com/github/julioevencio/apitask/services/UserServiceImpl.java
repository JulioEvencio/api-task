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
import com.github.julioevencio.apitask.exceptions.ApiTaskLoginException;
import com.github.julioevencio.apitask.exceptions.ApiTaskResourceNotFoundException;
import com.github.julioevencio.apitask.exceptions.ApiTaskSQLException;
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
		try {
			UserEntity user = UserMapperDTO.fromDTO(dto);

			user.setEnabled(true);
			user.setCredentialsNonExpired(true);
			user.setAccountNonLocked(true);
			user.setAccountNonExpired(true);
			user.setPassword(passwordEncoder.encode(dto.getPassword()));

			return UserMapperDTO.fromEntity(userRepository.save(user));
		} catch (Exception e) {
			throw new ApiTaskSQLException("Username or e-mail already exists");
		}
	}

	@Override
	public TokenResponseDTO login(LoginRequestDTO dto) {
		UserEntity user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new ApiTaskLoginException("Username not found"));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new ApiTaskLoginException("Invalid password");
		}

		List<String> roles = user.getRoles().stream().map(role -> role.toString()).toList();

		return tokenJwtService.createAccessToken(user.getUsername(), roles);
	}

	@Override
	public UserResponseDTO me() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiTaskResourceNotFoundException("User not found"));

		return UserMapperDTO.fromEntity(user);
	}

}
