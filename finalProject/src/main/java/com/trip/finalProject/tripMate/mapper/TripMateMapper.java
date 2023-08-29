package com.trip.finalProject.tripMate.mapper;

import java.util.List;

import com.trip.finalProject.tripMate.service.TripMateVO;

public interface TripMateMapper {
	//여행 메이트 글 전체조회
	public List<TripMateVO> selectAllTripMate();
	
	//여행 메이트 글 상세조회
	public TripMateVO selectTripMateInfo(TripMateVO tripMateVO);
	
	//여행 메이트 글 조회수 업데이트
	public int updateMateRecruitHit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 등록
	public int insertEditor(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제
	public int deleteTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 수정
	public int updateTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	public int insertTripMateApply(TripMateVO tripMateVO); 
	
	//여행 메이트 신청 시 게시글의 신청자 수 업데이트
	public int updateMateRecruitApplyNum(TripMateVO tripMateVO);
	
	//여행 메이트 게시글의 현재 신청자 수 조회
	public int selectMateRecruitApplyNum(TripMateVO tripMateVO);
}
