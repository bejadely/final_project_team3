package com.trip.finalProject.calculation.service;

import lombok.Data;

@Data
public class CalculationVO {
	
	private String calId;
	private String calAmount;
	private String calStatus;
	private String postId;
	private String calDate;
	
	// 조인을 위한 필드 추가
	private String writerId;
	
	private String bankCode;
	private String accountNumber;
}
