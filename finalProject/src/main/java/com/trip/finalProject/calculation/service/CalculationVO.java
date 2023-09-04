package com.trip.finalProject.calculation.service;

import lombok.Data;

@Data
public class CalculationVO {
	
	public String calId;
	public Double calAmount;
	public String calStatus;
	public String postId;
	public String calDate;
	
	// 조인을 위한 필드 추가
	public String writerId;
	
	public String bankCode;
	public String accountNumber;
}
