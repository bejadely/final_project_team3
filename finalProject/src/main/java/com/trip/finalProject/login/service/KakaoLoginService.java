package com.trip.finalProject.login.service;

public interface KakaoLoginService {
	
String getAccessToken (String authorize_code);
public MemberVO getUserInfo(String access_Token) ;

}