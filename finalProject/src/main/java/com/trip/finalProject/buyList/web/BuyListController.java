package com.trip.finalProject.buyList.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.buyList.service.BuyListService;
import com.trip.finalProject.buyList.service.BuyListVO;
import com.trip.finalProject.common.PagingVO;

@Controller
@RequestMapping("/")
public class BuyListController {
	
	@Autowired
	BuyListService buyService;
	
	// 여행메이트 전체 조회
		@GetMapping("/common/buyPkList")
		public String paList(Model model, 
				@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
				@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
			String memberId = "1";
			int total = buyService.pkCountInfo(memberId);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			System.out.println("total: "+pagingVO);
			List<BuyListVO> buyList = buyService.pkAllLikeInfo(pagingVO);

			model.addAttribute("list", buyList);
			model.addAttribute("paging", pagingVO);

			return "myPage/buyList/pkList";
		}
		
		// 여행계획 전체 조회
		@GetMapping("/common/buySpList")
		public String trList(Model model, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
				@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
			String memberId = "1";
			int total = buyService.spCountInfo(memberId);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			List<BuyListVO> buyList = buyService.spAllLikeInfo(pagingVO);

			model.addAttribute("list", buyList);
			model.addAttribute("paging", pagingVO);

			return "myPage/buyList/spList";
		}
		
		@GetMapping("/common/buyPkSelect")
		public String selectPk(BuyListVO buyVO,Model model) {
			BuyListVO findVo = buyService.selectPkInfo(buyVO);
			model.addAttribute("list", findVo);
						
			return "myPage/buyList/detailBuyPkInfo";
		}
		
		@GetMapping("/common/buySpSelect")
		public String selectSp(BuyListVO buyVO,Model model) {
			BuyListVO findVo = buyService.selectSpInfo(buyVO);
			model.addAttribute("list", findVo);
						
			return "myPage/buyList/detailBuySpInfo";
		}

}
