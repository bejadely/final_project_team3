package com.trip.finalProject.like.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.like.service.LikeService;
import com.trip.finalProject.like.service.LikeVO;

@Controller
@RequestMapping("/")
public class LikeController {

	@Autowired
	LikeService liService;
	
	// 여행메이트 전체 조회
	@GetMapping("myMtList")
	public String paList(Model model, 
			@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		String memberId = "1";
		int total = liService.mtCountInfo(memberId);
		System.out.println("토탈 :" + total);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		System.out.println("total: "+pagingVO);
		List<LikeVO> paList = liService.mtAllLikeInfo(pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "myPage/likeList/mtList";
	}
	
	// 여행계획 전체 조회
	@GetMapping("/myTrList")
	public String trList(Model model, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		LikeVO likeVO = new LikeVO();
		String memberId = likeVO.getMemberId();
		int total = liService.trCountInfo(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<LikeVO> paList = liService.trAllLikeInfo(pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "myPage/likeList/trList";
	}
}
