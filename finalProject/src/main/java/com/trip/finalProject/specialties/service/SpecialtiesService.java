package com.trip.finalProject.specialties.service;

import java.util.List;

import com.trip.finalProject.location.service.LocationVO;

public interface SpecialtiesService {
	
	
	
	
	//특산물 리스트
	public List<SpecialtiesVO> getSpecialtiesList();
	
	//특산물 등록
	public void insertSepcialties(SpecialtiesVO specialtiesVO);
	
	//지역 코드 리스트
	public List<LocationVO> getLocationList();
	
	//특산물 옵션 리스트
	public List<SpecialtiesOptionVO> getOptionList(String postId);

	//특산물 정보
	public SpecialtiesVO getSpecialtiesInfo(String postId);
}
