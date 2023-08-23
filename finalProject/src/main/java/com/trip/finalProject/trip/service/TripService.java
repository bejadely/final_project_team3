package com.trip.finalProject.trip.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface TripService {
	//여행기록 전체조회 페이징용
    public int tripRecordCount();
	
	//여행기록 전체 조회
	public List<TripVO> getTripAll(PagingVO pagingVO);
	
	//여행기록 회원 조회
	public List<TripVO> getTripPer(PagingVO pagingVO);
	
	//여행기록 등록
	public int InsertTripInfo(TripVO tripVO);
	
	//여행기록 임시저장
	public int TsInsertTripInfo(TripVO tripVO);
}
