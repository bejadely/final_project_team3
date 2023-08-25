package com.trip.finalProject.adminMember.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trip.finalProject.adminMember.service.AdminMemberService;
import com.trip.finalProject.adminMember.service.AdminMemberVO;
import com.trip.finalProject.alert.service.AlertService;
import com.trip.finalProject.alert.service.AlertVO;
import com.trip.finalProject.authConfirm.service.AuthConfirmService;
import com.trip.finalProject.authConfirm.service.AuthConfirmVO;
import com.trip.finalProject.common.PagingVO;

@Controller
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	@Autowired
	AuthConfirmService authConfirmService;
	
	@Autowired
	AlertService alertService;
	
	// 회원정보 전체 조회
	@GetMapping("/seeAllMemberList")
	public String seeAllMember(Model model
			                 , @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			                 , @RequestParam(value = "cntPerPage", defaultValue = "10")Integer cntPerPage ){
		
		// 전체 조회될 회원 수 카운트
		int total = adminMemberService.memberCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		// 회원 전체 조회
		List<AdminMemberVO> list = adminMemberService.selectAllMember(pagingVO);
		
		// 모든 회원 정보 모델에 담기
		model.addAttribute("list", list);
		model.addAttribute("paging", pagingVO);
		
		return "admin/manageMember/seeAllMemberList";
	}
	
	// 특정 조건으로 회원 상세 검색
	@GetMapping("/searchAdminMember")
	public String searchAdminMember(@RequestParam( name = "searchBy" ) String searchBy, @RequestParam( name = "keyword" ) String keyword, Model model, AdminMemberVO adminMemberVO) {
		
		// 조건 파악
		if(searchBy.equals("name")) {
			
			// 이름으로 검색기능 수행
			adminMemberVO.setMemberName(keyword);
			List<AdminMemberVO> list = adminMemberService.searchMemberByName(adminMemberVO);
			model.addAttribute("list", list);
			
		} else if(searchBy.equals("id")) {
			
			// 아이디로 검색기능 수행
			adminMemberVO.setMemberId(keyword);
			List<AdminMemberVO> list = adminMemberService.searchMemberById(adminMemberVO);
			model.addAttribute("list", list);

		}
		
		return "admin/manageMember/seeAllMemberList";
	}
	
	// 회원상세정보 조회
	@GetMapping("/seeMemberDetail")
	public String seeMemberDetail(AdminMemberVO memberVO) {

		
		
		return "admin/manageMember/memberDetail";
	}
	
	// 권한 승인 요청 전체 조회
	@GetMapping("authRequestList")
	public String authRequestList(Model model) {
		
		// 권한승인 요청한 모든 내역 조회
		List<AdminMemberVO> list = adminMemberService.selectAllAuthRequest();
		
		// 권한승인한 모든 내역을 담은 리스트 모델에 담기
		model.addAttribute("list", list);
		
		System.out.println(model.getAttribute("list"));
		
		return "admin/auth/authRequestList";
	}
	
	// 권한 승인 + 승인 내역 저장
	@Transactional
	@PostMapping("/approveAuthRequest")
	public String approveAuthRequest(AdminMemberVO memberVO, AuthConfirmVO authVO, AlertVO alertVO, RedirectAttributes rtt) {
		
		// 권한승인 처리
		Map<String, String> map = adminMemberService.approveAuthRequest(memberVO.getMemberId());
		
		// 승인내역 저장을 위한 작업
		authVO.setRequesterId(memberVO.getMemberId());
		authConfirmService.insertApproveData(authVO);
		
		// 해당 멤버에게 요청결과 알림 발송
		alertVO.setContent("권한승인 요청이 승인되었습니다.");
		alertService.insertAlert(alertVO);
		
		// 처리결과 (success / fail) 값 담아서 보내기
		rtt.addFlashAttribute("approveResult", map.get("result"));
		rtt.addFlashAttribute("memberId", memberVO.getMemberId());
		
		return "redirect:authRequestList";
	}
	
	// 권한 승인 신청 반려 + 반려 내역 저장
	@Transactional
	@PostMapping("/rejectAuthRequest")
	public String rejectAuthRequest(AdminMemberVO memberVO, AuthConfirmVO authVO, AlertVO alertVO, RedirectAttributes rtt) {
		
		// 권한승인요청 반려 처리
		Map<String, String> map = adminMemberService.rejectAuthRequest(memberVO.getMemberId());
		
		// 반려내역 저장을 위한 작업
		authVO.setRequesterId(memberVO.getMemberId());
		authVO.setRejectReasonDetail(memberVO.getRejectReasonDetail());
		authConfirmService.insertRejectData(authVO);
		
		// 해당 멤버에게 요청결과 알림 발송
		alertVO.setContent("권한승인 요청이 반려되었습니다.");
		alertService.insertAlert(alertVO);
		
		// 처리결과 (Success / Fail) 값 담아서 보내기
		rtt.addFlashAttribute("rejectResult", map.get("result"));
		rtt.addFlashAttribute("memberId", memberVO.getMemberId());
		
		return "redirect:authRequestList";
	}
	
	
	
}
