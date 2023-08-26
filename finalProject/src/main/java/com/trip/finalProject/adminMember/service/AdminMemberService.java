package com.trip.finalProject.adminMember.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;

public interface AdminMemberService {
	
	// 회원전체 조회
	public List<AdminMemberVO> selectAllMember(PagingVO pagingVO);
	
	// 전체 회원 수 카운트
	public int memberCount();
	
	// 회원 상세 조회
	public AdminMemberVO getMemberDetail(AdminMemberVO vo);
	
	// 이름으로 회원 검색
	public List<AdminMemberVO> searchMemberByName(AdminMemberVO vo);
	
	// 아이디로 회원 검색
	public List<AdminMemberVO> searchMemberById(AdminMemberVO vo);
	
	// 권한 승인 요청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
	// 권한 승인 요청 승인 처리
	public Map<String, String> approveAuthRequest(String memberId);
	
	// 권한 승인 요청 반려 처리
	public Map<String, String> rejectAuthRequest(String memberId);
	
}
