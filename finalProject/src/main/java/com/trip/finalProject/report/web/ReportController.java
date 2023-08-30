package com.trip.finalProject.report.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.report.service.ReportService;
import com.trip.finalProject.report.service.ReportVO;

@Controller
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	// 새로운 신고내역 전체 조회 
	@GetMapping("/admin/seeAllReport")
	public String seeAllNewReport(Model model
			 , @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
			 , @RequestParam( name = "keyword", defaultValue = "" ) String keyword
            , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
            , @RequestParam( name = "cntPerPage", defaultValue = "10")Integer cntPerPage) {
		
		
		// 새로운 신고접수내역 전체 조회
		Map<String, Object> map = reportService.selectAllNewReport(nowPage, cntPerPage);
		
		// 모든 회원 정보 모델에 담기
		model.addAttribute("list", map.get("list"));
		model.addAttribute("paging", map.get("PagingVO"));
		
		// 검색어가 없을 경우를 대비한 구문
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchBy", searchBy);
		
		return "admin/report/seeAllreport";
	}
	
	// 신고내역 상세조회
	@GetMapping("/admin/seeReportDetail")
	public String seeReportDetail(ReportVO reportVO, Model model) {
		
		// 상세조회 수행 및 결과값 모델에 담기
		model.addAttribute("reportVO", reportService.getReportDetail(reportVO));
		
		return "admin/report/seeReportDetail";
	}
	
}
