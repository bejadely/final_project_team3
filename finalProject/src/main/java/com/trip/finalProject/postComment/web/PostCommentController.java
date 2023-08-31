package com.trip.finalProject.postComment.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.postComment.service.PostCommentService;
import com.trip.finalProject.postComment.service.PostCommentVO;

@Controller
public class PostCommentController {
	@Autowired
	PostCommentService postCommentService;
	
	//해당 게시글의 전체 댓글 조회(ajax)
	@GetMapping("/commentList")
	public String commentList(PostCommentVO postCommentVO) {
		postCommentService.getPostCommentAll();
		return null;
	}
	
	//댓글 저장 (ajax)
	@PostMapping("/insertComment")
	@ResponseBody
	public String insertComment(PostCommentVO postCommentVO) {
		postCommentService.insertPostComment(postCommentVO);
		//System.out.println(postCommentVO);
		return null;
	}
	
}
