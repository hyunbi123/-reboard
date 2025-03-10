package reboard.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Page {
	
	int totalCount; //글의 전체 갯수 ->자동계산
	int pagePerCount=10; //1페이지의 글의 갯수 ->수동입력
	int totalPage; //전체페이지 ->자동계산
	int requestPage=1;//요청한페이지 or 현재페이지 ->수동입력
	//요청한 페이지에 대한 시작글번호와 끝글번호가 필요
	//글의 시작번호, 글의 끝번호
	int startnum; //글의 시작번호 -> 자동계산
	int endnum; //글의 끝번호 ->자동계산
	//요청한페이지의 네비게이트 시작페이지 번호, 끝페이지 번호
	int startPage; //네비게이트 시작번호 ->자동계산
	int endPage;//네비게이트 끝번호 ->자동계산
	//이전페이지 다음페이지 표시 여부 확인 속성
	boolean isPre;
	boolean isNext;
	//요청한 페이지의 게시판 리스트
	List<Writing> list;
	
	public boolean getIsPre() {
		return isPre;
	}
	
	public boolean getIsNext() {
		return isNext;
	}
	
}
