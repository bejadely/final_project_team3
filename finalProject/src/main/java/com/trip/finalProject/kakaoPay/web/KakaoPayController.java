package com.trip.finalProject.kakaoPay.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.finalProject.kakaoPay.service.KakaoApproveResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayService;
import com.trip.finalProject.kakaoPay.service.PaymentVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {
	
	private final KakaoPayService kakaoPayService;
	
	@PostMapping("ready")
	public KakaoPayResponseVO readyToKakaoPay(PaymentVO vo,@RequestParam("quantity") int quantity) {
		
		return kakaoPayService.kakoPayReady(vo,quantity);
	}
	
	@GetMapping("/success")
	public ResponseEntity<KakaoApproveResponseVO> afterPayRequest(@RequestParam("pg_token") String pgToken) {
		KakaoApproveResponseVO kakaoApproveResponseVO = kakaoPayService.approveResponse(pgToken);
		return new ResponseEntity<>(kakaoApproveResponseVO,HttpStatus.OK);
	}

}
