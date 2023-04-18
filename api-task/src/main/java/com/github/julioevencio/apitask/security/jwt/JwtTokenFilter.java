package com.github.julioevencio.apitask.security.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.github.julioevencio.apitask.services.JwtTokenProviderService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProviderService jwtTokenProviderService;

	public JwtTokenFilter(JwtTokenProviderService jwtTokenProviderService) {
		this.jwtTokenProviderService = jwtTokenProviderService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = jwtTokenProviderService.resolveToken((HttpServletRequest) request);

		if (token != null && jwtTokenProviderService.validateToken(token)) {
			Authentication auth = jwtTokenProviderService.getAuthentication(token);

			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		chain.doFilter(request, response);
	}

}
