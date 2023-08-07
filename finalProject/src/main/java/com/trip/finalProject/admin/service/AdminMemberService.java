package com.trip.finalProject.admin.service;

import java.util.List;

public interface AdminMemberService {
	
	// 관리자용 Service
	
	// 권한 승인 요청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
}
