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
	
	//여행기록 상세조회
	public TripVO getTripInfo(TripVO tripVO);
	
	//여행기록 등록(임시저장 되어있는 게시글을 저장 상태로 업데이트)
	public int InsertTripInfo(TripVO tripVO);
	
	//여행기록 임시저장(임시저장 되어있는 게시글을 추가된 내용을 가지고 다시 업데이트)
	public int TsTripInfo(TripVO tripVO);
	
	//전체조회 페이지에서 여행기록 등록 버튼 클릭 시 실행(임시저장 상태로 등록)
	public TripVO TsInsertTripInfo(TripVO tripVO);
	
	//여행기록 삭제
	
	
	//여행기록 지도 맵핑
	public int InsertTripMapping(TripVO tripVO);
	
}
