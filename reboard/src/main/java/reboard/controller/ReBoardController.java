package reboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import reboard.model.InsertPage;
import reboard.model.Page;
import reboard.model.ReplyForm;
import reboard.model.ViewPage;
import reboard.service.ReBoardService;


@Controller
@RequestMapping("/board")
public class ReBoardController {

	@Autowired
	ReBoardService service;
	
	//localhost:8080/board/ (주의사항 마지막 / 반드시 포함)
	@RequestMapping("/")
	public String index(Model model, Integer requestPage, String searchQuery) {
		//return "/WEB-INF/views/index.jsp";
		model.addAttribute("requestPage", requestPage);
		model.addAttribute("searchQuery", searchQuery);
		return "index";
	}
	
	//게시판만의 데이터를 처리하는 페이지
	@RequestMapping("ajaxList")
	public String ajaxList(Model model,
			@RequestParam(defaultValue = "1") Integer requestPage,
			@RequestParam(defaultValue = "") String searchQuery) {
		
		//System.out.println("************" + searchQuery);
		
		if (!searchQuery.equals("")) {
			model.addAttribute("searchQuery", searchQuery);
			/*
	        model.addAttribute("boardpage", service.searchForQuery("title",searchQuery));
	    	*/
			model.addAttribute("boardpage"
	        		, service.searchForQuery(requestPage, "title", searchQuery));
	    } else {
	        model.addAttribute("boardpage", service.getPage(requestPage));
	    }
		//System.out.println(service.searchForQuery("title",searchQuery));
	    return "ajaxlist";
	}
	
	
	//문제. 검색한 리스트에서 페이지 번호를 클릭했을 때 검색한 값이 유지되면서
	//선택한 페이지의 값이 나타나도록 처리하시오.(ajaxlist.jsp 페이지 수정)
	
	
	//get방식의 글쓰기 폼
	@GetMapping("write")
	public String writeform() {
		return "writeform";
	}
	//post방식의 글 처리
	@PostMapping("write")
	public String write(
			@ModelAttribute InsertPage insertPage,
			@RequestParam("attachment") MultipartFile file
			) {
		//전달받은 데이터가 정상적인지 확인
		/*
		System.out.println(insertPage);
		System.out.println(file.getOriginalFilename());
		*/
		
		//파일저장(위치-웹서버저장위치랑 별도로 생각)
		/*
		String path="C:\\storage\\";
		String filename=file.getOriginalFilename();
		String fullpath=path+filename;
		try {
		file.transferTo(new File(fullpath));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		*/	
		service.regeister(insertPage,file);
		return "redirect:/board/list"; // 입력 처리 후 보여질 페이지로 이동
	}
	
	//페이지리스트보기
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(defaultValue = "1") 
							int requestPage) {
		ModelAndView mv=new ModelAndView();
		mv.addObject("boardpage",service.getPage(requestPage));
		mv.setViewName("list");
		return mv;
	}
	
	//상세보기
	@RequestMapping("view")
	public ModelAndView view(int id){
		ModelAndView mv=new ModelAndView();
		mv.addObject("view",service.viewPage(id));
		mv.setViewName("view");
		return mv;
	}
	
	//수정하기
	@RequestMapping("updateform")
	public ModelAndView updateform(int id){
		ModelAndView mv=new ModelAndView();
		mv.addObject("view",service.viewPage(id));
		mv.setViewName("updateform");
		return mv;
	}
	
	/*
	//댓글쓰기폼열기
	@GetMapping("reply")
	public String replyform(int parentid, Model model) {
		model.addAttribute("parentid", parentid);
		ViewPage view=service.viewPage(parentid);
		model.addAttribute("title",view.getTitle());
		model.addAttribute("tab",view.getTab());
		System.out.println("title:"+view.getTitle());
		System.out.println("tab:"+view.getTab());
		return "replyform";
	}
	*/
	
	//댓글처리
	@PostMapping("reply")
	public String reply(@ModelAttribute ReplyForm reform,
			@RequestParam("attachment") MultipartFile file) {
		service.replyRegister(reform,file);
		return "redirect:/board/list";
	}
	
	//검색
		/*
		@GetMapping("search")
		public ModelAndView search(String searchField,String searchQuery,
				String  startDate, String endDate) {
			System.out.println(searchField);
			System.out.println(searchQuery);
			System.out.println(startDate);
			System.out.println(endDate);
			ModelAndView mv=new ModelAndView();
			Page page=null;
			if(searchField.equals("createdat")) {
				//날짜에 의한 검색
				page=service.searchForDate(startDate, endDate);
			}else {
				//질의어에 의한 검색
				page=service.searchForQuery(searchField,searchQuery);
			}
			//return "redirect:/board/list";
			mv.addObject("boardpage",page);
			mv.setViewName("searchList");
			return mv;
		}
		*/
		
		//Model을 이용한 검색처리
		@GetMapping("search")
		public String search(Model model,
				String searchField,
				String searchQuery,
				String  startDate, String endDate) {
			Page page=null;
			switch(searchField) {
			case "createdat" : 
				page=service.searchForDate(startDate, endDate);
				break;
			case "title":
				page=service.searchForQuery(searchField,searchQuery);
				break;
			case "content":
				page=service.searchForQuery(searchField,searchQuery);
				break;
			case "author":
				page=service.searchForQuery(searchField,searchQuery);
				break;
			default:
				return "redirect:/board/list";
			}
			model.addAttribute("boardpage",page);
			return "searchList";
		}
		
	}