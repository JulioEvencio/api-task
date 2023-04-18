package com.github.julioevencio.apitask.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.julioevencio.apitask.exception.ResourceNotFoundException;
import com.github.julioevencio.apitask.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Username or password not found"));
	}

}
