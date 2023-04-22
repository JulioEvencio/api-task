package com.github.julioevencio.apitask.services;

import java.util.List;

import com.github.julioevencio.apitask.dto.security.TokenResponseDTO;

public interface TokenJwtService {

	public TokenResponseDTO createAccessToken(String username, List<String> roles);

	public boolean validateToken(String token);

	public String getUsername(String token);

}
