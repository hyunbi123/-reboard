package com.example.demo.login.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("")
	public String login() {
		return "login/login"; 
	}
	
	@GetMapping("success")
	public String success() {
		return "login/success"; 
	}
	
	@GetMapping("fail")
	public String fail() {
		return "login/fail"; 
	}
	
	
	@GetMapping("accessError")
	public String accessError(Authentication auth, Model model) {
		return "login/accessError";
	}
	@GetMapping("user")
	public String user() {
		return "login/user"; 
	}
}
