package com.trip.finalProject.trip.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripVO;

public interface TripMapper {
	//여행기록 전체조회 페이징용
	public int getTotalCount();

	//여행기록 전체 조회
	public List<TripVO>	selectAllTrip(PagingVO pagingVO);
	
	//여행기록 등록
	public int insertTripInfo(TripVO tripVO);
}
