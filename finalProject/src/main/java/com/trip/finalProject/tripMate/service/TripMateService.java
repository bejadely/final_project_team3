package com.trip.finalProject.tripMate.service;

import java.util.List;

public interface TripMateService {
	//여행 메이트 글 전체조회 - 페이징은 다른 기능이 어느정도 구현이 되고 나서 검색이 가능한 페이징으로 추가 해야함.
	public List<TripMateVO> getTripMateAll();
	
	//여행 메이트 글 상세조회
	public TripMateVO getTripMateInfo(TripMateVO tripMateVO);
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	public TripMateVO InsertTripMateApply(TripMateVO tripMateVO); 
}
