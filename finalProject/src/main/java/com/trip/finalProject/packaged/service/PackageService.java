
package com.trip.finalProject.packaged.service;
import java.util.List;
import java.util.Map;

import com.trip.finalProject.location.service.LocationVO;

import com.trip.finalProject.common.PagingVO;

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
	
	//가이드 마이페이지 (재운) ===================================================================
	//전체 리스트 페이징
	public int guiListCount(PackageVO pacVO);
	//전체 리스트
	public List<PackageVO> guiListPackage(PackageVO pacVO, PagingVO pagingVO);
	//판매완료 리스트 페이징
	public int guiListComCount(PackageVO pacVO);
	//전체 리스트(판매완료)
	public List<PackageVO> guiListComPackage(PackageVO pacVO, PagingVO pagingVO);
	//패키지 데이터
	public PackageVO guidePacInfo(PackageVO pacVO);
	//결제 회원 정보
	public List<PackageVO> pacMember(PackageVO pacVO);

}
