package com.trip.finalProject.authConfirm.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.authConfirm.mapper.AuthConfirmMapper;
import com.trip.finalProject.authConfirm.service.AuthConfirmService;
import com.trip.finalProject.authConfirm.service.AuthConfirmVO;

@Service
public class AuthConfirmServiceImpl implements AuthConfirmService {

	@Autowired
	AuthConfirmMapper authConfirmMapper;
	
	@Override
	public List<AuthConfirmVO> selectAllConfirmList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertApproveData(AuthConfirmVO vo) {
		
		return authConfirmMapper.insertApproveData(vo);
	}

	@Override
	public int insertRejectData(AuthConfirmVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
