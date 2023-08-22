
package com.trip.finalProject.login.service;

import javax.servlet.http.HttpSession;

public interface MemberService {
	
	//회원등록(가입)
	public String insertMemberInfo(MemberVO memberVO);
	
	//int login(MemberVO memberVO);

	MemberVO login(MemberVO memberVO);
	
	MemberVO memberSelect(MemberVO memberVO);

	void logout(HttpSession session);

	Integer checkId(MemberVO memberVO);
	
	
	//로그인시 아이디 비밀번호 DB유무 체크
	public Integer loginAccountCheck(MemberVO vo);
	/* idCheck(id) */

	
	

}