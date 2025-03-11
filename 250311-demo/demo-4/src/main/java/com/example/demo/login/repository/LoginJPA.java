package com.example.demo.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.model.Member;

public interface LoginJPA extends JpaRepository<Member, Integer>{

	Optional<Member> findByUsername(String username);

}
