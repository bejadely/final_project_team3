package com.trip.finalProject.naverlogin.mapper;



import org.json.simple.JSONObject;

import com.trip.finalProject.member.service.MemberVO;

public interface NaverLoginMapper {
	
	 public int insertNaverLogin(JSONObject responseMap);
	 public MemberVO findNaver(JSONObject responseMap);

}
