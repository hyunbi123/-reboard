package com.example.demo.login.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler 
implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//성공시 이 객체는 로그인에 정보를 저장하는 역할
		//Role에 대한 역할을 미리 설정
		List<String> roleNames=new ArrayList<String>();
		System.out.println("login success!!");
		authentication.getAuthorities().forEach(authority->{
			roleNames.add(authority.getAuthority());
		});
		//여러개의 권한을 리스트에 저장하고 각 권한에 맞는 페이지로 이동
		if(roleNames.contains("ROLE_ROOT")) {
			response.sendRedirect("/index");
			return;
		}
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/index");
			return;
		}
		if(roleNames.contains("ROLE_USER")) {
			response.sendRedirect("/index");
			//response.sendRedirect("/login/user");//login컨트롤러 페이지 이동
			return;
		}
		if(roleNames.contains("ROLE_GUEST")) {
			response.sendRedirect("/index");
			return;
		}
		
		
	}

}
