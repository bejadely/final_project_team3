package com.trip.finalProject.kakaoPay.service;


public interface KakaoPayService {
	public KakaoPayResponseVO kakoPayReady(PaymentVO vo,int quantity);
	
	public KakaoApproveResponseVO approveResponse(String pgToken);
	
	public KakaoPayInfoResponseVO infoResponse();
}
