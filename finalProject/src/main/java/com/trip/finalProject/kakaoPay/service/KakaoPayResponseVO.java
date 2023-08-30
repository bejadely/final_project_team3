package com.trip.finalProject.kakaoPay.service;

import lombok.Data;

@Data
public class KakaoPayResponseVO {
	//결제 고유 번호
	private String tid; 
		
	//pc 결제 페이지 url
	private String next_redirect_pc_url;
}
