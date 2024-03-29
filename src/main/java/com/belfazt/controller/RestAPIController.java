package com.belfazt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belfazt.dao.UserRepository;
import com.belfazt.model.User;

@RestController
@RequestMapping("api")
@CrossOrigin
public class RestAPIController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("data1")
	public String data1() {
		return "This is data 1";
	}
	
	@GetMapping("data2")
	public String data2() {
		return "This is data 2";
	}
	
	@GetMapping("management/report")
	public String report() {
		return "This is manager report";
	}
	
	@GetMapping("admin/users")
	public List<User> allUsers(){
		return this.userRepository.findAll();
	}
	
}
