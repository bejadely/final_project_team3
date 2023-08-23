package com.trip.finalProject.festival.service;



import java.util.Date;

import lombok.Data;

@Data
public class FestivalInfoVO {
	private String festivalId;
	private String areaCode;
	private String sigunguCode;
	private String festivalName;
	private String startDate;
	private String endDate;
	private String festivalImg;
	private String contentId;
	private Date saveDate;
}
