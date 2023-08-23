package com.trip.finalProject.packaged.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;

@Controller
public class PackageController {
	
	@Autowired
	PackageService packageService;
	
	@GetMapping("packageInfo")
	public String getpackageInfo(Model model, PackageVO packageVO) {
		PackageVO findVO = packageService.packageInfo(packageVO);

		model.addAttribute("info",findVO);
		return "package/packageInfo";
	}
	
	@GetMapping("packageList")
	public String getPackageList(Model model) {
		model.addAttribute("packageList",packageService.getPackageList());
		return "package/packageList";
	}
}
