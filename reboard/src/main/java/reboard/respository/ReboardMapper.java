package reboard.respository;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import reboard.model.Board;


public interface ReboardMapper extends BoardDAOInter{
	public int insert(@Param("board") Board board);
	public List<Board> findAll();
	public Board findById(int id);
	public int update(Board board);
	public int delete(int id);
	public int count(); 
	public List<Board> pageList(@Param("startnum") int startnum, 
			@Param("endnum") int endnum);
	public int viewCountPlus(int id);
	//댓글처리
	public int replyInsert(@Param("board") Board board);

	//검색에 관한 사항
	public int searchCount(
			@Param("searchField") String searchField,
			@Param("searchQuery") String searchQuery); 
	public List<Board> searchPageList(
			@Param("startnum") int startnum, 
			@Param("endnum") int endnum,
			@Param("searchField") String searchField,
			@Param("searchQuery") String searchQuery
			);
	
	public int dateSearchCount(
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
	public List<Board> dateSearchPageList(
			@Param("startnum") int startnum, 
			@Param("endnum") int endnum,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate
			);
	//createdat beteen '2025-01-01' and '2025-01-31' 
}
