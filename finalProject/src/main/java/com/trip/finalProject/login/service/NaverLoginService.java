package com.trip.finalProject.login.service;

public interface NaverLoginService {
	String getAccessToken (String authorize_code);
	public MemberVO getUserInfo(String access_Token) ;

}
