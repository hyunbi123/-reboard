package com.example.demo.board.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.board.model.Question;
import com.example.demo.board.model.QuestionForm;
import com.example.demo.board.repository.QuestionJPA;

@Service
public class QuestionService {

	@Autowired
	QuestionJPA qjpa;

	public List<Question> getList() {
		return qjpa.findAll();
	}

	public Page<Question> pageList(Pageable pageable) {
		return qjpa.findAll(pageable);
	}

	public void insert(QuestionForm questionform) {
		Question question = new Question();
		BeanUtils.copyProperties(questionform, question);
		question.setCreateDate(LocalDateTime.now());
		Question q = qjpa.save(question);
	}
}
