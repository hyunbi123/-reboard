package com.example.demo.board.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Answer {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(columnDefinition = "TEXT")
	private String content;
	private LocalDateTime createDate;
	//여러개의 답변에 한개의 질문(**주체는 자신의 객체) 
	@ManyToOne
	private Question question;
	//many는 자신의 객체 여러개, one연결된 객체, 단일객체가 있으면 테이블에서는
	//id로 연결하여 사용하므로 question_id로 테이블 생성
}
