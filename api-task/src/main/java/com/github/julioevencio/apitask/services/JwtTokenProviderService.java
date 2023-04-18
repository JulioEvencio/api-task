package com.github.julioevencio.apitask.services;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.julioevencio.apitask.dto.security.TokenDTO;
import com.github.julioevencio.apitask.exception.InvalidJwtAuthenticationException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProviderService {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:3600000}")
	private Long validityInMilliseconds = 3600000L;

	@Autowired
	private UserDetailsService userDetailsService;

	private Algorithm algorithm = null;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}

	public TokenDTO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		String accessToken = this.getAccessToken(username, roles, now, validity);
		String refreshToken = this.getRefreshToken(username, roles, now);

		return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issueURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.withIssuer(issueURL)
				.sign(algorithm)
				.strip();
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date validity = new Date(now.getTime() + validityInMilliseconds * 3);
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.sign(algorithm)
				.strip();
	}

	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = this.decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		
		return new UsernamePasswordAuthenticationToken(decodedJWT, "", userDetails.getAuthorities());
	}
	
	private DecodedJWT decodedToken(String token) {
		Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(alg).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		
		return decodedJWT;
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			DecodedJWT decodedJWT = this.decodedToken(token);
			
			if (decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			
			return true;
		} catch(Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
		}
	}
	
}
