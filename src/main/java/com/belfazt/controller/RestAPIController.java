package com.belfazt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class RestAPIController {

	@GetMapping("data1")
	public String data1() {
		return "This is data 1";
	}
	
	@GetMapping("data2")
	public String data2() {
		return "This is data 2";
	}
	
}
