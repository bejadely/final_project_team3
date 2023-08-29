package com.trip.finalProject.like.service;

import java.sql.Date;

import lombok.Data;

@Data
public class LikeVO {
	//post_like
	private String likeId;
	private String memberId;
	private String postId;

	//trip_record
	private String tripTitle;
	private Date startDay;
	private Date endDay;
	
	//mate_recruit
	private String mateTitle;
	private Integer applyNum;
	private String complition;
	private Date deadline;
	private String tripArea;

	
}
