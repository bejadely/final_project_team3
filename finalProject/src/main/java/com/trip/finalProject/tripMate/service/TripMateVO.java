package com.trip.finalProject.tripMate.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TripMateVO {
		//map
		private String mapId;
		private int mapNo;
		private String mapName;
		private double mapLat;
		private double mapLng;
		private String postId;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date uploadDate;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date tripDate;
		
		//member
		private String memberId;
		private String memberName;
		private String password;
		private String authority;
		private int punishCount;
		
		//file
		private String fileId;
		private String originImg;
		private String savedImg;
		private int fileNo;
		private String imgType;
		
		//mate_recruit 메이트 글 등록
		private String mateTitle;
		private String mateContent;
		private int mateExpense;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date startDay;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date endDay;
		private String mateStyle;
		private String mateTag;
		private int mateMax;
		private int applyNum;
		private String complition;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date writeDate;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date deadline;
		private int hit;
		private String writerId;
		
		//mate_apply 메이트 신청
		private String applyId;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date applyDay;
		private String applyInfo;
		private String approveStatus;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date approveDate;
		
		//선택한 여행 지역을 최초로 지도에 표시하기 위한 위도,경도 값
		private String tripArea;
		
	
}
