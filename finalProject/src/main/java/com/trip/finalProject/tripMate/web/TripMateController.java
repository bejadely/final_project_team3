package com.trip.finalProject.tripMate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.trip.finalProject.tripMate.service.TripMateService;
import com.trip.finalProject.tripMate.service.TripMateVO;

@Controller
public class TripMateController {
	@Autowired
	TripMateService tripMateService;
	
	//여행 메이트 글 전체 조회
	@GetMapping("tripMateList")
	public String tripMateList(Model model) {
		model.addAttribute("tripMateList", tripMateService.getTripMateAll());
		return "tripMate/tripMateList";
	}
	
	//여행 메이트  글 상세 조회
	@GetMapping("tripMateInfo")
	public String tripMateInfo(TripMateVO tripMateVO, Model model) {
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		model.addAttribute("tripMateInfo", findVO);
		return "tripMate/tripMateInfo";
	}
	
	//여행 메이트 신청 - form
	@PostMapping("tripMateApplyForm")
	public String tripMateForm(Model model) {
		model.addAttribute("tripMateVO", new TripMateVO());
		return "tripMate/tripMateApplyForm";
	}
	
	//여행 메이트 신청 - process
	@PostMapping("tripMateApplyInsert")
	public String tripMateApplyInsert(TripMateVO tripMateVO, Model model) {
		tripMateService.InsertTripMateApply(tripMateVO);
		return "redirect:tripMateList";
	}
	
}
