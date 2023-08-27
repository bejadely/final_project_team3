package com.trip.finalProject.notice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.adminMember.service.AdminMemberVO;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.notice.service.NoticeService;
import com.trip.finalProject.notice.service.NoticeVO;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	
	
	//공지사항 리스트 조회
	@GetMapping("/noticeList")
	public String selectNoticeList(Model model
									, @RequestParam(value = "nowPage", defaultValue ="1")Integer nowPage
									,@RequestParam(value= "cntPerPage",defaultValue="10")Integer cntPerPage) {
		
		
		int total = noticeService.listCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		//전제 공지사항 리스트 조회
		List<NoticeVO> list = noticeService.SelectAllNoticeList(pagingVO);
		
		// 모든 리스트 모델에 담기
				model.addAttribute("list", list);
				model.addAttribute("paging", pagingVO);
		return"notice/NoticeList";

	};
	
	
	
	//검색
	@GetMapping("/noticeSearch")
	public String boardSearch() {
		return"notice/BoardList";
	};

	
	//공지사항 작성 폼 불러옴
	  @GetMapping("/noticeWrite")
	  public String boardWrite() {
		  return"notice/noticeWriteForm"; 
	  };
	 
	//공지사항 작성후 DB저장
	@PostMapping("/noticeProc")
	public String boardInsert(NoticeVO noticeVO) {
		noticeService.noticeInsert(noticeVO);
		return"notice/NoticeList";
	};
	
	/*
	 * //공지사항 리스트 불러오기 list에 뿌리기
	 * 
	 * @GetMapping("/boardSelect") public String boardSelect() {
	 * return"notice/BoardList"; };
	 */
	@GetMapping("/boardEdit")
	public String boardEdit() {
		return"notice/BoardList";
	};
	@GetMapping("/boardUpdate")
	public String boardUpdate() {
		return"notice/BoardList";
	};
	@GetMapping("/boardDelete")
	public String boardDelete() {
		return"notice/BoardList";
	};
	

	@GetMapping("/notice") 
	public String memberInsertForm() {
		return"notice/noticeBoard";
	}
	
	
	@PostMapping("/notice")
	public String noticeInsert(NoticeVO noticeVO) throws Exception { 
		
		//Service를 호출하여 insertPmtNtmForm() 실행
		noticeService.insertpost(noticeVO);
		 // 공지사항 등록 후 리스트 화면으로 이동
			
		// view에 결과 넘김
		return "redirect:/";
	}
	
	
	
	
}