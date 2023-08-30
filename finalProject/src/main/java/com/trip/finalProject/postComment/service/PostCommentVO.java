package com.trip.finalProject.postComment.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostCommentVO {
	
	//post_comment
	private String commentId; 					//댓글id
	private String postId;						//게시글id		
	private int cmtGrp;							//댓글 소속의 번호
	private int cmtGrps;						//같은 댓글 소속에서의 순서
	private int cmtGrpl;						//댓글과 대댓글 구분 0,1 
	private String writerId;					//작성자id
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	private Date registDate;					//댓글 작성일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifiyDate;					//댓글 수정일
	private String cmtContent;					//댓글 내용
	
}
