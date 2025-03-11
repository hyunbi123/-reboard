package com.example.demo.board.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class QuestionForm {
	@NotEmpty(message="제목은 필수사항입니다.")
	private String subject;
	@NotEmpty(message="내용은 필수사항입니다.")
	private String content;
	

}
