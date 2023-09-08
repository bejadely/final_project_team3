package com.trip.finalProject.trip.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;

import lombok.Data;

@Data
public class TripVO {
	
	//map
	private String mapId;
	private int mapNo;
	private String mapName;
	private double mapLat;
	private double mapLng;
	private String postId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadDate;
	private String tripDate;
	
	//member
	private String memberId;
	private String memberName;
	private String password;
	private String authority;
	private int punishCount;

	//file upload관련
	private String fileId;
	private String originImg;
	private String savedImg;
	private int fileNo;
	private String imgType;
	private String loadingImg;
	
	//trip_record
	private String writerId;
	private String tripTitle;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDay;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDay;
	private String tripDisclose;
	private String tripSave;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registDay;
	private String tripReview;
	private String tripShare;
	
	//trip_record_memo
	private String memoId;
	private int memoNo;
	private String content;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date memoDate;
	
	//에디터
	private List<AttachedFileVO> attachList;
	private List<AttachedFileVO> editorAttachList;
	

	//선택한 여행 지역을 최초로 지도에 표시하기 위한 위도,경도 값
	private String tripArea;
	
	private String kakaoMap;
	
	
}
