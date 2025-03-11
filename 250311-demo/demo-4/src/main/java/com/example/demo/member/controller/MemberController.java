package com.example.demo.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.member.model.RegisterForm;
import com.example.demo.member.service.MemberService;



@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService service;
	
	@GetMapping("register")
	public void getRegister() {} 
	
	@PostMapping("register")
	public String postRegister(@ModelAttribute RegisterForm form) {
		System.out.println(form);
		service.register(form);
		return "redirect:/";
		//return "redirect:/login";
	}
}
