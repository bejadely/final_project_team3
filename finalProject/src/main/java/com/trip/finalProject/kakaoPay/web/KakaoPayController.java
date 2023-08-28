package com.trip.finalProject.kakaoPay.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.kakaoPay.service.KakaoPayInfoResponseVO;
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
	
	@GetMapping("success")
	public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken) {
		kakaoPayService.approveResponse(pgToken);
		ModelAndView mv = new ModelAndView("/payment/kakaPaySuccess");
		return mv;
	}
	
	@PostMapping("info")
	public ResponseEntity infoPurchase() {
		return null;
	}
	
	@GetMapping("cancel")
	public ModelAndView cancel() {
		ModelAndView mv = new ModelAndView("/package/packageList");
		return mv;
	}
	
	@GetMapping("/fail")
    public ModelAndView fail() {
		ModelAndView mv = new ModelAndView("/package/packageList");
		return mv;
    }
	
}
