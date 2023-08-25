package com.trip.finalProject.festival.web;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.festival.service.FestivalInfoVO;
import com.trip.finalProject.festival.service.FestivalService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//오유리, 2023년 08월, 축제정보페이지
@Controller
public class FestivalController {

	@Autowired
	FestivalService festivalService;
	
	//축제정보 페이지(첫페이지)
	@GetMapping("/festival")
	public String getFestivalInfo(Model model) {

		//캘린더 정보 받아오기
		model.addAttribute("festivalCalendar", festivalService.getFestivalCalendarInfo());
		
		//리스트 정보 받아오기
		model.addAttribute("festivalList", festivalService.getFestivalListInfo());
		
		return "festival/festivalInfo"; 
	}
	
	//월이동에 따른 리스트 정보 조회
	
	
	//축제정보 수정
	
	
	//축제정보 삭제
	
	
	//모달창 내 소개정보 api로 조회
	@GetMapping("/content")
	@ResponseBody
	public String getFestivalContent(String contentId) {
		System.out.println("컨트롤러 단에서"+contentId);
		
		return festivalService.getFestivalContent(contentId);
	}
	
	//축제정보 api로 받아와서 db등록 : 테스트용(없어도 됨)
	@GetMapping("/test")
	@ResponseBody
	public void getFestivalInfoAndSave(Model model) throws Exception {

		festivalService.getFestivalInfoAndSave();

	}
}
