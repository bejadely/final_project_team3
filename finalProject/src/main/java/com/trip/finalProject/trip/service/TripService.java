package com.trip.finalProject.trip.service;

import java.util.List;

public interface TripService {
	//여행기록 전체 조회
	public List<TripVO> getTripAll();
	
	//여행기록 등록
	public int InsertTripInfo(TripVO tripVO);
}
