package com.trip.finalProject.kakaoPay.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.cart.service.CartService;
import com.trip.finalProject.cart.service.CartVO;
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
	
	@Autowired
	CartService cartService;
	
	private final KakaoPayService kakaoPayService;
	
	@PostMapping("/ready")
	public KakaoPayResponseVO readyToKakaoPay(PaymentVO vo,@RequestParam("quantity") int quantity,@RequestParam(value="postId", required=false)String postId, String specialtyType) {
		return kakaoPayService.kakoPayReady(vo,quantity,postId,specialtyType);

	}
	

	@GetMapping("/success")
	public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken, String specialtyType, String partner_order_id, String partner_user_id,String postId) {
		KakaoApproveResponseVO approveResponse = kakaoPayService.approveResponse(pgToken,partner_order_id,partner_user_id);
		// KakaoApproveResponseVO 객체의 tid 값을 가져오기
		//String tid = approveResponseVO.getTid();
		// tid 값을 출력
		//mv.addObject("tid", approveResponse.getTid());
		ModelAndView mv = new ModelAndView();
		mv.addObject("tid",approveResponse.getTid());
		mv.addObject("specialtyType",specialtyType);
		mv.addObject("postId",postId);
		mv.setViewName("redirect:/payment/info");
		
		
		return mv;
	}
	
	
	@GetMapping("/info")
	public ModelAndView info(@ModelAttribute KakaoApproveResponseVO  approveResponse) {
	    KakaoPayInfoResponseVO kakaoPayInfoResponseVO = kakaoPayService.infoResponse(approveResponse.getTid());
	    //int totalAmount = kakaoPayInfoResponseVO.getAmount().getTotal();
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
		if(approveResponse.getPostId().substring(0,3).equals("PKG")) {
			KakaoPayInfoResponseVO kakaoVO = new KakaoPayInfoResponseVO();
			kakaoVO.setQuantity(kakaoPayInfoResponseVO.getQuantity());
			kakaoVO.setPostId(kakaoPayInfoResponseVO.getPostId());
			kakaoPayService.updatePackageQuantity(kakaoVO);
			
		}
		System.out.println(approveResponse.getPostId().substring(0,3));
		System.out.println(kakaoPayInfoResponseVO.getItem_code());
		//주문 상세 테이블 등록
		if(kakaoPayInfoResponseVO.getItem_code() == null || kakaoPayInfoResponseVO.getItem_code().equals("")) {
			
			kakaoPayInfoResponseVO.setSpecialtyType(approveResponse.getSpecialtyType());
			kakaoPayInfoResponseVO.setPaymentId(vo.getPaymentId());
			kakaoPayInfoResponseVO.setPrice(kakaoPayInfoResponseVO.getAmount().getTotal());
			kakaoPayInfoResponseVO.setPostId(approveResponse.getPostId());
			kakaoPayInfoResponseVO.setItem_code(kakaoPayInfoResponseVO.getItem_code());
			kakaoPayInfoResponseVO.setItem_name(kakaoPayInfoResponseVO.getItem_name());
			kakaoPayInfoResponseVO.setMemberId(kakaoPayInfoResponseVO.getPartner_user_id());
			kakaoPayInfoResponseVO.setQuantity(kakaoPayInfoResponseVO.getQuantity());
			kakaoPayInfoResponseVO.setOrderDate(kakaoPayInfoResponseVO.getApproved_at().replace("T", " "));
			kakaoPayInfoResponseVO.setOrderStatus(kakaoPayInfoResponseVO.getStatus());
			kakaoPayInfoResponseVO.setCid(kakaoPayInfoResponseVO.getCid());
			kakaoPayInfoResponseVO.setTid(kakaoPayInfoResponseVO.getTid());
			kakaoPayInfoResponseVO.setCancelAmount(kakaoPayInfoResponseVO.getCancel_available_amount().getTotal());
			kakaoPayInfoResponseVO.setCancelTaxFreeAmount(kakaoPayInfoResponseVO.getCancel_available_amount().getTax_free());
			
			 kakaoPayService.insertPurchase(kakaoPayInfoResponseVO);
		}else {
			List<CartVO> cartProduct = new ArrayList<>();
			String[] cartIdArray = kakaoPayInfoResponseVO.getItem_code().split(",");
			for(int i=0; i<cartIdArray.length; i++) {
				cartProduct.addAll( cartService.cartInfo(cartIdArray[i]));						
			}
			//cartProduct = cartService.cartInfo(cartIdArray);
			
			 System.out.println(cartProduct); 
			 for(int i=0; i<cartProduct.size(); i++) {
				 kakaoPayInfoResponseVO.setPostId(cartProduct.get(i).getPostId());
				 kakaoPayInfoResponseVO.setMemberId(cartProduct.get(i).getMemberId());
				 kakaoPayInfoResponseVO.setQuantity(cartProduct.get(i).getQuantity());
				 kakaoPayInfoResponseVO.setItem_name(cartProduct.get(i).getCartName());
				 kakaoPayInfoResponseVO.setPrice(cartProduct.get(i).getPrice()*cartProduct.get(i).getQuantity());
				 kakaoPayInfoResponseVO.setTid(kakaoPayInfoResponseVO.getTid());
				 kakaoPayInfoResponseVO.setCid(kakaoPayInfoResponseVO.getCid());
				 kakaoPayInfoResponseVO.setOrderStatus(kakaoPayInfoResponseVO.getStatus());
				 kakaoPayInfoResponseVO.setOrderDate(kakaoPayInfoResponseVO.getApproved_at().replace("T", " "));
				 kakaoPayInfoResponseVO.setPaymentId(vo.getPaymentId());
				 kakaoPayInfoResponseVO.setSpecialtyType(cartProduct.get(i).getOptionId());
				 kakaoPayInfoResponseVO.setCancelAmount(cartProduct.get(i).getPrice()*cartProduct.get(i).getQuantity());
				 kakaoPayInfoResponseVO.setCancelTaxFreeAmount(cartProduct.get(i).getPrice()*cartProduct.get(i).getQuantity());
				 kakaoPayService.insertPurchase(kakaoPayInfoResponseVO);
				 kakaoPayService.deleteCart(cartProduct.get(i).getCartId());
			  }
			 
		}	
	   	
	    ModelAndView mv = new ModelAndView("redirect:/kakaoPaySuccess");
	    
	    return mv;
	}
	
	@PostMapping("/refund")
	public ModelAndView refund(KakaoPayInfoResponseVO vo) {
		System.out.println(vo);
		kakaoPayService.KakaoCancelResponse(vo);
		kakaoPayService.updatePurchase(vo);
		ModelAndView mv = new ModelAndView("/payment/kakaoRefundSuccess");
		return mv;
	}
	
	
	
	@GetMapping("/cancel")
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
