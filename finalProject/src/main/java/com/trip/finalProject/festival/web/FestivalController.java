package com.trip.finalProject.festival.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.festival.service.FestivalService;


@Controller
public class FestivalController {

	@Autowired
	FestivalService festivalService;
	
	//축제정보 페이지
	@GetMapping("/festival")
	public String getFestivalInfo() {
		
		return "festival/festivalInfo"; 
	}

	//축제정보 api로 받아와서 db등록
	@GetMapping("/test")
	public void getfestivalInfoAndSave(Model model) throws Exception {

		festivalService.getFestivalInfoAndSave();

	}
	
	//축제정보 수정
	
	
	//축제정보 삭제
	
	
}
