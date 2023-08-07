package com.trip.finalProject.admin.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.admin.mapper.AdminMemberMapper;
import com.trip.finalProject.admin.service.AdminMemberService;
import com.trip.finalProject.admin.service.AdminMemberVO;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {
	
	@Autowired
	AdminMemberMapper amm;
	
	@Override
	public List<AdminMemberVO> selectAllAuthRequest() {
		// 권한 승인 요청 내역 전체 조회
		return amm.selectAllAuthRequest();
	}

}
