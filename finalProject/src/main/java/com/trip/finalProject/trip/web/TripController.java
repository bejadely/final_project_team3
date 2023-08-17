package com.trip.finalProject.trip.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.trip.service.TripVO;

@Controller
public class TripController {
	@Autowired
	TripService tripService;
	@Value("${kakao.map.key}")
	String kakaoMap;
	
	//여행기록 전체 조회
	@GetMapping("tripRecordList")
	public String tirpRecordList(Model model
			  ,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			  ,@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		int total = tripService.tripRecordCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripRecordList = tripService.getTripAll(pagingVO);
		
		model.addAttribute("tripRecordList", tripRecordList);
		model.addAttribute("paging", pagingVO);
		
		return "trip/tripRecordList";
	}
	
	//여행기록 상세조회
	@GetMapping("tripRecordInfo")
	public String tripRecordInfo(TripVO tripVO, Model model) {
		TripVO findVO = tripService.getTripInfo(tripVO);
		model.addAttribute("tripInfo", findVO);
		return "trip/tripRecordInfo";
	}
	
	//여행기록 등록 - form
	@PostMapping("tripRecordInsertForm")
	public String tripRecordInsertForm(TripVO tripVO, Model model) {
		model.addAttribute("tripVO", tripVO);
		return "trip/tripRecordInsertForm";
	}
	
	//여행기록 등록 - 처리
	@PostMapping("tripRecordInsert")
	public String tripRecordInsertProcess(TripVO tripVO) {
		tripService.InsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}
	
	//여행기록 임시저장 - 처리
	@PostMapping("tsTripRecordInsert")
	public String tsTripRecordInsertProcess(TripVO tripVO) {
		tripService.TsInsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}
	
	
	//여행기록 지도 등록
	
	//여행기록 메모 등록
}
