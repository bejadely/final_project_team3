package com.trip.finalProject.report.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trip.finalProject.common.mapper.CommonMapper;
import com.trip.finalProject.report.service.ReportService;
import com.trip.finalProject.report.service.ReportVO;

@Controller
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	CommonMapper commonMapper;
	
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
		
		return "admin/report/seeAllReport";
	}
	
	// 신고내역 상세조회
	@GetMapping("/admin/seeReportDetail")
	public String seeReportDetail(ReportVO reportVO, Model model) {
		
		// 공통 코드 중 제재사유 코드 모두 읽어오기
		// List<Map<String, String>> punishReasonList = commonMapper.selectCode("P");
		
		// 상세조회 수행 및 결과값 모델에 담기
		model.addAttribute("reportVO", reportService.getReportDetail(reportVO));
		// model.addAttribute("reasonList", punishReasonList);
		
		return "admin/report/seeReportDetail";
	}
	
	// 신고 처리
	@PostMapping("/admin/punishProcess")
	public String punishProcess(ReportVO reportVO, RedirectAttributes rtt) {
		
		// 신고처리 서비스 호출
		String message = reportService.punishProcess(reportVO);
		
		// 처리결과 메세지 담기
		rtt.addFlashAttribute("message", message);
		
		return "redirect:/admin/seeAllReport";
	}
	
}
