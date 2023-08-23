package com.trip.finalProject.attachedFile.service;

import java.util.Date;

import lombok.Data;

@Data
public class AttachedFileVO {
	private String postId;
	private String fileId;
	private String memberId;
	private String originImg;
	private String savedImg;
	private int fileNo;
	private Date uploadDate;
	private String imgType;
}
