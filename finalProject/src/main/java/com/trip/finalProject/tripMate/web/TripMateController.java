package com.trip.finalProject.tripMate.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.attachedFile.service.AttachedFileService;
import com.trip.finalProject.attachedFile.service.AttachedFileVO;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.tripMate.service.TripMateService;
import com.trip.finalProject.tripMate.service.TripMateVO;

@Controller
public class TripMateController {
	@Autowired
	TripMateService tripMateService;
	
	@Autowired
	AttachedFileService attachedFileService;
	
	//여행 메이트 게시글 전체 조회
	@GetMapping("/tripMateList")
	public String tripMateList(Model model
			 				, @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
			 				, @RequestParam( name = "keyword", defaultValue = "" ) String keyword
			 				, @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
			 				, @RequestParam( name = "cntPerPage", defaultValue = "12")Integer cntPerPage ) {
		
		int total = tripMateService.mateCount();
		
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		List<TripMateVO> list = tripMateService.getTripMateAll(pagingVO);
		model.addAttribute("tripMateList", list);
		model.addAttribute("paging", pagingVO);
		
		// 검색어가 없을 경우를 대비한 구문
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("searchBy", searchBy);
		
		return "tripMate/tripMateList";
	}
	
	//특정 조건으로 여행 메이트 게시글 조회
	@GetMapping("/searchTripMate")
	public String searchTripMate(@RequestParam( name = "searchBy" ) String searchBy
			  					, @RequestParam( name = "keyword" ) String keyword
			  					, @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
			  					, @RequestParam( name = "cntPerPage", defaultValue = "12") Integer cntPerPage
			  					, Model model
			  					, TripMateVO tripMateVO) {
		//조건 설정
		if(searchBy.equals("tripArea")) {
			//여행지역 전체 카운트 수
			int total = tripMateService.countTripArea(keyword);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			
			//여행 지역으로 검색
			tripMateVO.setTripArea(keyword);
			List<TripMateVO> list = tripMateService.searchMateByTripArea(tripMateVO, pagingVO);
			model.addAttribute("tripMateList", list);
			model.addAttribute("paging", pagingVO);
			
		} else if(searchBy.equals("mateStyle")) {
			//여행스타일 전체 카운트 수
			int total = tripMateService.countTripStyle(keyword);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			
			//여행 타이틀로 검색
			tripMateVO.setMateStyle(keyword);
			List<TripMateVO> list = tripMateService.searchMateByStyle(tripMateVO, pagingVO);
			model.addAttribute("tripMateList", list);
			model.addAttribute("paging", pagingVO);
			
		} else if(searchBy.equals("mateTitle")) {
			//여행타이틀 전체 카운트 수
			int total = tripMateService.countTripTitle(keyword);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			
			//여행 타이틀로 검색
			tripMateVO.setMateTitle(keyword);
			List<TripMateVO> list = tripMateService.searchMateByTripTitle(tripMateVO, pagingVO);
			model.addAttribute("tripMateList", list);
			model.addAttribute("paging", pagingVO);
			
		} else if(searchBy.equals("writerId")) {
			//작성자 아이디 전체 카운트 수
			int total = tripMateService.countTripWrtierId(keyword);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			
			//작성자 아이디로 검색
			tripMateVO.setWriterId(keyword);
			List<TripMateVO> list = tripMateService.searchMateByTripWriterId(tripMateVO, pagingVO);
			model.addAttribute("tripMateList", list);
			model.addAttribute("paging", pagingVO);
		} 
		// 검색결과 기억을 위해 keyword와 searchBy 담기
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchBy", searchBy);
		
		return "tripMate/tripMateList";
	}
	
	
	
	//여행 메이트 게시글 상세 조회
	@GetMapping("/tripMateInfo")
	public String tripMateInfo(TripMateVO tripMateVO, Model model) {
		//조회수 카운트
		tripMateService.updateMateRecruitHit(tripMateVO);
		
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		
		model.addAttribute("tripMateInfo", findVO);
		
		//댓글, 대댓글 리스트 가져오기
		model.addAttribute("commentList", tripMateService.getCommentInfo(tripMateVO));
		
		return "tripMate/tripMateInfo";
	}
	
	//여행 메이트 게시글 등록 - form
	@GetMapping("/common/tripMateRecruitForm")
	public String tripMateRecruitForm(Model model) {
		return "tripMate/tripMateRecruitForm";
	}
	
	//여행 메이트 게시글 등록 - process
	@PostMapping("/common/tripMateRecruitInsert")
	public ModelAndView tripMateRecruitInsert(TripMateVO tripMateVO) {
		tripMateService.insertTripMateRecruit(tripMateVO);
		ModelAndView mv = new ModelAndView("redirect:/tripMateList");
		return mv;
	}
	
