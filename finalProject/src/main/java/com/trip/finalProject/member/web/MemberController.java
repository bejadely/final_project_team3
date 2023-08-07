package com.trip.finalProject.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MemberController {

	
	//로그인화면 호출
	@GetMapping("home/login")//url에 접근하면 메서드 실행
	public String loginForm() {
		
		return"home/login"; //로그인페이지 호출
	}
}
