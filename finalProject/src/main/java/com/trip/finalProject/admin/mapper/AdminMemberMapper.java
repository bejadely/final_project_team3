package com.trip.finalProject.admin.mapper;

import java.util.List;

import com.trip.finalProject.admin.service.AdminMemberVO;

public interface AdminMemberMapper {
	
	// 권한 승인 신청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
}
