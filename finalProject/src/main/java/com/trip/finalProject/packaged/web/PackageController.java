package com.trip.finalProject.packaged.web;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trip.finalProject.attachedFile.service.AttachedFileService;
import com.trip.finalProject.attachedFile.service.AttachedFileVO;
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;

@Controller
public class PackageController {
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	AttachedFileService attachedFileService;
	
	@GetMapping("packageInfo")
	public String getpackageInfo(Model model, PackageVO packageVO) {
		PackageVO findVO = packageService.packageInfo(packageVO);

		model.addAttribute("info",findVO);
		return "package/packageInfo";
	}
	
	//ck-editor
	@GetMapping("packageInsertForm")
	public String package2() {
		return "package/packageInsertForm";
	}

	
	@PostMapping("register")
	public ModelAndView register(PackageVO vo) {	
		packageService.register(vo);
		ModelAndView mv = new ModelAndView("redirect:/packageList");
		return mv;
	}
	
	@GetMapping("packageList")
	public String getPackageList(Model model) {
		model.addAttribute("packageList",packageService.getPackageList());
		return "package/packageList";
	}
	
	@GetMapping("getAttachList")
	@ResponseBody
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo){
		System.out.println(vo.getPostId());
		return attachedFileService.getAttachList(vo);
	}
	
	
}
