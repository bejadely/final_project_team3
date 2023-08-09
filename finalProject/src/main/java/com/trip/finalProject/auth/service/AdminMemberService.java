package com.trip.finalProject.auth.service;

import java.util.List;
import java.util.Map;

public interface AdminMemberService {
	
	// 관리자용 Service
	
	// 권한 승인 요청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
	// 권한 승인 처리
	public Map<String, String> approveAuthRequest(String memberId);
	
}
