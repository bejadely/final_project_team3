package com.trip.finalProject.adminMember.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.adminMember.mapper.AdminMemberMapper;
import com.trip.finalProject.adminMember.service.AdminMemberService;
import com.trip.finalProject.adminMember.service.AdminMemberVO;
import com.trip.finalProject.common.PagingVO;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {
	
	@Autowired
	AdminMemberMapper amm;
	
	@Override
	public List<AdminMemberVO> selectAllMember(PagingVO pagingVO) {
		// 회원 전체 조회
		return amm.selectAllMemeber(pagingVO);
	}
	
	@Override
	public int memberCount() {
		// 전체 회원 수 카운트
		return amm.getAllMemberCount();
	}
	
	@Override
	public AdminMemberVO getMemberDetail(AdminMemberVO vo) {
		// 회원 상세 조회
		return amm.getMemberDetail(vo);
	}
	
	@Override
	public String modifyMemberInfo(AdminMemberVO vo) {
		// 회원 정보 수정
		int result = amm.modifyMemberInfo(vo);
		
		if(result > 1) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@Override
	public List<AdminMemberVO> searchMemberByName(AdminMemberVO vo) {
		// 이름으로 회원 검색
		return amm.searchMemberByName(vo);
	}
	
	@Override
	public List<AdminMemberVO> searchMemberById(AdminMemberVO vo) {
		// 아이디로 회원 검색
		return amm.searchMemberById(vo);
	}
	
	@Override
	public List<AdminMemberVO> selectAllAuthRequest() {
		// 권한 승인 요청 내역 전체 조회
		return amm.selectAllAuthRequest();
	}

	@Override
	public Map<String, String> approveAuthRequest(String memberId) {
		// 권한 승인 요청 승인 처리
		Map<String, String> map = new HashMap<>();
		
		map.put("memberId", memberId);
		
		int result = amm.approveAuthRequest(memberId);
		if(result > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}

	@Override
	public Map<String, String> rejectAuthRequest(String memberId) {
		// 권한 승인 요청 반려 처리
		Map<String, String> map = new HashMap<>();
		
		map.put("memberId", memberId);
		
		int result = amm.rejectAuthRequest(memberId);
		if(result > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}

}
