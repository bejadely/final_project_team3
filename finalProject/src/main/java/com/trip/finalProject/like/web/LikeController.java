package com.trip.finalProject.like.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.like.service.LikeService;
import com.trip.finalProject.like.service.LikeVO;
import com.trip.finalProject.trip.service.TripVO;

@Controller
public class LikeController {

	@Autowired
	LikeService liService;
	
	// 패키지 전체 조회
	@GetMapping("myPaList")
	public String paList(Model model, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		LikeVO likeVO = new LikeVO();
		String memberId = likeVO.getMemberId();
		int total = liService.paCountInfo(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<LikeVO> paList = liService.paAllLikeInfo(pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "myPage/likeList/paList";
	}
	
	// 패키지 전체 조회
	@GetMapping("mySpList")
	public String spList(Model model, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		LikeVO likeVO = new LikeVO();
		String memberId = likeVO.getMemberId();
		int total = liService.spCountInfo(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<LikeVO> paList = liService.spAllLikeInfo(pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "trip/tripRecordList";
	}
	
	// 패키지 전체 조회
	@GetMapping("myTrList")
	public String trList(Model model, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		LikeVO likeVO = new LikeVO();
		String memberId = likeVO.getMemberId();
		int total = liService.trCountInfo(memberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<LikeVO> paList = liService.trAllLikeInfo(pagingVO);

		model.addAttribute("list", paList);
		model.addAttribute("paging", pagingVO);

		return "trip/tripRecordList";
	}
}
