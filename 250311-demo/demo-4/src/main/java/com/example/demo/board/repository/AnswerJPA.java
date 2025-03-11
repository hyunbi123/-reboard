package com.example.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.model.Answer;
import com.example.demo.board.model.Question;

public interface AnswerJPA extends JpaRepository<Answer,Integer>{

}
