package com.example.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.model.Question;

public interface QuestionJPA extends JpaRepository<Question,Integer>{

}
