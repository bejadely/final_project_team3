package com.trip.finalProject.report.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;

public interface ReportService {
	
	// 새로운 신고내역 전체 조회
	public Map<String, Object> selectAllNewReport(Integer nowPage, Integer cntPerPage);
	
	// 신고 내역 상세 조회
}
