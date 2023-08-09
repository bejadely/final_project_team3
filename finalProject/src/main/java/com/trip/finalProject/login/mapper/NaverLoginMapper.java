package com.trip.finalProject.login.mapper;



import org.json.simple.JSONObject;

import com.trip.finalProject.login.service.MemberVO;

public interface NaverLoginMapper {
	
	 public int insertNaverLogin(JSONObject responseMap);
	 public MemberVO findNaver(JSONObject responseMap);

}
