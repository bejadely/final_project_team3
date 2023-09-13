package com.trip.finalProject.calculation.service;

import java.util.Map;

public interface CalculationService {
	
	// 미정산 내역 전체 조회
	public Map<String, Object> selectNotCalList(Integer nowPage, Integer cntPerPage);
	
	// 일괄 정산 처리
	public String calAtOnce(CalculationVO calculationVO);
	
	// 특정월 정산 내역 조회
	public Map<String, Object> selectCompCalList(Integer nowPage, Integer cntPerPage, String searchYear, String searchMonth);
	
}
