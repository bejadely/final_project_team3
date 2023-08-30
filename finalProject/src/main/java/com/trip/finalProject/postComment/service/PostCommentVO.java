package com.trip.finalProject.postComment.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostCommentVO {
	
	//post_comment
	private String commentId;
	private String postId;
	private int cmtGrp;
	private int cmtGrps;
	private String writerId;
	private String deleteYn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifiyDate;
	private String cmtContent;
	
}
