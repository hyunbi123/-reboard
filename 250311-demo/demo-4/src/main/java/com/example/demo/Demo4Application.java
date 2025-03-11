package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class Demo4Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Demo4Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
				
	}
	
	//오류발생코드(일치하는 페이지가 존재하지 않을 경우 오류 발생
	/*
	@GetMapping("/")
	public void index() {
	}
	*/
	
	//리턴을 이용하여 페이지 처리
	/*
	@GetMapping("/")
	public String index() {
		return "index";
	}
	*/

	//void를 사용해서 화면을 표시할 경우 url과 파일명이 일치해야 한다.
	@GetMapping("/index")
	public void index() {
	}
}
