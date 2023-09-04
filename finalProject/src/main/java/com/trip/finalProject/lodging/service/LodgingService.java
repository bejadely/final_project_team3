package com.trip.finalProject.lodging.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.location.service.LocationVO;

public interface LodgingService {

	//숙소 등록;
	public int insertLodgingInfo(LodgingVO lodingVO);

	//숙소 리스트
	public List<LodgingVO> getLodgingList(PagingVO pagingVO);

	public List<LocationVO> getLocationList();

	public List<LodgingVO> getAreaList(LodgingVO lodgingVO);

	public Map<String, Object> getDetailInfoReviewList(String contentid);

	public int lodgingCount();

	public int lodgingCountTitle(String keyword);

	public List<LodgingVO> searchPackageByTitle(LodgingVO lodgingVO, PagingVO pagingVO);


	
}
