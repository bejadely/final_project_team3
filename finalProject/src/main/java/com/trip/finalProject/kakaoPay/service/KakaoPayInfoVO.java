package com.trip.finalProject.kakaoPay.service;



import lombok.Data;

@Data
public class KakaoPayInfoVO {
	private String paymentId;
	private String partnerUserId;
	private String orderName;
	private int totalAmount;
	private String tid;
	private String cid;
	private String status;
	private String calculateStatus;
	private String partnerOrderId;
	private String pg_token;
	private String approved_at;
}
