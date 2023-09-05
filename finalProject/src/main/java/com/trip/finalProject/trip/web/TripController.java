package com.trip.finalProject.trip.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
	@GetMapping("/tripRecordList")
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
	@GetMapping("/common/myPageTrip")
	public String maPageTrip(Model model
			  ,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			  ,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		int total = tripService.tripPerCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPer(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/trip/myPageTrip";
	}
	
	//여행기록 개인 조회/임시저장 - myPgae(재운)
	@GetMapping("/common/myPageNotTrip")
	public String maPageNotTrip(Model model
			,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		int total = tripService.tripPerNotCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPerNot(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/trip/myPageNotTrip";
	}
	
	//여행기록 개인 조회/완료된 여행 - myPgae(재운)
	@GetMapping("/common/myPageComTrip")
	public String maPageComTrip(Model model
			,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		int total = tripService.tripPerComCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<TripVO> tripList = tripService.getTripPerCom(pagingVO);
		
		model.addAttribute("tripList", tripList);
		model.addAttribute("paging", pagingVO);
		
		return "myPage/trip/myPageComTrip";
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
	
	// 여행기록 상세조회
	@Transactional
	@GetMapping("/tripRecordInfo")
	public String tripRecordInfo(TripVO tripVO, Model model) {
		TripVO findVO = tripService.getTripInfo(tripVO);
		
		//여행 기록 데이터
		model.addAttribute("tripInfo", findVO);

		//여행 메모 데이터
		List<TripVO> memoInfo = tripService.getMemoData(tripVO);
		//System.out.println(memoInfo);
		model.addAttribute("memoInfo", memoInfo);
		
		return "trip/tripRecordInfo";
	}
	
	//ajax 해당 게시글의 여행경로 데이터 불러오기
	@PostMapping("/mapInfoList")
	@ResponseBody
	public Map<String, Object> mapInfoList(TripVO tripVO) {
		tripVO.getPostId();
		tripVO.getTripDate();
		//tripService.getMapData(tripVO);
		
		List<TripVO> mapDataList = tripService.getMapData(tripVO);
		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("tripMap", mapDataList);
		return map;
	}
	
	// 여행기록 등록 - 여행기록 작성하기 버튼 클릭 시 실행되는 메소드
	// form 호출 시 해당 내용을 임시저장을 시킴(post_id를 가져오기 위함) 
	@PostMapping("/common/tripRecordInsertForm")
	public String tripRecordInsertForm(TripVO tripVO, Model model) {
		
		// 여행기록 테이블에 데이터 삽입
		TripVO result = tripService.TsInsertTripInfo(tripVO);
		
		//post
		model.addAttribute("tripVO", result);
		return "trip/tripRecordInsertForm";
	}

	// 여행기록 등록 - 임시저장 상태에서 저장상태로 상태 업데이트
	@PostMapping("/common/tripRecordInsertUp")
	public String tripRecordInsertProcess(TripVO tripVO) {
		//여행기록 저장상태 변경
		tripService.InsertTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}

	// 여행기록 임시저장 - 임시 저장인 상태로 다시 업데이트
	@PostMapping("/common/tsTripRecordInsertUp")
	public String tsTripRecordInsertProcess(TripVO tripVO) {
		tripService.TsTripInfo(tripVO);
		return "redirect:/tripRecordList";
	}
	
	//여행기록 수정 - form
	@GetMapping("/common/tripRecordModify")
	public String tripRecordModifyForm(TripVO tripVO, Model model) {
		TripVO vo = tripService.getTripInfo(tripVO);
		return "trip/tripRecordModifyForm";
	}
	
	//여행기록 수정 - process
	
	// 여행기록 게시글 삭제
	@Transactional
	@GetMapping("/common/tripRecordDelete")
	public String tripRecordDelete(TripVO tripVO) {
		//여행기록 게시글 삭제
		tripService.deleteTripInfo(tripVO);
		
		//해당 게시글의 여행경로 삭제
		tripService.deleteMapData(tripVO);
		
		//해당 게시글의 여행메모 삭제
		tripService.deleteMemoData(tripVO);
		
		return "redirect:/tripRecordList";
	}

	// 여행기록 메모 등록 
	@PostMapping("/common/tripMemoInsert")
	@ResponseBody
	public String tripMemoInsert(@RequestBody TripVO[] memoDataArry) {
		//여행메모 등록
		for (TripVO item : memoDataArry) {
			tripService.InsertTripMemo(item);
		}
		return null;
	}
	
	// 여행 경로 저장 (ajax)
    @PostMapping("/common/tripMappingInsert")
    @ResponseBody
    public String receiveMappingData(@RequestBody TripVO[] mapDataArry) {
    	for (TripVO item : mapDataArry) {
            tripService.InsertTripMapping(item);
        	//System.out.println(item);
        }
        return null;
    }
    
    // 여행 경로 삭제 (ajax)
    @PostMapping("/common/mapDeleteList")
    @ResponseBody
    public TripVO tripMapDel(TripVO tripVO) {
    	
    	tripService.deleteMapData(tripVO);
    	return null;
    }
    
    
    
}
