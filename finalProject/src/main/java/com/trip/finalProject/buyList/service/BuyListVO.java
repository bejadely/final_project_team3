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
	private Integer puPrice;
	private Date orderDate;
	private String orderStatus;
	private String paymentId;
	private String cid;
	private String tid;
	private Integer cancelAmount;
	private Integer cancelTaxFreeAmount;
	//pakage_regist
	private Date startDate;
	private Date endDate;
	private Date deadlineDate;
	private String completion;
	private Integer pkPrice;
	private String name;
	private String tourTheme;
	//sepcialties_resigt
	private String type;
	private String title;
	private String discount;
	//specialties_option
	private String specialtyType;
	private Integer discountRate;
	//attached_fild
	private String loadingImg;
}
