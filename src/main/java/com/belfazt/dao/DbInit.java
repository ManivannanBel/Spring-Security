package com.belfazt.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.belfazt.model.User;

@Service
public class DbInit implements CommandLineRunner{
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public DbInit(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		User user1 = new User("mani", passwordEncoder.encode("mani123"), "USER", "");
		User user2 = new User("admin", passwordEncoder.encode("admin123"), "ADMIN", "ACCESS_API_DATA_1,ACCESS_API_DATA_2");
		User user3 = new User("maxi", passwordEncoder.encode("maxi123"), "MANAGER", "ACCESS_API_DATA_1");
		
		List<User> users = Arrays.asList(user1, user2, user3);
		
		userRepository.saveAll(users);
	}	
}
