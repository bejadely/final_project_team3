package com.trip.finalProject.trip.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripVO;

public interface TripMapper {
	//여행기록 전체조회 페이징용
	public int getTotalCount();
	
	//여행기록 전체조회 아이디
	public int getWriterIdCount(String keyword);
	
	//여행기록 전체조회 타이틀
	public int getTitleCount(String keyword);
	
	//마이페이지 페이징
	public int getPerCount(String writerId);
	
	//마이페이지 페이징 - 임시저장
	public int getPerNotCount(String writerId);
	
	//마이페이지 페이징
	public int getPerComCount(String writerId);

	//여행기록 전체 조회
	public List<TripVO>	selectAllTrip(PagingVO pagingVO);
	
	//여행기록 전체 조회 아이디
	public List<TripVO> selectAllWriter(TripVO tripVO, PagingVO pagingVO);
	
	//여행기록 전체 조회 타이틀
	public List<TripVO> selectAllTitle(TripVO tripVO, PagingVO pagingVO);
	
	//여행기록 회원 조회 - 미완료 여행
	public List<TripVO> selectPerTrip(TripVO tripVO, PagingVO pagingVO);
	
	//여행기록 업데이트
	public int updateDis(TripVO tripVO);
	
	//여행기록 회원 조회 - 임시저장
	public List<TripVO> selectPerNotTrip(TripVO tripVO, PagingVO pagingVO);
	
	//여행기록 회원 조회 - 완료 여행
	public List<TripVO> selectPerComTrip(TripVO tripVO, PagingVO pagingVO);
	
	//여행기록 상세조회
	public TripVO selectTripInfo(TripVO tripVO);
	
	//여행기록 게시글 조회수 카운트
	public int updateTripRecordHit(TripVO tripVO);
	
	//여행기록 등록(임시저장 데이터를 저장 상태로 상태 변경)
	public int insertTripInfo(TripVO tripVO);
	
	//여행기록 임시저장(임시저장 데이터를 다시 임시저장 시킴)
	public int tsTripInfo(TripVO tripVO);
	
	//여행기록 등록 시 일단 게시글을 임시저장 상태로 등록해버림(임시저장 상태로 저장)
	public int tsInsertTripInfo(TripVO tripVO);
	
	//여행메모 저장
	public int insertTripMemo(TripVO tripVO);
	
	//여행메모 수정
	public int modifyMemoData(TripVO tripVO);
	
	//여행메모 데이터 조회
	public List<TripVO> selectMemoData(TripVO tripVO);
	
	//여행경로 저장
	public int insertTripMapping(TripVO tripVO);
	
	//여행경로 삭제
	public int deleteTripMapping(TripVO tripVO);
	
	//여행경로 데이터 조회
	public List<TripVO> selectMapData(TripVO tripVO);
	
	//여행기록 수정
	public int modifyTripInfo(TripVO tripVO);
	
	//여행기록 삭제
	public int deleteTripInfo(TripVO tripVO);
	
	//여행기록 삭제시 해당 게시글과 관련된 여행경로 삭제
	public int deleteMapData(TripVO tripVO);
	
	//여행기록 삭제시 해당 게시글과 관련된 여행메모 삭제
	public int deleteMemoData(TripVO tripVO);
	
	//여행기록 기간 완료시 자동으로 여행완료로 업데이트 처리
	public int completeTripRecord();
	
	//여행후기 등록
	public int insertTripReview(TripVO tripVO);
	
}
