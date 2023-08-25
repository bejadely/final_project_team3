package com.trip.finalProject.trip.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface TripService {
	//여행기록 전체조회 페이징용
    public int tripRecordCount();
    
    //마이페이지 페이징용
    public int tripPerCount();
    
    //마이페이지 페이징용 - 임시저장
    public int tripPerNotCount();
    
    //마이페이지 페이징용 - 완료된 여행
    public int tripPerComCount();
	
	//여행기록 전체 조회
	public List<TripVO> getTripAll(PagingVO pagingVO);
	
	//여행기록 회원 조회 - 미완료 여행
	public List<TripVO> getTripPer(PagingVO pagingVO);

	//여행기록 회원 조회 - 임시저장
	public List<TripVO> getTripPerNot(PagingVO pagingVO);
	
	//여행기록 회원 조회 - 미완료 여행
	public List<TripVO> getTripPerCom(PagingVO pagingVO);
	
	//여행기록 상세조회
	public TripVO getTripInfo(TripVO tripVO);
	
	//여행기록 등록
	public int InsertTripInfo(TripVO tripVO);
	
	//여행기록 임시저장
	public int TsInsertTripInfo(TripVO tripVO);
	
	//여행기록 지도 맵핑
	public int InsertTripMapping(TripVO tripVO);
	
}
