package com.trip.finalProject.festival.web;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.festival.service.FestivalInfoVO;
import com.trip.finalProject.festival.service.FestivalService;

//오유리, 2023년 08월, 축제정보페이지
@Controller
public class FestivalController {

	@Autowired
	FestivalService festivalService;
	
	//축제정보 페이지
	@GetMapping("/festival")
	public String getFestivalInfo(Model model) {

		model.addAttribute("festivalCalendar", festivalService.getFestivalCalendarInfo());
		
		return "festival/festivalInfo"; 
	}

	//축제정보 조회(첫페이지)
	
	
	//축제정보 등록
	
	
	//축제정보 수정
	
	
	//축제정보 삭제
	
	
	//축제정보 api로 받아와서 db등록 : 테스트용(없어도 됨)
	@GetMapping("/test")
	public void getfestivalInfoAndSave(Model model) throws Exception {

		festivalService.getFestivalInfoAndSave();

	}
}
