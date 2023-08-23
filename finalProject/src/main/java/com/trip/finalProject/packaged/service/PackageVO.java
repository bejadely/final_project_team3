package com.trip.finalProject.packaged.service;

import java.util.Date;
import java.util.List;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;

import lombok.Data;

@Data
public class PackageVO {
	private String postId;
	private String content;
	private String mainImage;
	private String name;
	private int price;
	private Date startDate;
	private Date endDate;
	private Date deadlineDate;
	private int maxReservation;
	private String tourTheme;
	
	private List<AttachedFileVO> attachList;
}
