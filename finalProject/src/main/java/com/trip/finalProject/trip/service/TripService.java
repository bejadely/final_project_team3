package com.trip.finalProject.trip.service;

import java.util.List;
import java.util.Map;

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
	
	//여행 저장 기록 업데이트
	public Map<String, Object> getUpdateDis(TripVO tripVO);

	//여행기록 회원 조회 - 임시저장
	public List<TripVO> getTripPerNot(PagingVO pagingVO);
	
	//여행기록 회원 조회 - 미완료 여행
	public List<TripVO> getTripPerCom(PagingVO pagingVO);
	
	//여행기록 상세조회
	public TripVO getTripInfo(TripVO tripVO);
	
	//여행기록 등록(임시저장 되어있는 게시글을 저장 상태로 업데이트)
	public int InsertTripInfo(TripVO tripVO);
	
	//여행기록 임시저장(임시저장 되어있는 게시글을 추가된 내용을 가지고 다시 업데이트)
	public int TsTripInfo(TripVO tripVO);
	
	//전체조회 페이지에서 여행기록 등록 버튼 클릭 시 실행(임시저장 상태로 등록)
	public TripVO TsInsertTripInfo(TripVO tripVO);
	
	//여행기록 삭제
	public int deleteTripInfo(TripVO tripVO);
	
	//여행기록 삭제시 해당 게시글과 관련된 여행경로 삭제
	public int deleteMapData(TripVO tripVO);
	
	//여행기록 삭제시 해당 게시글과 관련된 여행메모 삭제
	public int deleteMemoData(TripVO tripVO);
	
	//여행메모 등록
	public int InsertTripMemo(TripVO tripVO);

	//여행메모 데이터 조회
	public List<TripVO> getMemoData(TripVO tripVO);
	
	//여행경로 저장
	public int InsertTripMapping(TripVO tripVO);
	
	//여행경로 삭제
	public int deleteTripMapping(TripVO tripVO);
	
	//여행경로 데이터 조회
	public List<TripVO> getMapData(TripVO tripVO);
	
}
