package com.example.demo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.model.Member;

public interface MemberJPA extends JpaRepository<Member, Integer>{

}
