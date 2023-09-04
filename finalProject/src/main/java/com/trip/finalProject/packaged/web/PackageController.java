package com.trip.finalProject.packaged.web;


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
	@GetMapping("/packageInsertForm")
	public String package2(Model model) {
		model.addAttribute("area",packageService.getLocationList());
		return "package/packageInsertForm";
	}

	
	@PostMapping("/register")
	public ModelAndView register(PackageVO vo) {	
		packageService.register(vo);
		ModelAndView mv = new ModelAndView("redirect:/packageList");
		return mv;
	}
	
	@GetMapping("/packageList")
	public String getPackageList(Model model) {
		model.addAttribute("packageList",packageService.getPackageList());
		return "package/packageList";
	}
	
	@GetMapping("/getAttachList")
	@ResponseBody
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo){
		System.out.println(vo.getPostId());
		return attachedFileService.getAttachList(vo);
	}
	

    //모달창 내 정보+리뷰 가져오기
    @GetMapping("/packageInfoReview")
    @ResponseBody
    public Map<String,Object> getDetailInfoReview(String postId) {

        return packageService.getDetailInfoReviewList(postId);
    }

    //모달창 내 리뷰 가져오기
    @GetMapping("/packageReview")
    @ResponseBody
    public List<PackageReviewVO> getDetailReview(String postId, int page) {

        return packageService.getDetailReviewList(postId, page);
    }

    //모달창 내 리뷰 등록
    @PostMapping("/packageReview")
    @ResponseBody
    public Map<String,Object> reviewInsert(PackageReviewVO packageReviewVO) throws Exception {
        if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().replaceAll(" ", "").equals("")) {
        	packageReviewVO.setWriterId(session.getAttribute("sessionId").toString());
        } else {
            throw new Exception("no login");
        }

        return packageService.insertReviewInfo(packageReviewVO);
    }

    //모달창 내 리뷰 삭제
    @DeleteMapping("/packageReview")
    @ResponseBody
    public Map<String,Object> reviewDelete(String postId, String writerId, String reviewId) throws Exception {
        String sessionId = "";
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
		pacVO.setWriterId("1");
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
		pacVO.setWriterId("1");
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
		pacVO.setWriterId("1");
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
	
}
