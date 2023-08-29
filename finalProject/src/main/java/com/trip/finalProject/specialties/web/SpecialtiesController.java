package com.trip.finalProject.specialties.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.trip.finalProject.common.mapper.CommonMapper;
import com.trip.finalProject.specialties.service.SpecialtiesService;
import com.trip.finalProject.specialties.service.SpecialtiesVO;

@Controller
public class SpecialtiesController {
	@Autowired
	SpecialtiesService specialtiesService;
	
	@Autowired
	CommonMapper commonMapper;
	
	@GetMapping("/specialtiesInsertForm")
	public String package2(Model model) {
		model.addAttribute("S",commonMapper.selectCode("S"));
		model.addAttribute("area",specialtiesService.getLocationList());
		return "specialties/specialtiesInsertForm";
	}
	
	@PostMapping("/specialtiesInsert")
	public String specialtiesInsert(SpecialtiesVO specialtiesVO) {
		specialtiesService.insertSepcialties(specialtiesVO);
		
		return "redirect:/specialtiesList";
	}
	
	@GetMapping("/specialtiesList")
	public String specialtiesList(Model model) {
		model.addAttribute("specialtiesList",specialtiesService.getSpecialtiesList());
		return "specialties/specialtiesList";
	}
}
