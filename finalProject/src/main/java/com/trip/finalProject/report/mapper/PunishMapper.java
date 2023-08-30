package com.trip.finalProject.report.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.report.service.PunishVO;

public interface PunishMapper {
	
	// 제재내역 저장(제재)
	public int insertPunishInfo(PunishVO vo);
	
	// 제재 내역 전체 조회 카운트
	public int countAllPunishList();
	
	// 제재 내역 전체 조회
	public List<PunishVO> selectAllPunishList(PagingVO vo);
	
	
}
