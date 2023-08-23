package com.trip.finalProject.packaged.mapper;

import java.util.List;

import com.trip.finalProject.packaged.service.PackageVO;

public interface PackageMapper {
	//패키지 등록
	public int insertEditor(PackageVO packageVO);

	//패키지 정보
	public PackageVO packageInfo(PackageVO packageVO);
	
	//패키지 리스트
	public List<PackageVO> listPackage();
}
