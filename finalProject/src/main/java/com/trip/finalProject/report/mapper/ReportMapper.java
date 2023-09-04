package com.trip.finalProject.report.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.report.service.ReportVO;

public interface ReportMapper {
	
	// 신고 미처리 내역 갯수 카운트
	public int countAllNewReport();
	
	// 신고 미처리 내역 전체조회
	public List<ReportVO> selectAllNewReport(PagingVO vo);
	
	// 신고 내역 상세조회
	public ReportVO getReportDetail(ReportVO vo);
	
	// 신고 제재처리로 상태값 변경
	public int updateResultToPunish(ReportVO vo);
	
	// 신고 제재미처리로 상태값 변경
	public int updateResultToInvalidate(ReportVO vo);
	
}
