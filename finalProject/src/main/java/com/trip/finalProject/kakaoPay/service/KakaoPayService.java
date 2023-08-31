package com.trip.finalProject.kakaoPay.service;


public interface KakaoPayService {
	public KakaoPayResponseVO kakoPayReady(PaymentVO vo,int quantity, String postId, String specialtyType);
	
	public KakaoApproveResponseVO approveResponse(String pgToken);
	
	public KakaoPayInfoResponseVO infoResponse(String tid);
	
	public int insertPayment(KakaoPayInfoVO kakaoPayInfoVO);
	
	public int insertPurchase(KakaoPayInfoResponseVO kakaoPayInfoResponseVO);
	
	public KakaoCancelResponseVO KakaoCancelResponse(KakaoPayInfoResponseVO kakaoPayInfoResponseVO);
}
