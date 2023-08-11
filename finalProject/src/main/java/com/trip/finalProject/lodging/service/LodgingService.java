package com.trip.finalProject.lodging.service;

import java.util.List;

public interface LodgingService {

	//숙소 등록;
	public int insertLodgingInfo(LodgingVO lodingVO);

	//숙소 리스트
	public List<LodgingVO> getLodgingList();


	
}
