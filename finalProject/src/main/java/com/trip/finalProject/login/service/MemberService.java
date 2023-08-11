
package com.trip.finalProject.login.service;

import javax.servlet.http.HttpSession;

public interface MemberService {
	
	//회원등록(가입)
	public String insertMemberInfo(MemberVO memberVO);
	
	//int login(MemberVO memberVO);

	MemberVO login(MemberVO memberVO);
	
	MemberVO memberSelect(MemberVO memberVO);

	void logout(HttpSession session);

	
	

}