package com.trip.finalProject.login.mapper;

import com.trip.finalProject.login.service.MemberVO;

public interface MemberMapper {

	
	//회원등록
	 public int insertMember(MemberVO member);
	
	 //로그인
	 
	 //member는 MemberVO 클래스의 인스턴스(객체)를 가리키는 변수
	 public MemberVO login(MemberVO member);
	 
	 public MemberVO memberselect(MemberVO member);
	// 비밀번호 일치 여부 확인
	 public MemberVO checkPassword(MemberVO vo);
}
