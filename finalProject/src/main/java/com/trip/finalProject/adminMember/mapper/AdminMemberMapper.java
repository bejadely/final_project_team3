package com.trip.finalProject.adminMember.mapper;

import java.util.List;

import com.trip.finalProject.adminMember.service.AdminMemberVO;

public interface AdminMemberMapper {
	
	// 회원정보 전체 조회
	public List<AdminMemberVO> selectAllMemeber();
	
	// 전체 회원 수 카운트
	public int getAllMemberCount();
	
	// 이름으로 회원 검색
	public List<AdminMemberVO> searchMemberByName(AdminMemberVO vo);
	
	// 아이디로 회원 검색
	public List<AdminMemberVO> searchMemberById(AdminMemberVO vo);
	
	// 회원 단건 조회
	public AdminMemberVO selectMemberInfo(AdminMemberVO vo);
	
	// 권한 승인 신청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
	// 권한 승인 요청 승인 처리
	public int approveAuthRequest(String memberId);
	
	// 권한 승인 요청 반려 처리
	public int rejectAuthRequest(String memberId);
	
}