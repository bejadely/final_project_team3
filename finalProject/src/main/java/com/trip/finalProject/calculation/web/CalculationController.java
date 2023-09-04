package com.trip.finalProject.calculation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.trip.finalProject.calculation.service.CalculationService;

@Controller
public class CalculationController {
	
	@Autowired
	CalculationService calculationService;
}
