package com.trip.finalProject.kakaoPay.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.kakaoPay.service.KakaoApproveResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayInfoResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayInfoVO;
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
	public KakaoPayResponseVO readyToKakaoPay(PaymentVO vo,@RequestParam("quantity") int quantity,@RequestParam("postId")String postId) {
		
		return kakaoPayService.kakoPayReady(vo,quantity,postId);
	}
	
	@GetMapping("success")
	public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken) {
		KakaoApproveResponseVO approveResponse = kakaoPayService.approveResponse(pgToken);
		// KakaoApproveResponseVO 객체의 tid 값을 가져오기
		//String tid = approveResponseVO.getTid();
		System.out.println(approveResponse);
		// tid 값을 출력
		//mv.addObject("tid", approveResponse.getTid());
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/payment/info");
		return mv;
	}
	
	
	@GetMapping("/info")
	public ModelAndView info() {
	    KakaoPayInfoResponseVO kakaoPayInfoResponseVO = kakaoPayService.infoResponse();
	    System.out.println(kakaoPayInfoResponseVO);
	    
	    //int totalAmount = kakaoPayInfoResponseVO.getAmount().getTotal();
	    
	    KakaoPayInfoVO vo = new KakaoPayInfoVO();
		vo.setApprovedAt(kakaoPayInfoResponseVO.getApproved_at());
		vo.setCalculateStatus("N2");
		vo.setCid(kakaoPayInfoResponseVO.getCid());
		vo.setOrderName(kakaoPayInfoResponseVO.getItem_name());
		vo.setPartnerOrderId(kakaoPayInfoResponseVO.getPartner_order_id());
		vo.setPartnerUserId(kakaoPayInfoResponseVO.getPartner_user_id());
		vo.setStatus(kakaoPayInfoResponseVO.getStatus());
		vo.setTid(kakaoPayInfoResponseVO.getTid());
		vo.setTotalAmount(kakaoPayInfoResponseVO.getAmount().getTotal());
		kakaoPayService.insertPayment(vo);
		System.out.println(vo.getPaymentId());
		
		kakaoPayInfoResponseVO.setAmount(kakaoPayInfoResponseVO.getAmount());
		kakaoPayInfoResponseVO.setApproved_at(kakaoPayInfoResponseVO.getApproved_at());
		kakaoPayInfoResponseVO.setCancel_available_amount(null);
	    
	    ModelAndView mv = new ModelAndView("redirect:/kakaoPaySuccess");
	    
	    return mv;
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
