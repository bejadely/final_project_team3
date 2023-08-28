package com.trip.finalProject.kakaoPay.service;

import lombok.Data;

@Data
public class AmountVO {
	 private int total; // 총 결제 금액
	 private int tax_free; // 비과세 금액
	 private int tax; // 부가세 금액
	
}
