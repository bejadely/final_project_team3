package com.trip.finalProject.trip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.mapper.TripMapper;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.trip.service.TripVO;

@Service
public class TripServiceImpl implements TripService {
	
	@Autowired
	TripMapper tripMapper;
	
	//여행기록 전체 조회 페이징용
	@Override
	public int tripRecordCount() {
		return tripMapper.getTotalCount();
	}
	
	//여행기록 전체 리스트 조회
	@Override
	public List<TripVO> getTripAll(PagingVO pagingVO) {
		return tripMapper.selectAllTrip(pagingVO);
	}

	//여행기록 상세조회
	@Override
	public TripVO getTripInfo(TripVO tripVO) {
		return tripMapper.selectTripInfo(tripVO);
	}
	
	//여행기록 등록
	@Override
	public int InsertTripInfo(TripVO tripVO) {
		return tripMapper.insertTripInfo(tripVO);
	}

	//여행기록 임시저장
	@Override
	public int TsInsertTripInfo(TripVO tripVO) {
		return tripMapper.tsInsertTripInfo(tripVO);
	}

	//여행기록 지도 맵핑
	@Override
	public int InsertTripMapping(TripVO tripVO) {
		return tripMapper.insertTripMapping(tripVO);
	}



}
