package com.trip.finalProject.specialties.mapper;

import java.util.List;

import com.trip.finalProject.specialties.service.SpecialtiesVO;

public interface SpecialtiesMapper {
	//특산물 등록
	public int insertSpecialites(SpecialtiesVO specialtiesVO);
	
	//특산물 정보
	public SpecialtiesVO sepcialtiesInfo(SpecialtiesVO specialtiesVO);
	
	//특산물 리스트
	public List<SpecialtiesVO> listSpecialties();
	
	public List<SpecialtiesVO> listArea();
}
