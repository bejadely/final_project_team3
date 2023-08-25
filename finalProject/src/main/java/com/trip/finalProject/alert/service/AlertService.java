package com.trip.finalProject.alert.service;

import java.util.List;

public interface AlertService {
	
	// 알람 전체 조회
	public List<AlertVO> selectAllAlert();
	
	// 새로운 알람 내역 추가
	public int insertAlert(AlertVO vo);
	
}
