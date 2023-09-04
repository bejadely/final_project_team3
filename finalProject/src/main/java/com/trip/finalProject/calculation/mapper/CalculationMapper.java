package com.trip.finalProject.calculation.mapper;

import java.util.List;

import com.trip.finalProject.calculation.service.CalculationVO;
import com.trip.finalProject.common.PagingVO;

public interface CalculationMapper {
	// 미정산 내역 전체 조회
	public List<CalculationVO> selectNotCalList(PagingVO pagingVO);
}
