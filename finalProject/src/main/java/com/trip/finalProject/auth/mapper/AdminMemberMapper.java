package com.trip.finalProject.auth.mapper;

import java.util.List;

import com.trip.finalProject.auth.service.AdminMemberVO;

public interface AdminMemberMapper {
	
	// 권한 승인 신청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
	// 권한 승인 요청 승인 처리
	public int approveAuthRequest(String memberId);
	
	// 권한 승인 요청 반려 처리
	public int rejectAuthRequest(String memberId);

}
