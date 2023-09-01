package com.trip.finalProject.postComment.service;

import java.util.List;

public interface PostCommentService {
	
	//해당 게시글의 전체 댓글 조회
	public List<PostCommentVO> getPostCommentAll(String postId);
	
	//댓글 등록
	public int insertPostComment(PostCommentVO postCommentVO);
	

}
