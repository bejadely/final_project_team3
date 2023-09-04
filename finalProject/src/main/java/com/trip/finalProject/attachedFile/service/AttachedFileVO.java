package com.trip.finalProject.attachedFile.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AttachedFileVO {
	private String postId;
	private String fileId;
	private String memberId;
	private String originImg;
	private String savedImg;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date uploadDate;
	private String imgType;
	private String loadingImg;
}
