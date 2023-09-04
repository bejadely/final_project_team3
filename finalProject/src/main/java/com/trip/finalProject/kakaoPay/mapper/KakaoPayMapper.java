package com.trip.finalProject.kakaoPay.mapper;

import com.trip.finalProject.kakaoPay.service.KakaoPayInfoResponseVO;
import com.trip.finalProject.kakaoPay.service.KakaoPayInfoVO;

public interface KakaoPayMapper {
	//결제 등록
	public int insertPaymentInfo(KakaoPayInfoVO kakaoPayInfoVO);
	
	//주문 상세 내역 등록
	public int insertPurchaseInfo(KakaoPayInfoResponseVO kakaoPayInfoResponseVO);
	
	//주문 상태 변경
	public int updatePurchaseStatus(KakaoPayInfoResponseVO vo);
	
}
