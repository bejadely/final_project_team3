package com.trip.finalProject.trip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.trip.mapper.TripMapper;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.trip.service.TripVO;

@Service
public class TripServiceImpl implements TripService {
	
	@Autowired
	TripMapper tripMapper;
	
	//여행기록 전체 리스트 조회
	@Override
	public List<TripVO> getTripAll() {
		return tripMapper.selectAllTrip();
	}

	//여행기록 등록
	@Override
	public int InsertTripInfo(TripVO tripVO) {
		return tripMapper.insertTripInfo(tripVO);
	}
	

}
