package com.trip.finalProject.notice.web;

import java.util.List;

import javax.servlet.http.HttpSession;

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
									,@RequestParam(value= "cntPerPage",defaultValue="10")Integer cntPerPage
									,	HttpSession session) {
	String sessionAuthority = (String) session.getAttribute("sessionAuthority");
	//권한에 따라 볼 수 있는 기본 게시글 갯수 카운팅
	int total = noticeService.listCount(sessionAuthority);
	
	PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
	System.out.println("나우페이지"+nowPage);
	//전제 공지사항 리스트 조회
	List<NoticeVO> list = noticeService.SelectAllNoticeList(sessionAuthority,pagingVO);
	// 모든 리스트 모델에 담기
	model.addAttribute("list", list);
	model.addAttribute("paging", pagingVO);	    
	return"notice/noticeList";
	};
	
	
		
	//공지사항 게시글 상세보기
	@GetMapping("/seeNoticeDetail")
	public String selectNoticeDetail(NoticeVO noticeVO, Model model) {		
		//공지사항 조회수 증가
		noticeService.updateNoticeHit(noticeVO);
		// 공지사항 상세조회 실행
		noticeVO = noticeService.getNoticeDetail(noticeVO);
		model.addAttribute("noticeVO", noticeVO);
		System.out.println("공지사항"+noticeVO);
		return "notice/noticeDetail";
		
	}
	

	//공지사항 작성 폼 불러옴
	  @GetMapping("/admin/noticeWrite")
	  public String noticedWrite() {
		  return"notice/noticeWriteForm"; 
	  };
	 
	//공지사항 작성후 DB저장
	@PostMapping("/admin/noticeProc")
	public String noticeInsert(NoticeVO noticeVO) {
		noticeService.noticeInsert(noticeVO);
		return"redirect:/noticeList";
	};
	
	//공지사항 수정 폼 불러옴
    @PostMapping("/admin/noticeEdit")
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
		
		System.out.println("인풋" + noticeVO);
		
		// 게시글 수정
		String result = noticeService.modifyNoticeInfo(noticeVO);
		
		// 리다이렉트 어트리뷰트에 결과값 담기(성공 : success / 실패 : fail)
		rtt.addFlashAttribute("result", result);
		
		return "redirect:/seeNoticeDetail?noticeNumber=" + noticeVO.getNoticeNumber();
	}


	
	//게시글 삭제 기능 수행
	///admin/noticeDelete
	
	
	
	// 특정 조건으로 공지사항 상세 검색
	@GetMapping("/searchNotice")
	public String searchAdminMember( String noticeType
								  ,  String release
								  ,  String status		
								   ,@RequestParam( name = "keyword" ) String keyword
								  , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
								  , @RequestParam( name = "cntPerPage", defaultValue = "10") Integer cntPerPage
								  , Model model
								  , NoticeVO noticeVO) {
		
		
		System.out.println("노티스타입:"+noticeType);
		System.out.println("공개대상:"+release);
		System.out.println("공개상태:"+status);
		// 조건 파악 공지사항 or 이벤트
			
			// 전체 조회될 카운트
			int total = noticeService.countNoticeBySearch(noticeVO);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			
			//  검색기능 수행
			noticeVO.setTitle(keyword);
			List<NoticeVO> list = noticeService.searchNoticeByTitle(noticeVO, pagingVO);
			model.addAttribute("list", list);
			model.addAttribute("paging", pagingVO);
			
			

		// 검색결과 기억을 위해 keyword와 searchBy 담기
		model.addAttribute("keyword", keyword);
		model.addAttribute("noticeType", noticeType);
		model.addAttribute("release", release);
		model.addAttribute("status", status);
		/*
		 * System.out.println("--------------"); System.out.println("keyword"+keyword);
		 * System.out.println("noticeType"+noticeType);
		 * System.out.println("release"+release); System.out.println("status"+status);
		 */
		return "notice/noticeList";
	}

	
	
	
	
}