package com.trip.finalProject.tripMate.service;

import java.util.Date;

import lombok.Data;

@Data
public class PostCommentVO {
	private String commentId;
	private String postId;
	private String originCommentId;
	private String writerId;
	private String content;
	private Date registDate;
}