	//첨부파일 상세정보(파일 여러개를 보여주기 위해서)
	@GetMapping("/getAttach")
	@ResponseBody
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo){
		System.out.println(vo.getPostId());
		return attachedFileService.getAttachList(vo);
	}
	
	//여행 메이트 게시글 삭제
	@Transactional
	@GetMapping("/common/mateRecruitDelete")
	public String mateRecruitDelete(TripMateVO tripMateVO) {
		//여행 메이트 글 삭제 시 해당 게시글과 관련된 첨부파일 테이블 데이터 삭제
		tripMateService.deleteAttachedFile(tripMateVO);
		tripMateService.deleteTripMateRecruit(tripMateVO);
		return "redirect:/tripMateList";
	}
	
	
	//여행 메이트 게시글 수정 - form
	@GetMapping("/common/mateRecruitUpdateForm")
	public String mateRecruitUpdateForm(TripMateVO tripMateVO, Model model) {
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		model.addAttribute("tripMateVO", findVO);
		return "tripMate/tripMateUpdate";
	}
	
	
	//여행 메이트 게시글 수정 - process
	@PostMapping("/common/mateRecruitUpdate")
	public String mateRecruitUpdateProcess(TripMateVO tripMateVO){
		tripMateService.updateTripMateRecruit(tripMateVO);
		return "redirect:/tripMateList";
	}
	
	//여행 메이트 게시글 신고 - form
	@GetMapping("/common/mateRecruitReportForm")
	public String mateRecruitReportForm(TripMateVO tripMateVO, Model model) {
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		model.addAttribute("tripMateVO", findVO);
		return "tripMate/mateRecruitReportForm";
	}
	
	//여행 메이트 게시글 신고 - process
	@PostMapping("/common/mateRecruitReport")
	public String mateRecruitReport(TripMateVO tripMateVO) {
		tripMateService.reportTripMate(tripMateVO);
		return "redirect:/tripMateList";
	}
	
	//여행 메이트 신청 - form
	@PostMapping("/common/tripMateApplyForm")
	public String tripMateApplyForm(TripMateVO trvo,  
			@RequestParam(name="mateWriter") String mateWriter, Model model) {
		trvo.setMemberId(mateWriter);
		//System.out.println(trvo.getMemberId());
		model.addAttribute("mateWriter", trvo.getMemberId());
		
		model.addAttribute("mateVO", trvo );
		return "tripMate/tripMateApplyForm";
	}
	
	//여행 메이트 신청 - process
	@Transactional
	@PostMapping("/common/tripMateApplyInsert")
	public String tripMateApplyInsert(TripMateVO tripMateVO, 
			@RequestParam(name="mateWriter") String mateWriter, Model model) {
		//최대 인원, 신청 인원 조회
		//tripMateService.selectMateRecruitApplyNum(tripMateVO);
		//메이트 신청
		tripMateService.InsertTripMateApply(tripMateVO);
		
		//신청인원 업데이트
		tripMateService.updateMateRecruitApplyNum(tripMateVO);
		
		tripMateVO.setMemberId(mateWriter);
		//게시글 작성자에게 알림
		tripMateService.sendAlert(tripMateVO);
		
		return "redirect:/tripMateList";			
	}
	
	
	//마이페이지----------------------------------------------------------------------
	//내가 적성한 메이트
	@GetMapping("/common/myPageMate")
	public String tripMateList(Model model,
			TripMateVO trVO,
			@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		String memberId = "1";
		int total = tripMateService.myTripCount(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		trVO.setWriterId(memberId);
		List<TripMateVO> myTipPageList = tripMateService.myMateList(trVO, pagingVO);

		model.addAttribute("list", myTipPageList);
		model.addAttribute("paging", pagingVO);

		return "myPage/mate/myTripMate";
	}
	
	//내가 작성한 메이트 상세조회
	//여행 메이트 게시글 상세 조회
	@GetMapping("/common/tripMateMyInfo")
	public String tripMateMyInfo(TripMateVO tripMateVO, Model model) {
		
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		List<TripMateVO> memberVO = tripMateService.getTripMateMyInfo(tripMateVO);
		model.addAttribute("tripMateInfo", findVO);
		model.addAttribute("member", memberVO);
		
		return "myPage/mate/tripMateMyInfo";
	}
	
	
	//신청한 메이트 조회
	@GetMapping("/common/myPageAppMate")
	public String tripMateAppList(Model model,
			TripMateVO trVO,
			@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		String memberId = "leesw";
		int total = tripMateService.myTripAppCount(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		trVO.setMemberId(memberId);
		List<TripMateVO> myTipPageList = tripMateService.myMateAppList(trVO, pagingVO);
		
		model.addAttribute("list", myTipPageList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/mate/myTripAppMate";
	}
	//신청한 메이트 취소
	@PostMapping("common/myPageCancle")
	public String tripMateCancle(TripMateVO tripVO
			,@RequestParam("applyId") String applyId
			, @RequestParam("postId") String postId) {
		tripVO.setPostId(postId);
		tripVO.setApplyId(applyId);
		
		tripMateService.myMateCancle(tripVO);
		tripMateService.myTripnum(tripVO);
		return "redirect:/common/myPageAppTrip";
	}
	
	//ajax로 데이터 삭제
	@PostMapping("common/myPageMyCancle")
	@ResponseBody
	public Map<String, Object> tripMyMateCancle(TripMateVO tripVO) {
		HashMap<String, Object> map = new HashMap<>();
		
		tripMateService.myTripnum(tripVO);
		tripMateService.myMateCancle(tripVO);
		
		map.put("data", tripVO);
		
		return map;
	}
	
	//멤버 데이터 불러오기
	@GetMapping("/common/ajaxMember")
	@ResponseBody
	public TripMateVO tripMemberInfo(TripMateVO tripVO) {
		return tripMateService.memberInfo(tripVO);
	}
	
}
