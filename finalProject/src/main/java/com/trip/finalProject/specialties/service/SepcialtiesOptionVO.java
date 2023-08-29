package com.trip.finalProject.specialties.service;

import lombok.Data;

@Data
public class SepcialtiesOptionVO {
	private String optionId;
	private String specialityType;
	private int price;
	private Integer discount_rate;
	private String post_id;
	
}
