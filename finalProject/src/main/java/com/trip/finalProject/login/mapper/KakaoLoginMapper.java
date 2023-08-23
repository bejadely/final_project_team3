package com.trip.finalProject.login.mapper;

import java.util.HashMap;

import com.trip.finalProject.login.service.MemberVO;



public interface KakaoLoginMapper {

	 public int insertKakaoLogin(HashMap<String, Object> userInfo);
	 public MemberVO findKakao(HashMap<String, Object> userInfo);

}
