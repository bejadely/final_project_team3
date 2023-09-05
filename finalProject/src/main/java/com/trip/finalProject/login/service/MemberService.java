
package com.trip.finalProject.login.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

public interface MemberService {
	

	//회원정보 불러오기
	public MemberVO memberInfo(MemberVO memberVO);
	
	//회원정보 수정
	public Map<String, String> updateMember(MemberVO memberVO);

	
	//회원등록(가입)
	public String insertMemberInfo(MemberVO memberVO);
	
	//int login(MemberVO memberVO);
	
	//가이드 회원가입
	public String insertGuide(MemberVO memberVO);

	MemberVO login(MemberVO memberVO);
	
	MemberVO memberSelect(MemberVO memberVO);

	void logout(HttpSession session);

	Integer checkId(MemberVO memberVO);
	
	
	//로그인시 아이디 비밀번호 DB유무 체크
	public Integer loginAccountCheck(MemberVO vo);
	/* idCheck(id) */
	
	public MemberVO singleLogin(MemberVO vo);
	
	//휴대폰 번호로 계정 찾기
	MemberVO phoneNumberCheck(String phoneNumber);
}