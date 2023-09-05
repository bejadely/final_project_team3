package com.trip.finalProject.kakaoPay.service;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentVO {
	private String paymentId;
	private String partnerUserId; 
	private String orderName;
	private Integer totalAmount;
	private String tid;
	private String cid;
	private String status;
	private String calculateStatus;
	private String partnerOrderId;
	private String pgToken;
	private Date approvedAt;
	private String itemCode;
	
	
}
