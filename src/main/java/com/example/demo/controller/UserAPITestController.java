package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAPITestController {

	@RequestMapping("/user/home/APITest")
	public String showAPITest() {
		return "/user/home/APITest";
	}

	@RequestMapping("/user/home/APITest2")
	public String showAPITest2() {
		return "/user/home/APITest2";
	}
}