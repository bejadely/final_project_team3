package com.trip.finalProject.tripMate.mapper;

import java.util.List;

import com.trip.finalProject.tripMate.service.TripMateVO;

public interface TripMateMapper {
	//여행 메이트 글 전체조회
	public List<TripMateVO> selectAllTripMate();
	
	//여행 메이트 글 상세조회
	public TripMateVO selectTripMateInfo(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 등록
	public int insertEditor(TripMateVO tripMateVO);
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	public int insertTripMateApply(TripMateVO tripMateVO); 
}
