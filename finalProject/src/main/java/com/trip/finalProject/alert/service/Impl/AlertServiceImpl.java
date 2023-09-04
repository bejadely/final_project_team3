package com.trip.finalProject.alert.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.alert.mapper.AlertMapper;
import com.trip.finalProject.alert.service.AlertService;
import com.trip.finalProject.alert.service.AlertVO;

@Service
public class AlertServiceImpl implements AlertService {
	
	@Autowired
	AlertMapper alertMapper;
	
	@Override
	public List<AlertVO> selectAllAlert() {
		// 알람 전체 조회
		return null;
	}

	@Override
	public int insertAlert(AlertVO vo) {
		// 새로운 알람 내역 추가
		
		return alertMapper.insertAlert(vo);
	}

}
