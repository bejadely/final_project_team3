package com.trip.finalProject.lodging.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.lodging.service.LodgingVO;

public interface LodgingMapper {
	//숙소 등록
	public int insertLodging(LodgingVO lodginVO);
	
	//숙소 리스트
	public List<LodgingVO> listLodging(PagingVO pagingVO);

	public List<LocationVO> listArea();

	public List<LodgingVO> listAreaLodging(LodgingVO lodgingVO);

	public int lodgingCount();

	public int lodgingCountTitle(String keyword);

	public List<LodgingVO> searchPackageByTitle(LodgingVO lodgingVO, PagingVO pagingVO);
}
