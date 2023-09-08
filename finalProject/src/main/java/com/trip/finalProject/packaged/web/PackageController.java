package com.trip.finalProject.packaged.web;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.attachedFile.service.AttachedFileService;
import com.trip.finalProject.attachedFile.service.AttachedFileVO;
import com.trip.finalProject.packaged.service.PackageReviewVO;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;

@Controller
public class PackageController {
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	AttachedFileService attachedFileService;
	
	@Autowired
    HttpSession session;
	
	@GetMapping("/packageInfo")
	public String getpackageInfo(Model model, PackageVO packageVO) {
		PackageVO findVO = packageService.packageInfo(packageVO);

		model.addAttribute("info",findVO);
		return "package/packageInfo";
	}
	
	//ck-editor
	@GetMapping("/guide/packageInsertForm")
	public String package2(Model model) {
		model.addAttribute("area",packageService.getLocationList());
		return "package/packageInsertForm";
	}

	
	@PostMapping("/guide/register")
	public ModelAndView register(PackageVO vo) {	
		packageService.register(vo);
		ModelAndView mv = new ModelAndView("redirect:/packageList");
		return mv;
	}
	
	@GetMapping("/packageList")
	public String getPackageList(Model model
								, @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
								, @RequestParam( name = "keyword", defaultValue = "" ) String keyword
					            , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
					            , @RequestParam( name = "cntPerPage", defaultValue = "5")Integer cntPerPage) {
		//패키지 수 카운트
		int total = packageService.packageCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		List<PackageVO> packageList = packageService.getPackageList(pagingVO);
		//패키지 정보 모델에 담음
		model.addAttribute("packageList",packageList);
		model.addAttribute("paging",pagingVO);
		
		model.addAttribute("area",packageService.getLocationList());
		
		//검색어가 없을 경우
		model.addAttribute("keyword",keyword);
		model.addAttribute("searchBy",searchBy);
		return "package/packageList";
	}
	
