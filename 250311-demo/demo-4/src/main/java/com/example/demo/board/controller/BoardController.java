package com.example.demo.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.board.model.Question;
import com.example.demo.board.model.QuestionForm;
import com.example.demo.board.service.QuestionService;
import com.example.demo.board.repository.QuestionJPA;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	QuestionService qservice;
	
	@Autowired
    private QuestionJPA qjpa;
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("qlist", qservice.getList());
		return "board/list"; //templates/board/list.html
	}
	
	@GetMapping("/test1")
	public void test1(Model model) {
		model.addAttribute("data","hello"); //board/test1.html
	}
	@GetMapping("/test2")
	public void test2(Model model) {
		//model.addAttribute("data","hello"); //board/test2.html
	}
	//localhost:8080/board/pagelist?page=0
	//localhost:8080/board/pagelist
	@GetMapping("pagelist")
	public String pagelist(Model model
		, @RequestParam(value="page",defaultValue = "0") int page) {
		
		Pageable pageable =PageRequest.of(page, 10);
		Page<Question> paging=qservice.pageList(pageable);
		model.addAttribute("paging",paging); //페이지에 관련된 모든 정보 포함
		return "board/pagelist"; //templates/board/pagelist.html
	}
	
	//Question 입력폼 열기( 중요사항:입력값의 폼이 반드시 존재해야한다. )
	@GetMapping("question/insert")
	public String questionInsertForm(QuestionForm questionform) {
		return "board/question_form"; // templates/board/question_form.html
	}
	
	@PostMapping("question/insert")
	public String questionInsert(@Validated QuestionForm questionform,
			BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			//에러 갯수를 확인
			System.out.println(bindResult.getErrorCount());
			//모든 에러에 대한 내용을 확인
			for (FieldError error : bindResult.getFieldErrors()) {
				System.out.println("Error in field: " + error.getField() 
				+ " - " + error.getDefaultMessage());
			}
			return "board/question_form"; //errorpage.html
		}
		System.out.println(questionform);
		qservice.insert(questionform);
		//return "board/pagelist"; //model 처리가 안돼서 오류발생
		return "redirect:/board/pagelist";
	}
}
