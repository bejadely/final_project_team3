package com.trip.finalProject.packaged.service;

import java.util.List;

public interface PackageService {
	
	//패키지 등록
	public int insertEdirotInfo(PackageVO packageVO);
	
	//패키지 정보
	public PackageVO packageInfo(PackageVO packageVO);
	
	//패키지 리스트
	public List<PackageVO> getPackageList();

	String register(PackageVO vo);
}
