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
	
	//여행기록 개인 조회
	@GetMapping("myPageTrip")
	public String maPageTrip(Model model
			  ,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			  ,@RequestParam(value = "cntPerPage", defaultValue = "12") Integer cntPerPage) {
		int total = tripService.tripRecordCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPer(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/myPageTrip";
	}
	
	//여행기록 등록 - form
	@GetMapping("tripRecordInsert")
	public String tripRecordInsertForm(Model model) {
		model.addAttribute("tripVO", new TripVO());
		return "trip/tripRecordInsert";
	}
	// 여행기록 상세조회
	@GetMapping("tripRecordInfo")
	public String tripRecordInfo(TripVO tripVO, Model model) {
		TripVO findVO = tripService.getTripInfo(tripVO);
		model.addAttribute("tripInfo", findVO);
		return "trip/tripRecordInfo";
	}

	// 여행기록 등록
	// form 호출 시 해당 내용을 임시저장을 시킴(post_id를 가져오기 위함) 
	@PostMapping("tripRecordInsertForm")
	public String tripRecordInsertForm(TripVO tripVO, Model model) {
		
		// 여행기록 테이블에 데이터 삽입
		TripVO result = tripService.TsInsertTripInfo(tripVO);
		
		
		model.addAttribute("tripVO", result);
		return "trip/tripRecordInsertForm";
	}

	// 여행기록 등록 - 임시저장 상태에서 저장상태로 상태 업데이트
	@PostMapping("tripRecordInsertUp")
	public String tripRecordInsertProcess(TripVO tripVO, Model model) {
		tripService.InsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}

	// 여행기록 임시저장 - 임시 저장인 상태로 다시 업데이트
	@PostMapping("tsTripRecordInsertUp")
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
        for (TripVO item : mappingData) {
            tripService.InsertTripMapping(item);
        }
        return "redirect:/tripInsertForm";
    }
}