	//특정 조건으로 패키지 검색
	@GetMapping("/searchPackage")
	public String searchPackage(@RequestParam( name = "searchBy" ) String searchBy
							  , @RequestParam( name = "keyword" ) String keyword
							  , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
							  , @RequestParam( name = "cntPerPage", defaultValue = "5") Integer cntPerPage
							  , Model model
							  ,PackageVO packageVO) {
		//전체 조회될 패키지 수
		int total = packageService.packageCountTitle(keyword);
		
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		model.addAttribute("area",packageService.getLocationList());
		//제목으로 검색기능 수행
		packageVO.setName(keyword);
		List<PackageVO> list = packageService.searchPackageByTitle(packageVO, pagingVO);
		model.addAttribute("packageList",list);
		model.addAttribute("paging",pagingVO);
		// 검색결과 기억을 위해 keyword와 searchBy 담기
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchBy", searchBy);
		
		return "package/packageList";
	}
	
	
	@GetMapping("/getAttachList")
	@ResponseBody
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo){
		System.out.println(vo.getPostId());
		return attachedFileService.getAttachList(vo);
	}
	
	@GetMapping("/guide/packageUpdateForm")
	public String packageUpdateForm(Model model, PackageVO packageVO) {
		PackageVO vo = packageService.packageInfo(packageVO);
		model.addAttribute("info",vo);
		model.addAttribute("area",packageService.getLocationList());
		return "package/packageUpdateForm";
	}
	@PostMapping("/guide/packageUpdate")
	public String packageUpdate(PackageVO packageVO) {
		packageService.packageUpdate(packageVO);
		return "redirect:/packageList";
	}
	

    //  내 정보+리뷰 가져오기
    @GetMapping("/packageInfoReview")
    @ResponseBody
    public Map<String,Object> getDetailInfoReview(String postId) {

        return packageService.getDetailInfoReviewList(postId);
    }

    //  내 리뷰 가져오기
    @GetMapping("/packageReview")
    @ResponseBody
    public List<PackageReviewVO> getDetailReview(String postId, int page) {

        return packageService.getDetailReviewList(postId, page);
    }

    //  내 리뷰 등록
    @PostMapping("/common/packageReview")
    @ResponseBody
    public Map<String,Object> reviewInsert(PackageReviewVO packageReviewVO) throws Exception {
        if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().replaceAll(" ", "").equals("")) {
        	packageReviewVO.setWriterId(session.getAttribute("sessionId").toString());
        	System.out.println(packageReviewVO.getWriterId());
        } else {
            throw new Exception("no login");
        }

        return packageService.insertReviewInfo(packageReviewVO);
    }

    //  내 리뷰 삭제
    @DeleteMapping("/common/packageReview")
    @ResponseBody
    public Map<String,Object> reviewDelete(String postId, String writerId, String reviewId) throws Exception {
        String sessionId = "";
        System.out.println(writerId);
        if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().replaceAll(" ", "").equals("")) {
            sessionId =  session.getAttribute("sessionId").toString();
        } else {
            throw new Exception("no login");
        }

        if(!sessionId.equals(writerId)){
            throw new Exception("not same");
        }

        return packageService.deleteReviewInfo(postId, reviewId);
    }
	
	//가이드 마이페이지 (재운) ===================================================================
	//전체 리스트(모집 중)
	@GetMapping("/guide/packageSale")
	public String guiPacSale(Model model
							,PackageVO pacVO
							,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
							,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		pacVO.setWriterId(session.getAttribute("sessionId").toString());
		pacVO.setCompletion("D1");
		
		int total = packageService.guiListCount(pacVO);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<PackageVO> pacList = packageService.guiListPackage(pacVO, pagingVO);

		model.addAttribute("list", pacList);
		model.addAttribute("paging", pagingVO);
		
		return "guide/package/packageSale";
	}
	
	//전체 리스트 모집 완료
	@GetMapping("/guide/packageSoldOut")
	public String guiPacSoldOut(Model model
			,PackageVO pacVO
			,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		pacVO.setWriterId(session.getAttribute("sessionId").toString());
		pacVO.setCompletion("D2");
		
		int total = packageService.guiListCount(pacVO);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<PackageVO> pacList = packageService.guiListPackage(pacVO, pagingVO);
		
		model.addAttribute("list", pacList);
		model.addAttribute("paging", pagingVO);
		
		return "guide/package/packageSoldOut";
	}
	//완료페이지
	@GetMapping("/guide/packageCom")
	public String guiPacCom(Model model
			,PackageVO pacVO
			,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			,@RequestParam(value = "cntPerPage", defaultValue = "10") Integer cntPerPage) {
		pacVO.setWriterId(session.getAttribute("sessionId").toString());
		int total = packageService.guiListComCount(pacVO);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<PackageVO> pacList = packageService.guiListComPackage(pacVO, pagingVO);
		
		model.addAttribute("list", pacList);
		model.addAttribute("paging", pagingVO);
		
		return "guide/package/packageCom";
	}
	
	@GetMapping("/guide/packageDetail")
	public String guidePacDetail(Model model, PackageVO pacVO) {
		PackageVO findVO = packageService.guidePacInfo(pacVO);
		List<PackageVO> memberList = packageService.pacMember(pacVO);
		model.addAttribute("member", memberList);
		model.addAttribute("info", findVO);
		return "guide/package/packageDetail";
	}
	
	@GetMapping("/guide/deleteSalePackage")
	public String deleteSalePackage(String postId) {
		packageService.deletePackage(postId);
		return "redirect:/guide/packageSale";
	}
	
	@GetMapping("/guide/deleteSoldOutPackage")
	public String deleteSoldOutPackage(String postId) {
		packageService.deletePackage(postId);
		return "redirect:/guide/packageSoldOut";
	}
	
	@GetMapping("/guide/deleteComPackage")
	public String deleteComPackage(String postId) {
		packageService.deletePackage(postId);
		return "redirect:/guide/packageCom";
	}
	
}
