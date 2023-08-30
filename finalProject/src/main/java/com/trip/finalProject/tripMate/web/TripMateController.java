package com.trip.finalProject.tripMate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.attachedFile.service.AttachedFileService;
import com.trip.finalProject.attachedFile.service.AttachedFileVO;
import com.trip.finalProject.tripMate.service.TripMateService;
import com.trip.finalProject.tripMate.service.TripMateVO;

@Controller
public class TripMateController {
	@Autowired
	TripMateService tripMateService;
	
	@Autowired
	AttachedFileService attachedFileService;
	
	//여행 메이트 게시글 전체 조회
	@GetMapping("/tripMateList")
	public String tripMateList(Model model) {
		model.addAttribute("tripMateList", tripMateService.getTripMateAll());
		return "tripMate/tripMateList";
	}
	
	//여행 메이트 게시글 상세 조회
	@GetMapping("/tripMateInfo")
	public String tripMateInfo(TripMateVO tripMateVO, Model model) {
		//조회수 카운트
		tripMateService.updateMateRecruitHit(tripMateVO);
		
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		
		model.addAttribute("tripMateInfo", findVO);
		
		return "tripMate/tripMateInfo";
	}
	
	//여행 메이트 게시글 등록 - form
	@GetMapping("/tripMateRecruitForm")
	public String tripMateRecruitForm(Model model) {
		return "tripMate/tripMateRecruitForm";
	}
	
	//여행 메이트 게시글 등록 - process
	@PostMapping("/tripMateRecruitInsert")
	public ModelAndView tripMateRecruitInsert(TripMateVO tripMateVO) {
		tripMateService.insertTripMateRecruit(tripMateVO);
		ModelAndView mv = new ModelAndView("redirect:/tripMateList");
		return mv;
	}
	
	//첨부파일 상세정보(파일 여러개를 보여주기 위해서)
	@GetMapping("/getAttach")
	@ResponseBody
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo){
		System.out.println(vo.getPostId());
		return attachedFileService.getAttachList(vo);
	}
	
	//여행 메이트 게시글 삭제
	@GetMapping("/mateRecruitDelete")
	public String mateRecruitDelete(TripMateVO tripMateVO) {
		//여행 메이트 글 삭제 시 해당 게시글과 관련된 첨부파일 테이블 데이터 삭제
		tripMateService.deleteAttachedFile(tripMateVO);
		
		tripMateService.deleteTripMateRecruit(tripMateVO);
		return "redirect:/tripMateList";
	}
	
	
	//여행 메이트 게시글 수정 - form
	@GetMapping("/mateRecruitUpdateForm")
	public String mateRecruitUpdateForm(TripMateVO tripMateVO, Model model) {
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		model.addAttribute("tripMateVO", findVO);
		return "tripMate/tripMateUpdate";
	}
	
	
	//여행 메이트 게시글 수정 - process
	@PostMapping("/mateRecruitUpdate")
	public String mateRecruitUpdateProcess(TripMateVO tripMateVO){
		tripMateService.updateTripMateRecruit(tripMateVO);
		return "redirect:/tripMateList";
	}
	
	//여행 메이트 게시글 신고 - form
	@GetMapping("/mateRecruitReportForm")
	public String mateRecruitReportForm(TripMateVO tripMateVO, Model model) {
		TripMateVO findVO = tripMateService.getTripMateInfo(tripMateVO);
		model.addAttribute("tripMateVO", findVO);
		return "tripMate/mateRecruitReportForm";
	}
	
	//여행 메이트 게시글 신고 - process
	@PostMapping("/mateRecruitReport")
	public String mateRecruitReport(TripMateVO tripMateVO) {
		tripMateService.reportTripMate(tripMateVO);
		return "redirect:/tripMateList";
	}
	
	//여행 메이트 신청 - form
	@PostMapping("/tripMateApplyForm")
	public String tripMateApplyForm(TripMateVO trvo, Model model) {
		model.addAttribute("mateVO", trvo );
		return "tripMate/tripMateApplyForm";
	}
	
	//여행 메이트 신청 - process
	@Transactional
	@PostMapping("/tripMateApplyInsert")
	public String tripMateApplyInsert(TripMateVO tripMateVO, Model model) {
		//최대 인원, 신청 인원 조회
		//tripMateService.selectMateRecruitApplyNum(tripMateVO);
		//메이트 신청
		tripMateService.InsertTripMateApply(tripMateVO);
		
		//신청인원 업데이트
		tripMateService.updateMateRecruitApplyNum(tripMateVO);
		
		//게시글 작성자에게 알림
		tripMateService.sendAlert(tripMateVO);
		
		return "redirect:/tripMateList";			
	}
	
}
