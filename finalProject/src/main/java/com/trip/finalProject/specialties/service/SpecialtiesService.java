package com.trip.finalProject.specialties.service;

import java.util.List;

public interface SpecialtiesService {
	
	
	//특산물 정보
	public SpecialtiesVO getSpecialtiesInfo(SpecialtiesVO specialtiesVO);
	
	//특산물 리스트
	public List<SpecialtiesVO> getSpecialtiesList();
	
	//특산물 등록
	public void insertSepcialties(SpecialtiesVO specialtiesVO);
}