package com.trip.finalProject.authConfirm.mapper;

import java.util.List;

import com.trip.finalProject.authConfirm.service.AuthConfirmVO;

public interface AuthConfirmMapper {
	
	// 권한승인신청 처리현황 전체 조회
	public List<AuthConfirmVO> selectAllConfirmList();
	
	// 권한승인내역 테이블에 승인 내역 추가
	public int insertApproveData(AuthConfirmVO authConfirmVO);
	
	// 권한승인내역 테이블에 거절 내역 추가
	public int insertRejectData(AuthConfirmVO authConfirmVO);
	
}
