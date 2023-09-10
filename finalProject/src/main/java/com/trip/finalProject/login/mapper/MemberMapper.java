package com.trip.finalProject.login.mapper;

import com.trip.finalProject.login.service.MemberVO;

public interface MemberMapper {

	
	//회원등록
	 public int insertMember(MemberVO member);
	
	// 비밀번호 일치 여부 확인
	 public MemberVO checkPassword(MemberVO vo);
	 
	 //회원정보 가져오기
	 public MemberVO memebrInfo(MemberVO memberVO);
	 
	 //회원정보 수정하기
	 public int updateMember(MemberVO memberVO);
	 //로그인	 
	 //member는 MemberVO 클래스의 인스턴스(객체)를 가리키는 변수
	 public MemberVO login(MemberVO member);
	 
	 //멤버 정보확인  
	 public MemberVO memberselect(MemberVO member);
	 
	// 비밀번호 일치 여부 확인
	// public MemberVO checkPassword(MemberVO vo);
	 
	 //기존 DB에 아이디 있는지 확인 (회원가입 시)
	 public Integer checkId(MemberVO vo);
	 
	 //기존 DB에 계정 있는지 확인 (로그인 시)
	 public MemberVO checkLoginAccount(MemberVO member);
	 
	 //시큐어리티 활용 세션 값 담기위한 select
	 public MemberVO singleLogin(MemberVO member);
	 
	 //관리자 회원가입
	 public int insertGuide(MemberVO member);
	 
	 //입력한 전화번호로 회원 계정 찾기
	 public MemberVO checkIdByPhoneNumber(String num);
	 
	 //입력한 이메일을 비밀번호 업데이트 하기
	 public int editPassword(MemberVO vo);
	 
	 //회원정보 수정페이제를 위한 비밀번호 확인 구문
	 public MemberVO passwordVerify(MemberVO memberVO);

}
