package com.trip.finalProject.auth.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.auth.mapper.AdminMemberMapper;
import com.trip.finalProject.auth.service.AdminMemberService;
import com.trip.finalProject.auth.service.AdminMemberVO;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {
	
	@Autowired
	AdminMemberMapper amm;
	
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
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
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
			map.put("result", "Success");
		} else {
			map.put("result", "Fail");
		}
		
		return map;
	}

}
