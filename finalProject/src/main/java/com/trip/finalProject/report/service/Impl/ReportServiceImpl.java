package com.trip.finalProject.report.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.adminMember.mapper.AdminMemberMapper;
import com.trip.finalProject.alert.mapper.AlertMapper;
import com.trip.finalProject.alert.service.AlertVO;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.report.mapper.PunishMapper;
import com.trip.finalProject.report.mapper.ReportMapper;
import com.trip.finalProject.report.service.PunishVO;
import com.trip.finalProject.report.service.ReportService;
import com.trip.finalProject.report.service.ReportVO;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	AdminMemberMapper adminMemberMapper;
	
	@Autowired
	ReportMapper reportMapper;
	
	@Autowired
	PunishMapper punishMapper;

	@Autowired
	AlertMapper alertMapper;
	
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
		String message = "";
		
		if(reportVO.getResult().equals("P2")) {
			// 제재 처리
			
			// 1. Member 제재횟수 + 1
			int memberResult = adminMemberMapper.plusPunishCount(reportVO.getPunishedId());
			
			// 2. 신고현황에 처리내역 변경
			int reportResult = reportMapper.updateResultToPunish(reportVO);
			
			// 3. 제재이력에 이력 추가
			PunishVO punishVO = new PunishVO();
			punishVO.setReportId(reportVO.getReportId());
			punishVO.setPunishedId(reportVO.getPunishedId());
			punishVO.setPunishReason(reportVO.getPunishReason());
			int punishResult = punishMapper.insertPunishInfo(punishVO);
			
			// 4. 신고당한 게시글 삭제(작업중 : 조원들과 연계해야함) - 필요한 것 (상품등록글, 여행기록, 여행메이트 모집글 분류해서 각각 다른 delete 수행하는 구문)
			
			// 5. 신고당한 게시자의 알림창에 추가
			AlertVO alertVO = new AlertVO();
			alertVO.setMemberId(punishVO.getPunishedId());
			
			// 작업중(1/2/3회에 따라 알림 다르게 보내고, 3회가 될시 자동탈퇴처리되게 하는 구문 만들기
			alertVO.setContent("회원님께서 작성하신 글이 제재 처리되었습니다.<br>누적 제재 횟수가 3회에 도달할 시 자동 탈퇴 처리됨을 알려드립니다.<br>자세히보기..");
			int alertResult =  alertMapper.insertAlert(alertVO);
			
			// 예외처리
			if(memberResult > 0 && reportResult > 0 && punishResult > 0 && alertResult > 0) {
				message = "제재처리가 성공적으로 완료되었습니다.";
			} else {
				message = "제재처리가 성공적으로 이루어지지 않았습니다.\n다시 시도해주세요.";
			}
			
			
		} else {
			// 제재 미처리시
			
			// 신고현황에 처리내역 변경
			int reportResult = reportMapper.updateResultToInvalidate(reportVO);
			
			// 예외처리
			if(reportResult > 0) {
				message = "제재처리가 성공적으로 완료되었습니다.";
			} else {
				message = "제재처리가 성공적으로 이루어지지 않았습니다.\n다시 시도해주세요.";
			}
			
		}
		
		return message;
	}
	
}
