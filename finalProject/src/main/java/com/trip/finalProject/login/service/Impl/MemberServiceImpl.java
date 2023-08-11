package com.trip.finalProject.login.service.Impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.login.mapper.MemberMapper;
import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;


@Service //해당 클래스를 스프링의 서비스 빈으로 등록하
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper memberMapper;

	@Override
	public String insertMemberInfo(MemberVO memberVO) {

		int result = memberMapper.insertMember(memberVO);
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
	


}
