package com.example.demo.board.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Question {
	//질문은 아이디, 제목, 내용, 작성일을 가진다.
	@Id
	@GeneratedValue//(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 100) //테이블에서 제약사항으로 길이를 200초과 안됨
	private String subject;
	@Column(columnDefinition = "TEXT")
	private String content;
	private LocalDateTime createDate;
	//질문하나에 여러개의 글이 연결되어 있음
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	//one은 자신의 객체, many는 연결되는 객체이며 리스트, 하지만 테이블에 표시x
}
