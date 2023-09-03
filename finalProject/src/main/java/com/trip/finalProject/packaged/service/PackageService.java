
package com.trip.finalProject.packaged.service;
import java.util.List;
import java.util.Map;

import com.trip.finalProject.location.service.LocationVO;

public interface PackageService {
	
	//패키지 정보
	public PackageVO packageInfo(PackageVO packageVO);
	
	//패키지 리스트
	public List<PackageVO> getPackageList();

	public void register(PackageVO vo);

	Map<String, Object> getDetailInfoReviewList(String postId);

	List<PackageReviewVO> getDetailReviewList(String postId, int page);

	Map<String, Object> insertReviewInfo(PackageReviewVO PackageReviewVO) throws Exception;

	Map<String, Object> deleteReviewInfo(String postId, String reviewId) throws Exception;
	
	//지역 코드 리스트
	public List<LocationVO> getLocationList();
}
