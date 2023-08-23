package com.trip.finalProject.notice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.trip.finalProject.notice.service.NoticeService;
import com.trip.finalProject.notice.service.NoticeVO;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

   
	@GetMapping("/boardList")
	public String selectBoardList() {
		return"notice/BoardList";
	};
	@GetMapping("/boardSearch")
	public String boardSearch() {
		return"notice/BoardList";
	};
	@GetMapping("/boardWrite")
	public String boardWrite() {
		return"notice/BoardList";
	};
	@GetMapping("/boardInsert")
	public String boardInsert() {
		return"notice/BoardList";
	};
	@GetMapping("/boardSelect")
	public String boardSelect() {
		return"notice/BoardList";
	};
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