package com.trip.finalProject.postComment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.postComment.mapper.PostCommentMapper;
import com.trip.finalProject.postComment.service.PostCommentService;
import com.trip.finalProject.postComment.service.PostCommentVO;

@Service
public class PostCommentServiceImpl implements PostCommentService {
	@Autowired
	PostCommentMapper postCommentMapper;
	
	//해당 게시글의 전체 게시글 조회
	@Override
	public List<PostCommentVO> getPostCommentAll() {
		return postCommentMapper.selectAllPostComment();
	}
	
	// 댓글 등록
	@Override
	public int insertPostComment(PostCommentVO postCommentVO) {
		return postCommentMapper.insertPostComment(postCommentVO);
	}

}
