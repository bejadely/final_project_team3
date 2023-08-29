package com.trip.finalProject.tripMate.service;

import java.util.List;

public interface TripMateService {
	//여행 메이트 글 전체조회 - 페이징은 다른 기능이 어느정도 구현이 되고 나서 검색이 가능한 페이징으로 추가 해야함.
	public List<TripMateVO> getTripMateAll();
	
	//여행 메이트 게시글 상세조회
	public TripMateVO getTripMateInfo(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 조회수 카운트
	public int updateMateRecruitHit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 등록
	public void insertTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제
	public int deleteTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제 시 해당 게시글과 관련된 첨부파일 테이블 데이터 삭제
	public int deleteAttachedFile(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 수정
	public int updateTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 신고
	public int reportTripMate(TripMateVO tripMateVO);
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	public TripMateVO InsertTripMateApply(TripMateVO tripMateVO); 
	
	//여행 메이트 신청 시 게시글의 신청자 수 업데이트
	public int updateMateRecruitApplyNum(TripMateVO tripMateVO);
	
	//여행 메이트 게시글의 현재 신청자 수 조회
	public int selectMateRecruitApplyNum(TripMateVO tripMateVO);
	
}
