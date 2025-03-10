package reboard.respository;

import java.util.List;

import reboard.model.Board;
//기능설계서
public interface BoardDAOInter {
	//입력
	public int insert(Board board);
	
	//전체출력
	public List<Board> findAll();
	//아이디로 선택출력
	public Board findById(int id);
	//아이디로 업데이트
	public int update(Board board);
	//아이디로 삭제
	public int delete(int id);
	//게시글 수
	public int count();
	//페이지 리스트
	public List<Board> pageList(int startnum, int endnum);
	//게시글 count
	public int viewCountPlus(int id);
	
	//댓글입력
	public int replyInsert(Board board);
	
	//검색 글의 갯수확인, 검색글의 리스트
	public int searchCount(String searchField,String searchQuery); 
	public List<Board> searchPageList(int startnum,int endnum,
			String searchField,
			String searchQuery
			);
	
	public int dateSearchCount(
			String startDate,
			String endDate);
	
	public List<Board> dateSearchPageList(
			int startnum, 
			int endnum,
			String startDate,
			String endDate
			);
}
