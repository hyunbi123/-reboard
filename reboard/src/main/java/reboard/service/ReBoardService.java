package reboard.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import reboard.model.ViewPage;
import reboard.model.Page;
import reboard.model.ReplyForm;
import reboard.model.Writing;
import reboard.model.Board;
import reboard.model.InsertPage;
import reboard.respository.BoardDAOInter;


@Service
public class ReBoardService {
	
	BoardDAOInter dao;
	
	 @Autowired
	    public ReBoardService(BoardDAOInter dao) {
	        this.dao = dao;
	    }
	 
	@Qualifier("reboardMapper") //ReboardMapper.xml(객체)
	
	
	public void regeister(InsertPage insertPage, MultipartFile file) {
	Board board=new Board();
	board.setTitle(insertPage.getTitle());
	board.setContent(insertPage.getContent());
	board.setAttachment(file.getOriginalFilename());
	//파일 저장은 결과를 확인 후 저장
	int result=dao.insert(board);
	if(result>0) {
		String path="C:\\file\\java\\250120_댓글게시판구현\\reboard\\src\\main\\webapp\\file\\uploadFold";
		String filename=file.getOriginalFilename();
		String fullpath=path+filename;
		try {
		file.transferTo(new File(fullpath));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	}
	
	public Page getPage(int requestPage) {
		Page page=new Page();
		page.setTotalCount(dao.count());//전체글의 갯수
		page.setPagePerCount(10); //페이지당 글의 수 ->추후 입력값처리
		
		int totalPage=page.getTotalCount()/page.getPagePerCount();
		if((page.getTotalCount()%page.getPagePerCount())!=0){
				totalPage+=1;
		}
		page.setTotalPage(totalPage);//전체페이지 수
		
		
		page.setRequestPage(requestPage);//요청페이지(기본페이지1) -> 추후 입력값처리
		
		int startnum=((page.getRequestPage()-1)*page.getPagePerCount())+1; //요청한 페이지의 시작 글번호
		int endnum=startnum+page.getPagePerCount()-1; //요청한 페이지의 끝 글번호
		if(endnum>=page.getTotalCount()){ 
			endnum=page.getTotalCount();
		}
		page.setStartnum(startnum); //페이지 시작 번호
		page.setEndnum(endnum); //페이지 끝 번호
		
		int startPage=((page.getRequestPage()-1)/5)*5+1; //기준점확인
		int endPage=(startPage-1)+5;
		if(endPage>=page.getTotalPage()){ 
			endPage=page.getTotalPage();
		}
		page.setStartPage(startPage);//네비게이트의 시작페이지번호
		page.setEndPage(endPage); //네비게이트의 끝페이지번호
		
		//이전페이지 표시여부-6page보다 작은 경우 이전페이지표시 활성화
		boolean isPre=false;
		if(page.getRequestPage()<6) isPre=false;
		else isPre=true;
		page.setPre(isPre);

		//endPage(현재 표시하는 페이지중 가장 큰페이지) 전체페이지보다 큰경우
		boolean isNext=false;
		if(page.getTotalPage()>page.getEndPage()) isNext=true;
		else isNext=false;
		page.setNext(isNext);
		
		//글번호 시작, 끝을 알려주면 해당하는 페이지의 리스트만 리턴하는 함수
		List<Board> boardlist
		=dao.pageList(page.getStartnum(),page.getEndnum());
		 //Board리스트를 ->Page리스트 변경 후 저장
		page.setList(getPageList(boardlist));
		//System.out.println("********:"+page);
		return page;
	}
	//페이지리스트에 정보를 저장하는 함수
	//Board객체를 Writing객체로 변경해주는 함수
		public List<Writing> getPageList(List<Board> boardlist) {
			List<Writing> pagelist=new ArrayList<Writing>();
			for(Board board:boardlist) {
			Writing page=new Writing(); 
			page.setId(board.getId());
			page.setTitle(board.getTitle());
			page.setCreatedat(board.getCreatedat());
			page.setAuthor(board.getAuthor());
			page.setViewcnt(board.getViewcnt());
			pagelist.add(page);
			}
			return pagelist;
		}
		
		//상세보기
		public ViewPage viewPage(int id) {
			Board board=dao.findById(id);
			System.out.println(board);
			//dao로부터 받은 데이터를 ViewPage객체로 변환
			ViewPage page=new ViewPage();
			BeanUtils.copyProperties(board, page);
			System.out.println(page);
			//viewCnt를 1증가
			if(board!=null)	dao.viewCountPlus(id);
			return page;
		}

		public void replyRegister(ReplyForm reform
				,MultipartFile file) {
			//reform에 parentid를 이용하여 부모글에 대한 정보획득
			Board parentBoard=dao.findById(reform.getParentid());
			//부모의 정보, 리플정보, 세션정보를 합쳐서 하나의 Board로 전달
			Board board=new Board();
			board.setTitle(reform.getTitle());
			board.setContent(reform.getContent());
			board.setAttachment(file.getOriginalFilename());
			board.setAuthor("user");
			board.setType(parentBoard.getType());//현재게시판종류를 그대로 입력
			board.setParentid(parentBoard.getId()); //부모의id가 댓글의 parentid
			board.setTab(parentBoard.getTab()+1); //부모글댓글깊이+1
			//파일 저장은 결과를 확인 후 저장
			int result=dao.replyInsert(board);
			if(result>0) {
				String path="C:\\storage\\";
				String filename=file.getOriginalFilename();
				String fullpath=path+filename;
				try {
				file.transferTo(new File(fullpath));
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
		}
		
		//날짜 기간으로 검색
		public Page searchForDate(String startDate, String endDate) {
			int requestPage=1;
			int count=dao.dateSearchCount(startDate,endDate);
			int startnum=(requestPage-1)*10+1;
			int endnum=startnum+9;
			List<Board> list
			=dao.dateSearchPageList(startnum,endnum,
					startDate,endDate);
			return makePage(requestPage,count, list);
			
		}

		//찾기 검색
		public Page searchForQuery(String searchField, String searchQuery) {
			//찾기 조건에 맞는 글의 갯수
			//찾기 조건에 맞는 글의 리스트
			int requestPage=1;
			int count=dao.searchCount(searchField,searchQuery);
			int startnum=(requestPage-1)*10+1;
			int endnum=startnum+9;
			List<Board> list
			=dao.searchPageList(startnum,endnum,
					searchField,searchQuery);
			return makePage(requestPage,count, list);
		}
		
		//요청한 페이지, 글의 갯수, 리스트를 활용하면view에서 처리할 페이지가 생성
		public Page makePage(
				int requestPage
				,int count, 
				List<Board> list
				) {
			Page page=new Page();
			page.setTotalCount(count);//전체글의 갯수
			page.setPagePerCount(10); //페이지당 글의 수 ->추후 입력값처리
			
			int totalPage=page.getTotalCount()/page.getPagePerCount();
			if((page.getTotalCount()%page.getPagePerCount())!=0){
					totalPage+=1;
			}
			page.setTotalPage(totalPage);//전체페이지 수
			
			
			page.setRequestPage(requestPage);//요청페이지(기본페이지1) -> 추후 입력값처리
			
			int startnum=((page.getRequestPage()-1)*page.getPagePerCount())+1; //요청한 페이지의 시작 글번호
			int endnum=startnum+page.getPagePerCount()-1; //요청한 페이지의 끝 글번호
			if(endnum>=page.getTotalCount()){ 
				endnum=page.getTotalCount();
			}
			page.setStartnum(startnum); //페이지 시작 번호
			page.setEndnum(endnum); //페이지 끝 번호
			
			int startPage=((page.getRequestPage()-1)/5)*5+1; //기준점확인
			int endPage=(startPage-1)+5;
			if(endPage>=page.getTotalPage()){ 
				endPage=page.getTotalPage();
			}
			page.setStartPage(startPage);//네비게이트의 시작페이지번호
			page.setEndPage(endPage); //네비게이트의 끝페이지번호
			
			//이전페이지 표시여부-6page보다 작은 경우 이전페이지표시 활성화
			boolean isPre=false;
			if(page.getRequestPage()<6) isPre=false;
			else isPre=true;
			page.setPre(isPre);

			//endPage(현재 표시하는 페이지중 가장 큰페이지) 전체페이지보다 큰경우
			boolean isNext=false;
			if(page.getTotalPage()>page.getEndPage()) isNext=true;
			else isNext=false;
			page.setNext(isNext);
			
			//글번호 시작, 끝을 알려주면 해당하는 페이지의 리스트만 리턴하는 함수
			List<Board> boardlist=list;
			 //Board리스트를 ->Page리스트 변경 후 저장
			page.setList(getPageList(boardlist));
			//System.out.println("********:"+page);
			return page;
		}

		public Page searchForQuery(int requestPage, String searchField, String searchQuery) {
			int count=dao.searchCount(searchField,searchQuery);
			int startnum=(requestPage-1)*10+1;
			int endnum=startnum+9;
			List<Board> list
			=dao.searchPageList(startnum,endnum,
					searchField,searchQuery);
			return makePage(requestPage,count, list);
		}
}