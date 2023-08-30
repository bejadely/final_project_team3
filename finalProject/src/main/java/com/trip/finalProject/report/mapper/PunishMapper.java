package com.trip.finalProject.report.mapper;

import com.trip.finalProject.report.service.PunishVO;

public interface PunishMapper {
	
	// 제재내역 저장(제재)
	public int insertPunishInfo(PunishVO vo);
	
}
