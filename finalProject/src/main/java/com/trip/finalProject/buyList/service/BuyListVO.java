package com.trip.finalProject.buyList.service;

import java.util.Date;

import lombok.Data;

@Data
public class BuyListVO {
	//purchase
	private String orderDetailId;
	private String postId;
	private String memberId;
	private Integer quantity;
	private Integer price;
	private Date orderDate;
	private String orderStatus;
	private String paymentId;
	//pakage_regist
	private Date startDate;
	private Date endDate;
	private Date deadlineDate;
	private String completion;
	private String name;
	private String tourTheme;
	//sepcialties_resigt
	private String type;
	private String title;
	private String discount;
	//specialties_option
	private String specialtyType;
	private Integer discountRate;
}
