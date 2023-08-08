package com.trip.finalProject.trip.mapper;

import java.util.List;

import com.trip.finalProject.trip.service.TripVO;

public interface TripMapper {
	//여행기록 전체 조회
	public List<TripVO>	selectAllTrip();
	
	//여행기록 등록
	public int insertTripInfo(TripVO tripVO);
}
