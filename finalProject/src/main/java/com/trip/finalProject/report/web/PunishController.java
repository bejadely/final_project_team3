package com.trip.finalProject.report.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.report.service.PunishService;

@Controller
public class PunishController {
	
	@Autowired
	PunishService punishService;
	
	// 제재 처리 내역 전체 조회
	@GetMapping("/admin/seeAllPunish")
	public String seeAllPunish(Model model
							 , @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
							 , @RequestParam( name = "keyword", defaultValue = "" ) String keyword
				             , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
				             , @RequestParam( name = "cntPerPage", defaultValue = "10")Integer cntPerPage) {
		
		// 새로운 신고접수내역 전체 조회
		 Map<String, Object> map = punishService.selectAllPunishList(nowPage, cntPerPage);
		
		// 모든 회원 정보 모델에 담기
		model.addAttribute("list", map.get("list"));
		model.addAttribute("paging", map.get("PagingVO"));
		
		// 검색어가 없을 경우를 대비한 구문
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchBy", searchBy);
		
		
		return "admin/report/seeAllPunish";
	}
	
}
