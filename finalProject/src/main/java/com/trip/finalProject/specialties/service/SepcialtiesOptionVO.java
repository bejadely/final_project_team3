package com.trip.finalProject.specialties.service;

import lombok.Data;

@Data
public class SepcialtiesOptionVO {
	private String optionId;
	private String specialtyType;
	private int price;
	private Integer discount_rate;
	private String postId;
	
}
