package com.trip.finalProject.kakaoPay.service;


public interface KakaoPayService {
	public KakaoPayResponseVO kakoPayReady(PaymentVO vo,int quantity, String postId, String specialtyType);
	
	public KakaoApproveResponseVO approveResponse(String pgToken, String partner_order_id, String partner_user_id);
	
	public KakaoPayInfoResponseVO infoResponse(String tid);
	
	public int insertPayment(KakaoPayInfoVO kakaoPayInfoVO);
	
	public int insertPurchase(KakaoPayInfoResponseVO kakaoPayInfoResponseVO);
	
	public KakaoPayInfoResponseVO KakaoCancelResponse(KakaoPayInfoResponseVO kakaoPayInfoResponseVO);
	
	public int updatePurchase(KakaoPayInfoResponseVO vo);

	public void deleteCart(String cartId);
	
}
