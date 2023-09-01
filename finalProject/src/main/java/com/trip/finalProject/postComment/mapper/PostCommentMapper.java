package com.trip.finalProject.postComment.mapper;

import java.util.List;

import com.trip.finalProject.postComment.service.PostCommentVO;

public interface PostCommentMapper {
	//해당 게시글의 전체 댓글 조회
	public List<PostCommentVO> selectAllPostComment(String postId);
	
	//댓글 저장
	public int insertPostComment(PostCommentVO postCommentVO);
}

