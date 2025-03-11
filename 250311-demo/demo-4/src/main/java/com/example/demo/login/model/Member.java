package com.example.demo.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
public class Member {
	Integer id;
	String username;
	String password;
	String email;
	String role;
	java.sql.Date regdate;
}

