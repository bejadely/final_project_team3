package com.trip.finalProject.guideMain.service;

import lombok.Data;

import java.util.Date;

@Data
public class PackageRegistVO {
	private String name;
	private String tourTheme;
	private int price;
	private String memberIdString;
	private String writerId;
	private Date orderDate;
	
}
