package com.trip.finalProject.kakaoPay.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;
import com.trip.finalProject.specialties.service.SpecialtiesService;
import com.trip.finalProject.specialties.service.SpecialtiesVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/common/payment")
@RequiredArgsConstructor
public class KakaoPayController {
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	CartService cartService;
	@Autowired
    HttpSession session;
	private final KakaoPayService kakaoPayService;
	
	@PostMapping("/ready")
	public KakaoPayResponseVO readyToKakaoPay(PaymentVO vo,@RequestParam("quantity") int quantity,@RequestParam(value="postId", required=false)String postId, String specialtyType) throws Exception {
		String sessionId = "";
		if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().replaceAll(" ", "").equals("")) {
			sessionId =  session.getAttribute("sessionId").toString();
        } else {
            throw new Exception("no login");
        }	
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
		mv.setViewName("redirect:/common/payment/info");
		
		
		return mv;
	}
	
	
	@GetMapping("/info")
	public ModelAndView info(@ModelAttribute KakaoApproveResponseVO  approveResponse) {
	    KakaoPayInfoResponseVO kakaoPayInfoResponseVO = kakaoPayService.infoResponse(approveResponse.getTid());
	    //int totalAmount = kakaoPayInfoResponseVO.getAmount().getTotal();
	    KakaoPayInfoVO vo2 = new KakaoPayInfoVO();
		vo2.setApprovedAt(kakaoPayInfoResponseVO.getApproved_at().replace("T", " "));
		vo2.setCalculateStatus("N2");
		vo2.setCid(kakaoPayInfoResponseVO.getCid());
		vo2.setOrderName(kakaoPayInfoResponseVO.getItem_name());
		vo2.setPartnerOrderId(kakaoPayInfoResponseVO.getPartner_order_id());
		vo2.setPartnerUserId(kakaoPayInfoResponseVO.getPartner_user_id());
		vo2.setStatus(kakaoPayInfoResponseVO.getStatus());
		vo2.setTid(kakaoPayInfoResponseVO.getTid());
		vo2.setTotalAmount(kakaoPayInfoResponseVO.getAmount().getTotal());
		
		kakaoPayService.insertPayment(vo2);
		if(approveResponse.getPostId().substring(0,3).equals("PKG")) {
			KakaoPayInfoResponseVO kakaoVO = new KakaoPayInfoResponseVO();
			kakaoVO.setQuantity(kakaoPayInfoResponseVO.getQuantity());
			kakaoVO.setPostId(approveResponse.getPostId());
			PackageVO packageVO = new PackageVO();
			packageVO.setPostId(approveResponse.getPostId());
			PackageVO findVO = packageService.packageInfo(packageVO);
			kakaoVO.setNowReservation(findVO.getNowReservation());
			kakaoVO.setMaxReservation(findVO.getMaxReservation());
			kakaoPayService.updatePackageQuantity(kakaoVO);
			
		}
		//주문 상세 테이블 등록
		if(kakaoPayInfoResponseVO.getItem_code() == null || kakaoPayInfoResponseVO.getItem_code().equals("")) {
			
			kakaoPayInfoResponseVO.setSpecialtyType(approveResponse.getSpecialtyType());
			kakaoPayInfoResponseVO.setPaymentId(vo2.getPaymentId());
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
				 kakaoPayInfoResponseVO.setPaymentId(vo2.getPaymentId());
				 kakaoPayInfoResponseVO.setSpecialtyType(cartProduct.get(i).getOptionId());
				 kakaoPayInfoResponseVO.setCancelAmount(cartProduct.get(i).getPrice()*cartProduct.get(i).getQuantity());
				 kakaoPayInfoResponseVO.setCancelTaxFreeAmount(cartProduct.get(i).getPrice()*cartProduct.get(i).getQuantity());
				 kakaoPayService.insertPurchase(kakaoPayInfoResponseVO);
				 kakaoPayService.deleteCart(cartProduct.get(i).getCartId());
			  }
			 
		}	
	   	
	    ModelAndView mv = new ModelAndView("redirect:/common/buyPkList");
	    
	    return mv;
	}
	
	@PostMapping("/refund")
	public ModelAndView refund(KakaoPayInfoResponseVO vo) {
		System.out.println(vo);
		kakaoPayService.KakaoCancelResponse(vo);
		if(vo.getPostId().substring(0,3).equals("PKG")) {
			kakaoPayService.updatePurchase(vo);
			vo.setQuantity(-vo.getQuantity());  
			PackageVO packageVO = new PackageVO();
			packageVO.setPostId(vo.getPostId());
			PackageVO findVO = packageService.packageInfo(packageVO);
			vo.setNowReservation(findVO.getNowReservation());
			vo.setMaxReservation(findVO.getMaxReservation());
			kakaoPayService.updatePackageQuantity(vo);
		}else {
			kakaoPayService.updatePurchase(vo);
		}
		
		
		ModelAndView mv = new ModelAndView("redirect:/common/buyPkList");
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
