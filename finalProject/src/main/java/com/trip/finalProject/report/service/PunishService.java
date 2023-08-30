package com.trip.finalProject.report.service;

import java.util.Map;

public interface PunishService {
	
	// 제재 처리 현황 전체 조회
	public Map<String, Object> selectAllPunishList(Integer nowPage, Integer cntPerPage);
}
