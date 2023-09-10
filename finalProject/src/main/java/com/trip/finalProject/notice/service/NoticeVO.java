package com.trip.finalProject.notice.service;

import java.util.List;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;

import lombok.Data;

@Data
public class NoticeVO {
	private Integer noticeNumber;
	private String title;
	private String content;
	private String registDate;
	private String updateDate;
	private String status;
	private String release;
	private String noticeType;
	private String writerId;
	private String hit;
	
	private String page;
	
	private Integer rn;

	private List<AttachedFileVO> attachList;
	private List<AttachedFileVO> editorAttachList;
	
	
	

}
