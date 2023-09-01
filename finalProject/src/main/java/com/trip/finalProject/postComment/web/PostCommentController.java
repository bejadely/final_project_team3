package com.trip.finalProject.postComment.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
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
	@ResponseBody
	public Map<String, Object> commentList(String postId, Model model) {
		//System.out.println(postId);
		postCommentService.getPostCommentAll(postId);
		//System.out.println(postCommentService.getPostCommentAll(postId));
		//model.addAttribute("cmtList", postCommentService.getPostCommentAll(postId));
		System.out.println(model);
		List<PostCommentVO>  commentList = postCommentService.getPostCommentAll(postId);
		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("cmtList", commentList);
		return map;
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
