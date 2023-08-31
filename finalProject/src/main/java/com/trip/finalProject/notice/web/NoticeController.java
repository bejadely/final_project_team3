package com.trip.finalProject.notice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
									,@RequestParam(value = "nowPage", defaultValue ="1")Integer nowPage
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
	
	//공지사항 게시글 상세보기
	@GetMapping("/admin/seeNoticeDetail")
	public String selectNoticeDetail(NoticeVO noticeVO, Model model) {
		
		// 공지사항 상세조회 실행
		noticeVO = noticeService.getNoticeDetail(noticeVO);
		model.addAttribute("noticeVO", noticeVO);
		System.out.println(noticeVO);
		return "notice/noticeDetail";
		
	}
	
	
	//검색
	@GetMapping("/noticeSearch")
	public String boardSearch() {
		return"notice/BoardList";
	};

	
	//공지사항 작성 폼 불러옴
	  @GetMapping("/noticeWrite")
	  public String noticedWrite() {
		  return"notice/noticeWriteForm"; 
	  };
	 
	//공지사항 작성후 DB저장
	@PostMapping("/noticeProc")
	public String noticeInsert(NoticeVO noticeVO) {
		noticeService.noticeInsert(noticeVO);
		return"redirect:/noticeList";
	};
	
	//공지사항 수정 폼 불러옴
    @PostMapping("/noticeEdit")
    public String noticedEdit(NoticeVO noticeVO, Model model) {
    	// 공지사항 상세조회 실행
    			noticeVO = noticeService.getNoticeDetail(noticeVO);
    			model.addAttribute("noticeVO", noticeVO);
    			System.out.println(noticeVO);
       return"notice/noticeEdit"; 
    };
    
	
	// 게시글 수정 기능 수행
	@PostMapping("/admin/modifyNoticeInfo")
	public String modifyNoticeInfo(NoticeVO noticeVO, RedirectAttributes rtt) {
		
		// 게시글 수정
		String result = noticeService.modifyNoticeInfo(noticeVO);
		
		// 리다이렉트 어트리뷰트에 결과값 담기(성공 : success / 실패 : fail)
		rtt.addFlashAttribute("result", result);
		
		return "redirect:seeNoticeDetail?noticeNumber=" + noticeVO.getNoticeNumber();
	}


	
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
	

	
	
	
	
}