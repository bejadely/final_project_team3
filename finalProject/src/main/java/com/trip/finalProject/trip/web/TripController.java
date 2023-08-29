package com.trip.finalProject.trip.web;

import java.util.List;
import java.util.Map;

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
	
	//여행기록 개인 조회/계획중 여행 - myPgae(재운)
	@GetMapping("myPageTrip")
	public String maPageTrip(Model model
			  ,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			  ,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		int total = tripService.tripPerCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPer(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/myPageTrip";
	}
	
	//여행기록 개인 조회/임시저장 - myPgae(재운)
	@GetMapping("myPageNotTrip")
	public String maPageNotTrip(Model model
			,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		int total = tripService.tripPerNotCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPerNot(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/myPageNotTrip";
	}
	
	//여행기록 개인 조회/완료된 여행 - myPgae(재운)
	@GetMapping("myPageComTrip")
	public String maPageComTrip(Model model
			,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		int total = tripService.tripPerComCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPerCom(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/myPageComTrip";
	}
	
	//여행기록 공개 설정 업데이트
	@PostMapping("/discloseUpdate")
	@ResponseBody
	public Map<String, Object> disUpdate(TripVO tripVO){
	    tripVO.getPostId();
	    tripVO.getTripDisclose();
	    	    
	    Map<String, Object> map = tripService.getUpdateDis(tripVO);
	    System.out.println("testMap : " + map);
	    
	    return map;
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
		
		//여행 기록 데이터
		model.addAttribute("tripInfo", findVO);
		
		//여행 경로 데이터
		List<TripVO> mapInfo = tripService.getMapData(tripVO);
		model.addAttribute("mapInfo", mapInfo);

		//여행 메모 데이터
		List<TripVO> memoInfo = tripService.getMemoData(tripVO);
		model.addAttribute("memoInfo", memoInfo);
		
		return "trip/tripRecordInfo";
	}

	// 여행기록 등록 - 여행기록 작성하기 버튼 클릭 시 실행되는 메소드
	// form 호출 시 해당 내용을 임시저장을 시킴(post_id를 가져오기 위함) 
	@PostMapping("tripRecordInsertForm")
	public String tripRecordInsertForm(TripVO tripVO, Model model) {
		
		// 여행기록 테이블에 데이터 삽입
		TripVO result = tripService.TsInsertTripInfo(tripVO);
		
		//post
		model.addAttribute("tripVO", result);
		return "trip/tripRecordInsertForm";
	}

	// 여행기록 등록 - 임시저장 상태에서 저장상태로 상태 업데이트
	@PostMapping("tripRecordInsertUp")
	public String tripRecordInsertProcess(TripVO tripVO) {
		tripService.InsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}

	// 여행기록 임시저장 - 임시 저장인 상태로 다시 업데이트
	@PostMapping("tsTripRecordInsertUp")
	public String tsTripRecordInsertProcess(TripVO tripVO) {
		tripService.TsTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}

	// 여행기록 지도 맵핑 - form
	@GetMapping("tripMappingInsertForm")
	public String tripMappingInsertForm(TripVO tripVO, Model model) {
		model.addAttribute("tripVO", tripVO);
		return "trip/tripMappingInsertForm";
	}
	
//	//여행경로 저장
//	@PostMapping("tripMappingInsert")
//	public String tripMappingInsert(TripVO tripVO) {
//		tripService.InsertTripMapping(tripVO);
//		
//		return "redirect:/tripRecordList";
//	}
//	// 여행기록 지도 맵핑 - 처리
//	@PostMapping("tripMappingInsert")
//	public String tripMappingInsertProcess(TripVO tripVO) {
//		tripService.TsInsertTripInfo(tripVO);
//		return "redirect:/tripInsertForm";
//	}

	// 여행기록 메모 등록 
	@PostMapping("tripMemoInsert")
	public String tripMemoInsert(TripVO tripVO, Model model) {
		
		//TripVO result = tripService.TsInsertTripInfo(tripVO);
		
		//model.addAttribute("tripVO", result);
		
		return null;
		//return "trip/tripRecordInsertForm";
	}
	
	
	// 여행 경로 저장 (ajax)
    @PostMapping("tripMappingInsert")
    @ResponseBody
    public String receiveMappingData(@RequestBody TripVO[] mapDataArry) {
    	for (TripVO item : mapDataArry) {
            tripService.InsertTripMapping(item);
        	//System.out.println(item);
        }
        return null;
        //return "redirect:/tripInsertForm";
    }
}
