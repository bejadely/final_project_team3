package com.trip.finalProject.lodging.web;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.lodging.service.LodgingService;
import com.trip.finalProject.lodging.service.LodgingVO;
import com.trip.finalProject.packaged.service.PackageVO;


@Controller
public class LodgingController {
	@Autowired
	LodgingService lodgingService;
	
	@GetMapping("lodging")
	public String lodgingList(Model model
							, @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
							, @RequestParam( name = "keyword", defaultValue = "" ) String keyword
				            , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
				            , @RequestParam( name = "cntPerPage", defaultValue = "5")Integer cntPerPage) {
		int total = lodgingService.lodgingCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		List<LodgingVO> lodgingList = lodgingService.getLodgingList(pagingVO);
		
		model.addAttribute("lodgingList",lodgingList);
		model.addAttribute("paging",pagingVO);
		
		model.addAttribute("area",lodgingService.getLocationList());
		
		//검색어가 없을 경우
		model.addAttribute("keyword",keyword);
		model.addAttribute("searchBy",searchBy);
		return"lodging/lodging";
	
	}
	//특정 조건으로 패키지 검색
		@GetMapping("/searchLodging")
		public String searchPackage(@RequestParam( name = "searchBy" ) String searchBy
								  , @RequestParam( name = "keyword" ) String keyword
								  , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
								  , @RequestParam( name = "cntPerPage", defaultValue = "5") Integer cntPerPage
								  , Model model
								  ,LodgingVO lodgingVO) {
			//전체 조회될 패키지 수
			int total = lodgingService.lodgingCountTitle(keyword);
			
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			model.addAttribute("area",lodgingService.getLocationList());
			//제목으로 검색기능 수행
			lodgingVO.setTitle(keyword);
			List<LodgingVO> list = lodgingService.searchPackageByTitle(lodgingVO, pagingVO);
			model.addAttribute("lodgingList",list);
			model.addAttribute("paging",pagingVO);
			// 검색결과 기억을 위해 keyword와 searchBy 담기
			model.addAttribute("keyword", keyword);
			model.addAttribute("searchBy", searchBy);
			
			return "lodging/lodging";
		}
	
	@GetMapping("apitest")
	public String apitest() {
		return"lodging/apitest";
	}
	
	@GetMapping("/areaList")
	@ResponseBody
	public List<LodgingVO> areaList(LodgingVO lodgingVO) {
		
		return lodgingService.getAreaList(lodgingVO);
	}
	
	
	@GetMapping("/infoReview")
    @ResponseBody
    public Map<String,Object> getLodgingDetailInfoReview(String contentid) {

        return lodgingService.getDetailInfoReviewList(contentid);
    }

}
