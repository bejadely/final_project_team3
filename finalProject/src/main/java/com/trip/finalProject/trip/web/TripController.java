package com.trip.finalProject.trip.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.trip.service.TripVO;

@Controller
public class TripController {
	@Autowired
	TripService tripService;
	
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
	
	//여행기록 등록 - form
	@GetMapping("tripRecordInsert")
	public String tripRecordInsertForm(Model model) {
		model.addAttribute("tripVO", new TripVO());
		return "trip/tripRecordInsert";
	}
	
	//여행기록 등록 - 처리
	@PostMapping("tripRecordInsert")
	public String tripRecordInsertProcess(TripVO tripVO) {
		tripService.InsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}
	
	//여행기록 지도 등록
	
	//여행기록 메모 등록
}
