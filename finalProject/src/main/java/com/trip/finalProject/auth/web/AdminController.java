package com.trip.finalProject.auth.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trip.finalProject.auth.service.AdminMemberService;
import com.trip.finalProject.auth.service.AdminMemberVO;
import com.trip.finalProject.authConfirm.service.AuthConfirmService;
import com.trip.finalProject.authConfirm.service.AuthConfirmVO;

@Controller
public class AdminController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	@Autowired
	AuthConfirmService authConfirmService;
	
	// 권한 승인 요청 전체 조회
	@GetMapping("/authRequestList")
	public String authRequestList(Model model) {
		
		// 권한승인 요청한 모든 내역 조회
		List<AdminMemberVO> list = adminMemberService.selectAllAuthRequest();
		
		// 권한승인한 모든 내역을 담은 리스트 모델에 담기
		model.addAttribute("list", list);
		
		System.out.println(model.getAttribute("list"));
		
		return "admin/authRequestList";
	}
	
	// 권한 승인 + 승인 내역 저장
	@Transactional
	@PostMapping("/approveAuthRequest")
	public String approveAuthRequest(AdminMemberVO adminVO, RedirectAttributes rtt) {
		
		// 권한승인 처리
		Map<String, String> map = adminMemberService.approveAuthRequest(adminVO.getMemberId());
		
		// 승인내역 저장을 위한 작업
		AuthConfirmVO authVO = new AuthConfirmVO();
		authVO.setRequesterId(adminVO.getMemberId());
		authConfirmService.insertApproveData(authVO);
		
		// 처리결과 (Success / Fail) 값 담아서 보내기
		rtt.addFlashAttribute("result", map.get("result"));
		rtt.addFlashAttribute("memberId", adminVO.getMemberId());
		
		return "redirect:authRequestList";
	}
	
	
}
