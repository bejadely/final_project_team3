package com.trip.finalProject.like.service;

import java.sql.Date;

import lombok.Data;

@Data
public class LikeVO {
	//post_like
	private String likeId;
	private String memberId;
	private String postId;
	//package_regist
	private String name;
	private String locationId;
	private String tourTheme;
	private Date startDate;
	private Date endDate;
	private String completion;
	//trip_record
	private String tripTitle;
	private Date startDay;
	private Date endDay;
	//specialties_resigt
	private String title;
	private String type;
	private int price;
	
}
