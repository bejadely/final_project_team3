package com.trip.finalProject.kakaoPay.mapper;

import com.trip.finalProject.kakaoPay.service.KakaoPayInfoResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayInfoVO;

public interface KakaoPayMapper {
	public int insertPaymentInfo(KakaoPayInfoVO kakaoPayInfoVO);
	
	public int insertPurchaseInfo(KakaoPayInfoResponseVO kakaoPayInfoResponseVO);
}
