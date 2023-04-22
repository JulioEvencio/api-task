package com.github.julioevencio.apitask.services;

import com.github.julioevencio.apitask.dto.security.LoginRequestDTO;
import com.github.julioevencio.apitask.dto.security.TokenResponseDTO;
import com.github.julioevencio.apitask.dto.user.UserRequestDTO;
import com.github.julioevencio.apitask.dto.user.UserResponseDTO;

public interface UserService {

	public UserResponseDTO register(UserRequestDTO dto);

	public TokenResponseDTO login(LoginRequestDTO dto);
	
	public UserResponseDTO me();

}
