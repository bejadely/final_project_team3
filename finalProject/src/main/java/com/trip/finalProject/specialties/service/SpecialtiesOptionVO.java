package com.trip.finalProject.specialties.service;

import lombok.Data;

@Data
public class SpecialtiesOptionVO {
	private String optionId;
	private String specialtyType;
	private int price;
	private Integer discountRate;
	private String postId;
	
}
