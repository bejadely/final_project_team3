package com.trip.finalProject.specialties.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.specialties.service.SpecialtiesOptionVO;
import com.trip.finalProject.specialties.service.SpecialtiesVO;

public interface SpecialtiesMapper {
	//특산물 등록
	public int insertSpecialites(SpecialtiesVO specialtiesVO);
	
	//특산물 정보
	public SpecialtiesVO sepcialtiesInfo(String postId);
	
	//특산물 리스트
	public List<SpecialtiesVO> listSpecialties();
	
	//지역 리스트
	public List<LocationVO> listArea();
	
	//특산물 옵션 등록
	public void insertSpecialtiesOption(SpecialtiesOptionVO specialtiesOptionVO);
	
	public List<SpecialtiesOptionVO> findByPostId(String postId);
	
	//0904창민
	//특산물 전체 등록 수 카운트
	public int countAllSpecial();
	
	//특산물 전체 조회
	public List<SpecialtiesVO> selectAllSpecial(PagingVO pagingVO);
	
	
}
