package com.trip.finalProject.kakaoPay.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoPayWebContorller {
	
	@GetMapping("/kakaoPaySuccess")
	public String kakaoPaySuccess() {
		
		return "payment/kakaPaySuccess";
	}
}
