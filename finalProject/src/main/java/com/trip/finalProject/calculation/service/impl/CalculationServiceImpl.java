package com.trip.finalProject.calculation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.calculation.mapper.CalculationMapper;
import com.trip.finalProject.calculation.service.CalculationService;

@Service
public class CalculationServiceImpl implements CalculationService {
	
	@Autowired
	CalculationMapper calculationMapper;
	
}
