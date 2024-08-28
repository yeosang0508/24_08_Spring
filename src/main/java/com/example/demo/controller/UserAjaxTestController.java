package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAjaxTestController {

	@RequestMapping("/user/home/plus")
	public String showTestPage() {
		return "/user/home/AjaxTest";
	}

	@RequestMapping("/usr/home/doPlus")
	@ResponseBody
	public String doPlus(int num1, int num2) {
		String msg = "더하기 성공!";

		int rs = num1 + num2;

		return rs + "/" + msg + "/S-1";
	}

	@RequestMapping("/user/home/doPlusJson")
	@ResponseBody
	public Map<String, Object> doPlusJson(int num1, int num2) {
		Map<String, Object> rs = new HashMap<>();

		rs.put("rs", num1 + num2);
		rs.put("msg", "더하기 성공! (map)");
		rs.put("code", "S-1 (map)");

		return rs;
	}

}