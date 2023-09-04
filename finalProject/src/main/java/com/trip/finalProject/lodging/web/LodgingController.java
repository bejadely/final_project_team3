package com.trip.finalProject.lodging.web;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.lodging.service.LodgingService;
import com.trip.finalProject.lodging.service.LodgingVO;


@Controller
public class LodgingController {
	@Autowired
	LodgingService lodgingService;
	
	@GetMapping("lodging")
	public String lodgingList(Model model) {
		model.addAttribute("lodgingList",lodgingService.getLodgingList());
		model.addAttribute("area",lodgingService.getLocationList());
		return"lodging/lodging";
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
