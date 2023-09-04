package com.trip.finalProject.adminMember.mapper;

import java.util.List;

import com.trip.finalProject.adminMember.service.AdminMemberVO;
import com.trip.finalProject.common.PagingVO;

public interface AdminMemberMapper {
	
	// 회원정보 전체 조회
	public List<AdminMemberVO> selectAllMemeber(PagingVO vo);
	
	// 전체 회원 수 카운트
	public int getAllMemberCount();
	
	// 회원 상세 조회 
	public AdminMemberVO getMemberDetail(AdminMemberVO vo);
	
	// 회원 정보 수정
	public int modifyMemberInfo(AdminMemberVO vo);
	
	// 회원 탈퇴 처리
	public int withdrawMember(AdminMemberVO vo);
	
	// 이름 검색의 총 결과값 카운트
	public int countName(String keyword);
	
	// 아이디 검색의 총 결과값 카운트
	public int countId(String keyword);
	
	// 이름으로 회원 검색
	public List<AdminMemberVO> searchMemberByName(AdminMemberVO adminMemberVO, PagingVO pagingVO);
	
	// 아이디로 회원 검색
	public List<AdminMemberVO> searchMemberById(AdminMemberVO adminMemberVO, PagingVO pagingVO);
	
	// 회원 단건 조회
	public AdminMemberVO selectMemberInfo(AdminMemberVO vo);
	
	// 권한 승인 신청 회원 전체 조회
	public List<AdminMemberVO> selectAllAuthRequest();
	
	// 권한 승인 요청 승인 처리
	public int approveAuthRequest(String memberId);
	
	// 권한 승인 요청 반려 처리
	public int rejectAuthRequest(String memberId);
	
	// 제재카운트 1 증가
	public int plusPunishCount(String memberId);
	
}
