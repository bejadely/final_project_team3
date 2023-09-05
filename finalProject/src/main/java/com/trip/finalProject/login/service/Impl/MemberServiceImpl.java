package com.trip.finalProject.login.service.Impl;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.login.mapper.MemberMapper;
import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;


@Service //해당 클래스를 스프링의 서비스 빈으로 등록하
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper memberMapper;
	
	//일반회원 회원가입
	@Override
	public String insertMemberInfo(MemberVO memberVO) {

		int result = memberMapper.insertMember(memberVO);
		if(result ==1) {
			return memberVO.getMemberId();
		}else {
			return null;
		}
	}
	
	//가이드회원 회원가입
	@Override
	public String insertGuide(MemberVO memberVO) {

		int result = memberMapper.insertGuide(memberVO);
		if(result ==1) {
			return memberVO.getMemberId();
		}else {
			return null;
		}
	}
	

	
	
	// 로그인 기능 수행
	 @Override	
	    public MemberVO login(MemberVO memberVO) {
		 //Mapper.xml의 select문인 login의 결과가 없으면  빈값이나 null을 가져옴
		 //MemberVO 타입으로 반환
		 MemberVO result = memberMapper.login(memberVO); 
		 
		 return result;
	    }

	 
	 @Override
	public MemberVO memberSelect(MemberVO vo) {
		return memberMapper.memberselect(vo);
	}
	 
	@Override
	public void logout(HttpSession session) {
	 session.invalidate(); // 세션 초기화
	 }
	
	//회원가입시 아이디 중복체크
	 @Override
		public Integer checkId(MemberVO vo) {
		
		 Integer result = memberMapper.checkId(vo);
		return result; 
			
		}
	 
		//로그인시 계정 유무 체크
	 @Override
		public Integer loginAccountCheck(MemberVO vo) {
		
		 Integer result = memberMapper.checkId(vo);
		return result; 
			
		}
	 	
	 //휴대폰 번호로 아이디 찾기

	 @Override
		public MemberVO phoneNumberCheck(String num) {
			MemberVO result = memberMapper.checkIdByPhoneNumber(num);
			return result;
	} 
	 
	 //비밀번호 찾기 중 비밀번호 수정하기
	 
	 @Override
	 public MemberVO editPassword(MemberVO vo) {
		
			MemberVO result = memberMapper.editPassword(vo);
			return result;
		 
	 }
	 
	 
		
		//회원정보 불러이기
		@Override
		public MemberVO memberInfo(MemberVO memberVO) {
			return memberMapper.memebrInfo(memberVO);
		}

		//회원정보 수정
		@Override
		public String updateMember(MemberVO memberVO) {
			
			int result = memberMapper.updateMember(memberVO);
			
			if(result > 1) {
				return "success";
			} else {
				return "fail";
			}
		}

		

		@Override
		public MemberVO singleLogin(MemberVO vo) {
			// 스프링시큐어리티 session담기 위한 정보 조회
			
			return memberMapper.singleLogin(vo);
		}
		
	


}
