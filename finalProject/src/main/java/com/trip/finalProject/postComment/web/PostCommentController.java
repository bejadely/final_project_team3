package com.trip.finalProject.postComment.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.postComment.service.PostCommentService;
import com.trip.finalProject.postComment.service.PostCommentVO;

@Controller
public class PostCommentController {
	@Autowired
	PostCommentService postCommentService;
	
	//댓글 저장 (ajax)
	@PostMapping("/InsertComment")
	@ResponseBody
	public String insertComment(@RequestBody PostCommentVO postCommentVO) {
		postCommentService.insertPostComment(postCommentVO);
		System.out.println(postCommentVO);
		return "tripMate/tripMateList";
	}
	
	
	
	
	
}
