package com.trip.finalProject.lodging.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.lodging.service.LodgingService;


@Controller
public class LodgingController {
	@Autowired
	LodgingService lodgingService;
	
	@GetMapping("lodging")
	public String lodgingList(Model model) {
		model.addAttribute("lodgingList",lodgingService.getLodgingList());
		return"lodging/lodging";
	}
	@GetMapping("apitest")
	public String apitest() {
		return"lodging/apitest";
	}
	
}
