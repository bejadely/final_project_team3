package com.trip.finalProject.tripMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.tripMate.mapper.TripMateMapper;
import com.trip.finalProject.tripMate.service.TripMateService;
import com.trip.finalProject.tripMate.service.TripMateVO;

@Service
public class TripMateServiceImpl implements TripMateService {
	
	@Autowired
	TripMateMapper tripMateMapper;
	
	//여행 메이트 글 전체 조회
	@Override
	public List<TripMateVO> getTripMateAll() {
		return tripMateMapper.selectAllTripMate();
	}

	//여행 메이트 글 상세 조회
	@Override
	public TripMateVO getTripMateInfo(TripMateVO tripMateVO) {
		return tripMateMapper.selectTripMateInfo(tripMateVO);
	}

	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	@Override
	public TripMateVO InsertTripMateApply(TripMateVO tripMateVO) {
		tripMateMapper.insertTripMateApply(tripMateVO);
		return tripMateVO;
	}

}
