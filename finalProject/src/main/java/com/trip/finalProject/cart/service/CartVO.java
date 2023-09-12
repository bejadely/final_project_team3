package com.trip.finalProject.cart.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CartVO {
//	CART_ID	NUMBER	No
//	POST_ID	VARCHAR2(13 BYTE)	No
//	OPTION_ID	VARCHAR2(13 BYTE)	No
//	CART_TYPE	CHAR(2 BYTE)	No
//	QUANTITY	NUMBER	No
//	PRICE	NUMBER	No
//	MEMBER_ID	VARCHAR2(14 BYTE)	No
//	REG_DATE	DATE	Yes
	private String cartId;
	private String postId;
	private String optionId;
	private Integer quantity;
	private Integer price;
	private String memberId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date regDate;
	private String cartName;
	private String specialtyType;
	private String loadingImg;

	
}
