package com.trip.finalProject.packaged.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;

import lombok.Data;

@Data
public class PackageVO {
	private String postId;
	private String content;
	private String name;
	private int price;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deadlineDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date regDate;
	private int maxReservation;
	private String tourTheme;
	private String savedImg;
	private String imgType;
	private String loadingImg;
	private String writerId;
	private String completion;
	private int nowReservation;
	
	private List<AttachedFileVO> attachList;
	private List<AttachedFileVO> editorAttachList;
	
	private String calStatus;
	//멤버 테이블 정보
	private String memberId;
	private int quantity;
	private Date orderDate;
	private String memberName;
	private String nationality;
	private String gender;
	private Date birthDate;
	private String orderStatus;
	
}
