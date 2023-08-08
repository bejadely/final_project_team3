package com.trip.finalProject.kakaologin.service;



import com.trip.finalProject.member.service.MemberVO;

public interface KakaoLoginService {
	
String getAccessToken (String authorize_code);
public MemberVO getUserInfo(String access_Token) ;

}