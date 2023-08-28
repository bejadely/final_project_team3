package com.trip.finalProject.trip.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.mapper.TripMapper;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.trip.service.TripVO;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	TripMapper tripMapper;
	
	//여행기록 전체 조회 페이징용
	@Override
	public int tripRecordCount() {
		return tripMapper.getTotalCount();
	}
	
	//마이페이지 계획 여행
	@Override
	public int tripPerCount() {
		return tripMapper.getPerCount();
	}
	
	//마이페이지 계획 여행
	@Override
	public int tripPerNotCount() {
		return tripMapper.getPerNotCount();
	}
	//마이페이지 완료여행
	@Override
	public int tripPerComCount() {
		return tripMapper.getPerComCount();
	}


	//여행기록 전체 리스트 조회
	@Override
	public List<TripVO> getTripAll(PagingVO pagingVO) {
		return tripMapper.selectAllTrip(pagingVO);
	}
	
	//여행기록 회원별 조회
	@Override
	public List<TripVO> getTripPer(PagingVO pagingVO) {
		return tripMapper.selectPerTrip(pagingVO);
	}
	
	//여행 저장 데이터 업데이트
	@Override
	public Map<String, Object> getUpdateDis(TripVO tripVO) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("게시물 번호", String.valueOf(tripVO.getPostId()));
		map.put("저장 번호", String.valueOf(tripVO.getTripDisclose()));
		
		int result  = tripMapper.updateDis(tripVO);
		if(result > 0) {
			map.put("결과", "Success");
		}else {
			map.put("결과", "fail");
		}		
		
		return map;	
	}
	
	//여행기록 회원별 조회
	@Override
	public List<TripVO> getTripPerNot(PagingVO pagingVO) {
		return tripMapper.selectPerNotTrip(pagingVO);
	}
	
	//여행기록 회원별 조회 - 완료된 여행
	@Override
	public List<TripVO> getTripPerCom(PagingVO pagingVO) {
		return tripMapper.selectPerComTrip(pagingVO);
	}

	//여행기록 상세조회
	@Override
	public TripVO getTripInfo(TripVO tripVO) {
		return tripMapper.selectTripInfo(tripVO);
	}
	
	//여행기록 등록(임시저장에서 저장으로 update)
	@Override
	public int InsertTripInfo(TripVO tripVO) {
		
		int result = tripMapper.insertTripInfo(tripVO);
		
		return result;
	}
	
	//여행기록 임시저장(임시저장 상태로 최초로 등록된 게시글을 다시 임시저장함)
	@Override
	public int TsTripInfo(TripVO tripVO) {
		
		int result = tripMapper.tsTripInfo(tripVO);
		
		return result;
	}
	

	//여행기록 등록 버튼 클릭시 실행(임시저장 상태)
	@Override
	public TripVO TsInsertTripInfo(TripVO tripVO) {
		
		// sql 실행
		tripMapper.tsInsertTripInfo(tripVO);
	
		return tripVO;
	}

	//여행경로 저장
	@Override
	public int InsertTripMapping(TripVO tripVO) {
		return tripMapper.insertTripMapping(tripVO);
		
	}

	//여행메모 등록
	@Override
	public TripVO InsertTripMemo(TripVO tripVO) {
		
		tripMapper.insertTripMemo(tripVO);
		
		return tripVO;
	}
	
	//여행기록 삭제
	@Override
	public int DeleteTripInfo(int postId) {
		int result = tripMapper.deleteTripInfo(postId);
		
		return result;
	}

	

}
