package com.trip.finalProject.trip.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripVO;

public interface TripMapper {
	//여행기록 전체조회 페이징용
	public int getTotalCount();

	//여행기록 전체 조회
	public List<TripVO>	selectAllTrip(PagingVO pagingVO);
	
	//여행기록 회원 조회
	public List<TripVO> selectPerTrip(PagingVO pagingVO);
	
	//여행기록 상세조회
	public TripVO selectTripInfo(TripVO tripVO);
	
	//여행기록 등록(임시저장 데이터를 저장 상태로 상태 변경)
	public int insertTripInfo(TripVO tripVO);
	
	//여행기록 임시저장(임시저장 데이터를 다시 임시저장 시킴)
	public int tsTripInfo(TripVO tripVO);
	
	//여행기록 등록 시 일단 게시글을 임시저장 상태로 등록해버림(임시저장 상태로 저장)
	public int tsInsertTripInfo(TripVO tripVO);
	
	//여행경로 저장
	public int insertTripMapping(TripVO tripVO);
	
	//여행기록 삭제
	public int deleteTripInfo(int postId);
	
}
