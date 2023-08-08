package com.trip.finalProject.kakaologin.mapper;

import java.util.HashMap;



import com.trip.finalProject.member.service.MemberVO;



public interface KakaoLoginMapper {

	 public int insertKakaoLogin(HashMap<String, Object> userInfo);
	 public MemberVO findKakao(HashMap<String, Object> userInfo);

}
