package com.trip.finalProject.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.admin.service.AdminMemberService;
import com.trip.finalProject.admin.service.AdminMemberVO;

@Controller
public class AdminController {
	
	@Autowired
	AdminMemberService ams;
	
	// 권한 승인 요청 전체 조회
	@GetMapping("authRequestList")
	public String authRequestList(Model model) {
		
		// 권한승인 요청한 모든 내역 조회
		List<AdminMemberVO> list = ams.selectAllAuthRequest();
		
		System.out.println(list);
		
		// 권한승인한 모든 내역을 담은 리스트 모델에 담기
		model.addAttribute("list", list);
		
		
		return "admin/authRequestList";
	}
	
}
