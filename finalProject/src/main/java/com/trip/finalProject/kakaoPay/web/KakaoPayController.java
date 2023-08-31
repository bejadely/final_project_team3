package com.trip.finalProject.kakaoPay.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public KakaoPayResponseVO readyToKakaoPay(PaymentVO vo,@RequestParam("quantity") int quantity,@RequestParam("postId")String postId, String specialtyType) {
		return kakaoPayService.kakoPayReady(vo,quantity,postId,specialtyType);
	}
	
	@GetMapping("success")
	public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken, String specialtyType) {
		KakaoApproveResponseVO approveResponse = kakaoPayService.approveResponse(pgToken);
		// KakaoApproveResponseVO 객체의 tid 값을 가져오기
		//String tid = approveResponseVO.getTid();
		// tid 값을 출력
		//mv.addObject("tid", approveResponse.getTid());
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("tid",approveResponse.getTid());
		mv.addObject("specialtyType",specialtyType);
		mv.setViewName("redirect:/payment/info");
		
		
		return mv;
	}
	
	
	@GetMapping("/info")
	public ModelAndView info(@ModelAttribute KakaoApproveResponseVO  approveResponse) {
	    KakaoPayInfoResponseVO kakaoPayInfoResponseVO = kakaoPayService.infoResponse(approveResponse.getTid());
	    //int totalAmount = kakaoPayInfoResponseVO.getAmount().getTotal();
	    //System.out.println(kakaoPayInfoResponseVO);
	    //결제 테이블 등록
	    System.out.println(approveResponse.getSpecialtyType());
	    KakaoPayInfoVO vo = new KakaoPayInfoVO();
		vo.setApprovedAt(kakaoPayInfoResponseVO.getApproved_at().replace("T", " "));
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
		
		//주문 상세 테이블 등록
		kakaoPayInfoResponseVO.setSpecialtyType(approveResponse.getSpecialtyType());
		kakaoPayInfoResponseVO.setPaymentId(vo.getPaymentId());
		kakaoPayInfoResponseVO.setPrice(kakaoPayInfoResponseVO.getAmount().getTotal());
		kakaoPayInfoResponseVO.setPostId(kakaoPayInfoResponseVO.getItem_code());
		kakaoPayInfoResponseVO.setMemberId(kakaoPayInfoResponseVO.getPartner_user_id());
		kakaoPayInfoResponseVO.setQuantity(kakaoPayInfoResponseVO.getQuantity());
		kakaoPayInfoResponseVO.setOrderDate(kakaoPayInfoResponseVO.getApproved_at().replace("T", " "));
		kakaoPayInfoResponseVO.setOrderStatus(kakaoPayInfoResponseVO.getStatus());
		kakaoPayInfoResponseVO.setCid(kakaoPayInfoResponseVO.getCid());
		kakaoPayInfoResponseVO.setTid(kakaoPayInfoResponseVO.getTid());
		
		
		System.out.println(kakaoPayInfoResponseVO);
	    kakaoPayService.insertPurchase(kakaoPayInfoResponseVO);
		
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
