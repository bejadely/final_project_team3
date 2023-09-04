package com.trip.finalProject.main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.main.service.MainService;	

@Controller
public class MainController {

	
	@Autowired
	MainService mainservice;
	
	
	@GetMapping("/")
	public String getSpecialtiesInfo(Model model) {
		
		//세일상품 정보 가져오기
		model.addAttribute("saleItem", mainservice.getSpecialtiesInfo());
		
		return "index";
	}
}
