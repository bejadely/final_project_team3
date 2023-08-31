package com.trip.finalProject.postComment.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostCommentVO {
	
	//post_comment
	private String commentId; 					//댓글id - 댓글 고유번호
	private String cmtContent;					//댓글 내용
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	private Date registDate;					//댓글 작성일
	private String postId;						//게시글id
	private String writerId;					//작성자id
	private String cmtStyle;					//댓글 대댓글 구분
	private String cmtParentId;					//부모댓글 아이디
	private String delYn;						//댓글 삭제 여부
	
}
