package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.board.model.Answer;
import com.example.demo.board.model.Question;
import com.example.demo.board.repository.AnswerJPA;
import com.example.demo.board.repository.QuestionJPA;

import jakarta.persistence.CascadeType;
import jakarta.transaction.Transactional;

@SpringBootTest
class Demo4ApplicationTests {

	@Autowired
	private QuestionJPA qjpa;
	@Autowired
	private AnswerJPA ajpa;
	
	@Test
	void contextLoads() {
		//전체지우기
		//시나리오.질문을 먼저 지울것인가? 답변을 먼저 지울것인가?
		//qjpa.deleteAll();
		//Question객체에서 Answer리스트가 연쇄적으로 삭제되도록 설계
		//(mappedBy = "question", cascade = CascadeType.REMOVE)
		
		//입력시 @GeneratedValue(strategy = GenerationType.IDENTITY)
		//strategy사용시 오류발생
		//질문 5개 입력
		/*
		for(int i=1;i<=105;i++) {
		Question q=new Question();
		q.setSubject(i+"번째 질문입니다.");
		q.setContent(i+"질문에 대해 알려주세요.");
		q.setCreateDate(LocalDateTime.now());
		qjpa.save(q);
		}
		*/
		
		//서버중지상태와 서버동작상태 모두 오류발생
		//System.out.println(qjpa.findAll());
		
		//별도로 출력해야 테스트 오류사항 없음
		/*
		List<Question> list=qjpa.findAll();
		for(Question q: list) {
			//System.out.println(q); //test시 toString함수는 오류발생
			//별도로 출력해야 테스트 오류사항 없음
			 System.out.println(q.getId());
			System.out.println(q.getSubject());
		 }
		 */
		
		//id글에 대한 답글을 입력
		//202번글이 만약 존재한다면 202번글에 대한 객체를 얻어야하고
		//얻은 객체를 답글에 입력사항으로 입력처리
		/*
		Question question=qjpa.findById(204).get();
		for(int i=1;i<=5;i++) {
		Answer answer=new Answer();
		answer.setQuestion(question);
		answer.setContent("202번 글에 대한 "+i+"번째 답글입니다.");
		answer.setCreateDate(LocalDateTime.now());
		ajpa.save(answer);
		}
		*/
		
		
		//152번 질문에 대한 답글리스트를 보기
		//오류발생
		//오류발생의 원인은 Question객체를 얻은 후에 AnswerList가 테이블로 연결되므로
		//@Transactional 실행함으로서 두 테이블간의 연결이 되는 상태가 된다.
		//아래의 testAnswerList()함수를 참고
		/*
		Question q=qjpa.findById(152).get();
		for(Answer answer:q.getAnswerList()) {
			System.out.println(answer.getContent());
		}
		*/
		
		//페이지만들기(org.springframework.data.domain.*)
		//Pageable pageable = null; //pageable은 기본적인 설정이 있어야 조회가 가능함
		//Pageable pageable =PageRequest.of(0, 10); //1(0)번페이지의 글10개 단위
		Pageable pageable 
		=PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createDate")));
		Page<Question> page=qjpa.findAll(pageable);
		//페이지의 속성은 어떤 사항이 있는가?
		//페이지의 리스트, 한페이지 표시될 크기(1page당 글이 10개 나열),현재페이지번호
		//전체데이터의 갯수,전체페이지수,다음(이전)페이지 존재여부
		System.out.println("전체글의 갯수:"+page.getTotalElements());
		System.out.println("전체페이지 수:"+page.getTotalPages());
		//System.out.println("해당페이지의 글리스트:"+page.getContent());
		for(Question q:page.getContent()) {
			System.out.println(q.getSubject());
		}
		System.out.println("현재페이지:"+page.getNumber()+1);
		System.out.println("이전페이지표시여부확인:"+page.hasPrevious());
		System.out.println("다음페이지표시여부확인:"+page.hasNext());
		
		//참고사항 : Pageable정렬(createDate->필드명)
		//PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createDate")));
	}
	
	
	//@Transactional
	//@Test
	public void testAnswerList() {
		Question q=qjpa.findById(202).get();
		List<Answer> list=q.getAnswerList();
		//System.out.println(list); //toString을 호출하는 것이므로 오류발생

		//개별출력이므로 정상 출력
		for(Answer answer:q.getAnswerList()) {
			System.out.println(answer.getContent());
		}
	}
	
	 //@Test
	    public void testFindAll() {
		 List<Question> list=qjpa.findAll();
		 //q자체를 출력할 경우 toString함수가 호출되는데 test에서는 이를 지원하지 않음
		 //결론은 값을 별도로 출력을 해줘야함
		 for(Question q: list) {
			 // System.out.println(q); //test시 toString함수는 오류발생
			 System.out.println(q.getId());
		 }
	 }
	    
	 //최종적으로 질문에 대한 답글 모두 보기
	    
	 //@Transactional   
	 //@Test
	 public void findall() {
		 //전체글의 리스트
		 List<Question> question=qjpa.findAll();
		 for(Question q: question) {
			 System.out.println(q.getSubject());
			 for(Answer answer:q.getAnswerList()) {
				 System.out.println(answer.getContent());
			 }
		 }
	 }
	    
	    
	    

}
