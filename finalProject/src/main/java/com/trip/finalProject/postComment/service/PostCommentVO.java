package com.trip.finalProject.postComment.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostCommentVO {
	
	//post_comment
	private String commentId; 					//댓글id - 댓글 고유번호
	private String postId;						//게시글id
	private int cmtNum;							//댓글번호
	private String writerId;					//작성자id
	private String cmtContent;					//댓글 내용
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	private Date registDate;					//댓글 작성일
	private int step;							//댓글 대댓글 구분 0,1			
	
}