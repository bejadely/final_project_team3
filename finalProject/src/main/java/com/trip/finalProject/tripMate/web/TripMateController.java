package com.trip.finalProject.tripMate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.attachedFile.service.AttachedFileService;
import com.trip.finalProject.attachedFile.service.AttachedFileVO;
import com.trip.finalProject.tripMate.service.TripMateService;
import com.trip.finalProject.tripMate.service.TripMateVO;

@Controller
public class TripMateController {
	@Autowired
	TripMateService tripMateService;
	
	@Autowired
	AttachedFileService attachedFileService;
	
	//여행 메이트 글 전체 조회
	@GetMapping("tripMateList")
	public String tripMateList(Model model) {
		model.addAttribute("tripMateList", tripMateService.getTripMateAll());
		return "tripMate/tripMateList";
	}
	
	//여행 메이트 글 상세 조회
	@GetMapping("tripMateInfo")
	public String tripMateInfo(TripMateVO tripMateVO, Model model) {
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		model.addAttribute("tripMateInfo", findVO);
		return "tripMate/tripMateInfo";
	}
	
	//여행 메이트 글 등록 - form
	@GetMapping("tripMateRecruitForm")
	public String tripMateRecruitForm(Model model) {
		return "tripMate/tripMateRecruitForm";
	}
	
	//여행 메이트 글 등록 - process
	@PostMapping("tripMateRecruitInsert")
	public ModelAndView tripMateRecruitInsert(TripMateVO tripMateVO) {
		tripMateService.insertTripMateRecruit(tripMateVO);
		ModelAndView mv = new ModelAndView("redirect:/tripMateList");
		return mv;
	}
	
	@GetMapping("getAttach")
	@ResponseBody
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo){
		System.out.println(vo.getPostId());
		return attachedFileService.getAttachList(vo);
	}
	
	//여행 메이트 신청 - form
	@PostMapping("tripMateApplyForm")
	public String tripMateApplyForm(TripMateVO trvo, Model model) {
		model.addAttribute("tripMateVO", trvo );
		return "tripMate/tripMateApplyForm";
	}
	
	//여행 메이트 신청 - process
	@PostMapping("tripMateApplyInsert")
	public String tripMateApplyInsert(TripMateVO tripMateVO, Model model) {
		tripMateService.InsertTripMateApply(tripMateVO);
		return "redirect:tripMateList";
	}
	
}