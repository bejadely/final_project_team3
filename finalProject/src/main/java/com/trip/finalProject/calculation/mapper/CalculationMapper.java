package com.trip.finalProject.calculation.mapper;

import java.util.List;

import com.trip.finalProject.calculation.service.CalculationVO;
import com.trip.finalProject.common.PagingVO;

public interface CalculationMapper {
	
	// 미정산 내역 카운트
	public int countNotCalList();
	
	// 미정산 내역 전체 조회
	public List<CalculationVO> selectNotCalList(PagingVO pagingVO);
	
	// 정산 처리( 단일 / 일괄작업 모두 수행 )
	public int calProc(String calId);
	
	// 특정월 정산 내역 카운트
	public int countCompCalList(String searchYear, String searchMonth);

	// 특정월 정산 내역 전체 조회
	public List<CalculationVO> selectCompCalList(PagingVO pagingVO, String searchYear, String searchMonth);
	
	// quartz scheduler : 매월 일괄 정산
	public int quartzCalMonthlyProc();
}
