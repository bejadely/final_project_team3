package com.trip.finalProject.kakaoPay.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.trip.finalProject.kakaoPay.mapper.KakaoPayMapper;
import com.trip.finalProject.kakaoPay.service.KakaoApproveResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayInfoResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayInfoVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayService;
import com.trip.finalProject.kakaoPay.service.PaymentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayServiceImpl implements KakaoPayService {
	
	@Autowired
	KakaoPayMapper kakaoPayMapper;
	
	static final String cid = "TC0ONETIME";
	static final String admin_Key = "7b4adfbd7d95d738f22c53a059516ffc";
	static final String partner_order_id = UUID.randomUUID() + "user1";
	
	private KakaoPayResponseVO kakaoPayResponseVO;
	
	
	@Override
	public KakaoPayResponseVO kakoPayReady(PaymentVO vo, int quantity,String postId) {
		// TODO Auto-generated method stub
		
		//서버로 요청할 Body
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", cid);
		parameters.add("partner_order_id", partner_order_id);
		parameters.add("partner_user_id", "user1");
        parameters.add("item_name", vo.getOrderName());
        parameters.add("item_code", postId);
        parameters.add("quantity", String.valueOf(quantity));
        parameters.add("total_amount", String.valueOf(vo.getTotalAmount()));
        parameters.add("tax_free_amount", String.valueOf(vo.getTotalAmount()));
        parameters.add("approval_url", "http://localhost:8787/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8787/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8787/payment/fail"); // 실패 시 redirect url
		
        //파라미터, 헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters,this.getHeaders());
		
		//외부에 보낼 URL
		RestTemplate restTemplate = new RestTemplate();
		
		kakaoPayResponseVO = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/ready", requestEntity, KakaoPayResponseVO.class);
		
		return kakaoPayResponseVO;
	}
	
    
    //결제 완료 승인
    public KakaoApproveResponseVO approveResponse(String pgToken) {
    	
    	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    	parameters.add("cid", cid);
    	parameters.add("tid", kakaoPayResponseVO.getTid());
    	parameters.add("partner_order_id",partner_order_id );
    	parameters.add("partner_user_id", "user1");
    	parameters.add("pg_token", pgToken);
    	
    	// 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        
        KakaoApproveResponseVO kakaoApproveResponseVO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseVO.class);
                
        return kakaoApproveResponseVO;
    }
    
    //주문조회
    public KakaoPayInfoResponseVO infoResponse(String tid) {
    	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    	parameters.add("cid", cid);
    	parameters.add("tid", tid);
    	// 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        
        KakaoPayInfoResponseVO kakaoPayInfoResponseVO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/order",
                requestEntity,
                KakaoPayInfoResponseVO.class);
                
        return kakaoPayInfoResponseVO;
    }
    
    
    
    
    //결제환불
//    public KakaoCancelResponse kakaoCancel() {
//    	MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
//    	parameter.add("cid", cid);
//    	parameter.add("tid", kakaoPayResponseVO.getTid());
//    	parameter.add("cancel_amount",);
//    	
//    }
    
    
  //서버로 요청할 Header
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    //결제 테이블 저장
	@Override
	public int insertPayment(KakaoPayInfoVO kakaoPayInfoVO) {
		// TODO Auto-generated method stub
		return kakaoPayMapper.insertPaymentInfo(kakaoPayInfoVO);
	}


	@Override
	public int insertPurchase(KakaoPayInfoResponseVO kakaoPayInfoResponseVO) {
		// TODO Auto-generated method stub
		return kakaoPayMapper.insertPurchaseInfo(kakaoPayInfoResponseVO);
	}
    
}
