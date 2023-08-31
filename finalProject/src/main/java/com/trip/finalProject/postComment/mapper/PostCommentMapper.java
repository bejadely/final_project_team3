package com.trip.finalProject.postComment.mapper;

import com.trip.finalProject.postComment.service.PostCommentVO;

public interface PostCommentMapper {
	
	//댓글 저장
	public int insertPostComment(PostCommentVO postCommentVO);
}

