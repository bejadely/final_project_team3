package com.trip.finalProject.specialties.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.common.mapper.CommonMapper;
import com.trip.finalProject.specialties.service.SpecialtiesOptionVO;
import com.trip.finalProject.specialties.service.SpecialtiesService;
import com.trip.finalProject.specialties.service.SpecialtiesVO;

@Controller
public class SpecialtiesController {
	@Autowired
	SpecialtiesService specialtiesService;
	
	@Autowired
	CommonMapper commonMapper;
	
	@GetMapping("/specialtiesInsertForm")
	public String package2(Model model) {
		model.addAttribute("S",commonMapper.selectCode("S"));
		model.addAttribute("area",specialtiesService.getLocationList());
		return "specialties/specialtiesInsertForm";
	}
	
	@PostMapping("/specialtiesInsert")
	public String specialtiesInsert(SpecialtiesVO specialtiesVO) {
		specialtiesService.insertSepcialties(specialtiesVO);
		
		return "redirect:/specialtiesList";
	}
	
	@GetMapping("/specialtiesList")
	public String specialtiesList(Model model) {
		model.addAttribute("specialtiesList",specialtiesService.getSpecialtiesList());
		return "specialties/specialtiesList";
	}
	
	@GetMapping("/specialtiesInfo")
	public String getSpecialtiesInfo(Model model, String postId) {
		SpecialtiesVO vo = specialtiesService.getSpecialtiesInfo(postId);
		List<SpecialtiesOptionVO> optionVO = specialtiesService.getOptionList(postId);
		model.addAttribute("info",vo);
		model.addAttribute("option", optionVO);
		return "specialties/specialtiesInfo";
	}
	
	//0904 창민 추가
	//특산물 전체 조회
	@GetMapping("/admin/seeAllSpecial")
	public String seeAllSpecial(Model model
							  , @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
							  , @RequestParam( name = "keyword", defaultValue = "" ) String keyword
				              , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
				              , @RequestParam( name = "cntPerPage", defaultValue = "10")Integer cntPerPage) {
		
		
		// 새로운 신고접수내역 전체 조회
		Map<String, Object> map = specialtiesService.selectAllSpecial(nowPage, cntPerPage);
		
		// 모든 회원 정보 모델에 담기
		model.addAttribute("list", map.get("list"));
		model.addAttribute("paging", map.get("PagingVO"));
		
		// 검색어가 없을 경우를 대비한 구문
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchBy", searchBy);
		
		return "admin/manageProduct/seeAllSpecial";
	}
}
