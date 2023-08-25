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
//	private Date updateDate;	//	업데이트 날짜
	private String address;
	private String content;	//	축제정보
//	private String deleteYN;	//	삭제유무
}
