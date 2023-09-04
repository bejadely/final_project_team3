package com.trip.finalProject.login.service;

import java.util.Map;

public interface KakaoLoginService {
	
public	Map<String, String> getAccessToken (String authorize_code);
public MemberVO getUserInfo(String access_Token, String refresh_Token) ;

}