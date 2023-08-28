package com.trip.finalProject.security.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	MemberService memberService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		HttpSession session = request.getSession();
		
		// 회원 단건 정보 조회
		String memberId = authentication.getName();
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemberId(memberId);
		
		
		MemberVO result = memberService.singleLogin(memberVO);
		
		session.setAttribute("sessionId", authentication.getName()); // 인증시 사용한 memberId
		session.setAttribute("sessionName", result.getMemberName());
		
		response.sendRedirect("/");
	}

}
