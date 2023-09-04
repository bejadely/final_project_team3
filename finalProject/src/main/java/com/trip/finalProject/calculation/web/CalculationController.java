package com.trip.finalProject.calculation.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trip.finalProject.calculation.service.CalculationService;
import com.trip.finalProject.calculation.service.CalculationVO;

@Controller
public class CalculationController {
	
	@Autowired
	CalculationService calculationService;
	
	
	// 미정산 내역 전체 조회폼 호출
	@GetMapping("/admin/selectNotCalList")
	public String selectNotCalList(Model model
					            , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
					            , @RequestParam( name = "cntPerPage", defaultValue = "10")Integer cntPerPage) {
		
		// 미정산 내역 전체 조회
		Map<String, Object> map = calculationService.selectNotCalList(nowPage, cntPerPage);
		
		// 처리결과 담기
		model.addAttribute("list", map.get("list"));
		model.addAttribute("paging", map.get("PagingVO"));
		
		
		return "admin/calculation/calculation";
	}
	
	// 일괄 정산
	@PostMapping("/admin/calAtOnce")
	public String calProc(CalculationVO calculationVO, RedirectAttributes rtt) {
		
		// 정산 처리 서비스 실행 (처리결과가 문자열로 반환)
		String result = calculationService. calAtOnce(calculationVO);
		rtt.addFlashAttribute("message", result);
		
		return "redirect:/admin/selectNotCalList";
	}
	
	
}
