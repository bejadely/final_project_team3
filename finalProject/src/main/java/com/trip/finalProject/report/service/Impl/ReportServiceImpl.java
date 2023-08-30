package com.trip.finalProject.report.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.adminMember.mapper.AdminMemberMapper;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.report.mapper.ReportMapper;
import com.trip.finalProject.report.service.ReportService;
import com.trip.finalProject.report.service.ReportVO;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	AdminMemberMapper adminMemberMapper;
	
	@Autowired
	ReportMapper reportMapper;

	@Override
	public Map<String, Object> selectAllNewReport(Integer nowPage, Integer cntPerPage) {
		
		// 새로운 신고내역 카운트
		int total = reportMapper.countAllNewReport();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		// 새로운 신고내역 전체 조회
		List<ReportVO> list = reportMapper.selectAllNewReport(pagingVO);
		
		// 컨트롤러에 값을 보내기 위한 Map 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("PagingVO", pagingVO);
		
		return map;
	}

	@Override
	public ReportVO getReportDetail(ReportVO reportVO) {
		// 신고 내역 상세 조회
		
		return reportMapper.getReportDetail(reportVO);
	}

	@Override
	@Transactional
	public String punishProcess(ReportVO reportVO) {
		// 신고 처리
		
		if(reportVO.getResult().equals("P2")) {
			// 제재 처리
			
			// 1. Member 제재횟수 + 1
			adminMemberMapper.plusPunishCount(reportVO.getPunishedId());
			
			// 2. 신고현황에 처리내역 변경
			reportMapper.updateResult(reportVO);
			
			// 3. 제재이력에 이력 추가
			
			
			// 4. 신고당한 게시글 삭제
			
			// 5. 신고당한 게시자의 알림창에 추가
			
		}
		
		
		
		
		// 제재 미처리시
		
		
		return null;
	}
	
}
