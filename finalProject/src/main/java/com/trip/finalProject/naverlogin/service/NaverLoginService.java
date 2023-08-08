package com.trip.finalProject.naverlogin.service;

import com.trip.finalProject.member.service.MemberVO;

public interface NaverLoginService {
	String getAccessToken (String authorize_code);
	public MemberVO getUserInfo(String access_Token) ;

}
