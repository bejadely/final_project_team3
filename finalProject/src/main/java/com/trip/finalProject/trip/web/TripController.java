package com.trip.finalProject.trip.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripService;
import com.trip.finalProject.trip.service.TripVO;

// 23.08.22 이승우 : 여행기록 전체조회
@Controller
public class TripController {
	@Autowired
	TripService tripService;

	@Value("${kakao.map.key}")
	String kakaoMap;

	// 여행기록 전체 조회
	@GetMapping("tripRecordList")
	public String tirpRecordList(Model model, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage,
			@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		int total = tripService.tripRecordCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripRecordList = tripService.getTripAll(pagingVO);

		model.addAttribute("tripRecordList", tripRecordList);
		model.addAttribute("paging", pagingVO);

		return "trip/tripRecordList";
	}

	// 여행기록 상세조회
	@GetMapping("tripRecordInfo")
	public String tripRecordInfo(TripVO tripVO, Model model) {
		TripVO findVO = tripService.getTripInfo(tripVO);
		model.addAttribute("tripInfo", findVO);
		return "trip/tripRecordInfo";
	}

	// 여행기록 등록 - form
	@PostMapping("tripRecordInsertForm")
	public String tripRecordInsertForm(TripVO tripVO, Model model) {
		model.addAttribute("tripVO", tripVO);
		return "trip/tripRecordInsertForm";
	}

	// 여행기록 등록 - 처리
	@PostMapping("tripRecordInsert")
	public String tripRecordInsertProcess(TripVO tripVO) {
		tripService.InsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}

	// 여행기록 임시저장 - 처리
	@PostMapping("tsTripRecordInsert")
	public String tsTripRecordInsertProcess(TripVO tripVO) {
		tripService.TsInsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}

	// 여행기록 지도 맵핑 - form
	@GetMapping("tripMappingInsertForm")
	public String tripMappingInsertForm(TripVO tripVO, Model model) {
		model.addAttribute("tripVO", tripVO);
		return "trip/tripMappingInsertForm";
	}

	// 여행기록 지도 맵핑 - 처리
	@PostMapping("tripMappingInsert")
	public String tripMappingInsertProcess(TripVO tripVO) {
		tripService.TsInsertTripInfo(tripVO);
		return "redirect:/tripInsertForm";
	}

	// 여행기록 메모 등록
	
	
	// 맵핑 배열 등록 테스트용
    @PostMapping("mappingInsert")
    @ResponseBody
    public String receiveMappingData(@RequestBody TripVO[] mappingData) {
        // Process the received data (mappingData) here
        // YourMappingObject is the class representing the structure of your data
        
        // Example: Print received data
        for (TripVO item : mappingData) {
            System.out.println("위도: " + item.getMapLat() + ", 경도: " + item.getMapLng());
        }
        return "trip/tripRecordList";
    }
}