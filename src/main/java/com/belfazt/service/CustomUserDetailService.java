package com.belfazt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.belfazt.dao.UserRepository;
import com.belfazt.model.User;
import com.belfazt.security.UserPrincipal;


public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		UserPrincipal userPrincipal = new UserPrincipal(user);
		
		return userPrincipal;
		
	}
	
}
