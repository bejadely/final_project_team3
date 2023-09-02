package com.trip.finalProject.tripMate.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.tripMate.service.TripMateVO;

public interface TripMateMapper {
	//여행 메이트 게시글 전체조회
	public List<TripMateVO> selectAllTripMate();
	
	//여행 메이트 게시글 상세조회
	public TripMateVO selectTripMateInfo(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 조회수 업데이트
	public int updateMateRecruitHit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 등록
	public int insertEditor(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제
	public int deleteTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제시 해당 게시글과 관련된 첨부파일 테이블 데이터 삭제
	public int deleteAttachedFile(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 수정
	public int updateTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 신고
	public int reportTripMate(TripMateVO tripMateVO);
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	public int insertTripMateApply(TripMateVO tripMateVO); 
	
	//여행 메이트 신청시 작성자에게 알림
	public int sendAlert(TripMateVO tripMateVO);
	
	//여행 메이트 신청시 게시글의 신청자 수 업데이트
	public int updateMateRecruitApplyNum(TripMateVO tripMateVO);
	
	//여행 메이트 게시글의 현재 신청자 수 조회
	public int selectMateRecruitApplyNum(TripMateVO tripMateVO);
	
	//마이페이지--------------------------------------------------------------------------------------
	//내가 작성한 메이트 페이징용
	public int myTripCount(String memberId);
	
	//전체 메이트 불러오기
	public List<TripMateVO> myMateList(TripMateVO trVO, PagingVO pagingVO);
	
	//내가 참여한 메이트 페이징용
	public int myTripAppCount(String memberId);
	
	//전체 참여한 불러오기
	public List<TripMateVO> myMateAppList(TripMateVO trVO, PagingVO pagingVO);
	
	//참여한 메이트 취소
	public int myTripCancle(TripMateVO trVO);

}
