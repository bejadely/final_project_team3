package com.trip.finalProject.postComment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.postComment.mapper.PostCommentMapper;
import com.trip.finalProject.postComment.service.PostCommentService;
import com.trip.finalProject.postComment.service.PostCommentVO;

@Service
public class PostCommentServiceImpl implements PostCommentService {
	@Autowired
	PostCommentMapper postCommentMapper;
	
	// 댓글 등록
	@Override
	public int insertPostComment(PostCommentVO postCommentVO) {
		return postCommentMapper.insertPostComment(postCommentVO);
	}

}
