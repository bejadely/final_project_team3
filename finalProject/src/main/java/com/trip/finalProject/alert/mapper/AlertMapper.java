package com.trip.finalProject.alert.mapper;

import java.util.List;

import com.trip.finalProject.alert.service.AlertVO;

public interface AlertMapper {
	
	// 알람 전체 조회
	public List<AlertVO> selectAllAlert();
	
	// 새로운 알람 내역 추가
	public int insertAlert(AlertVO vo);
	
}
