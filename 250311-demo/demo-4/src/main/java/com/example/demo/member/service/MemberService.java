package com.example.demo.member.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.member.model.Member;
import com.example.demo.member.model.RegisterForm;
import com.example.demo.member.repository.MemberJPA;

@Service
public class MemberService {
	
	@Autowired
	MemberJPA memberjpa;
	
	public void register(RegisterForm form) {
		Member member=new Member();
		PasswordEncoder pe=new BCryptPasswordEncoder();
		form.setPassword(pe.encode(form.getPassword()));
		BeanUtils.copyProperties(form, member);
		member.setRole("ROLE_USER");
		member.setRegdate(new java.sql.Date(new Date().getTime()));
		memberjpa.save(member);		
	}
	
}
