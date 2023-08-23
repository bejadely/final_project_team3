package com.trip.finalProject.lodging.mapper;

import java.util.List;

import com.trip.finalProject.lodging.service.LodgingVO;

public interface LodgingMapper {
	//숙소 등록
	public int insertLodging(LodgingVO lodginVO);
	
	//숙소 리스트
	public List<LodgingVO> listLodging();
}
