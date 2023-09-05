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
	@GetMapping("/common/myMtList")
	public String paList(Model model,
			LikeVO likeVO,
			@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		String memberId = "1";
		int total = liService.mtCountInfo(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		likeVO.setMemberId(memberId);
		List<LikeVO> paList = liService.mtAllLikeInfo(likeVO, pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "myPage/likeList/mtList";
	}
	
	// 여행계획 전체 조회
	@GetMapping("/common/myTrList")
	public String trList(Model model
			, LikeVO likeVO
			, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			, @RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		String memberId = likeVO.getMemberId();
		int total = liService.trCountInfo(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		likeVO.setMemberId(memberId);
		List<LikeVO> paList = liService.trAllLikeInfo(likeVO, pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "myPage/likeList/trList";
	}
}
