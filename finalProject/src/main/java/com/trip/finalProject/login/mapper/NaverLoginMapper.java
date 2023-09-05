package com.trip.finalProject.login.mapper;





import org.json.simple.JSONObject;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.trip.finalProject.login.service.MemberVO;

public interface NaverLoginMapper {
	
	 public int insertNaverLogin(JSONObject responseMap,OAuth2AccessToken oauthToken);
	 public MemberVO findNaver(JSONObject responseMap);

}
