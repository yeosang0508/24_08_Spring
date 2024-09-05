package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.util.crawlTest;

@Controller
public class UserHomeController {

	@RequestMapping("/user/home/main")
	public String showMain() {
		return "/user/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/user/home/main";
	}
	
	@RequestMapping("/user/crawl")
	public String doCrawl() {

		crawlTest.crawl();

		return "redirect:/user/home/main";
	}
}