package com.trip.finalProject.packaged.mapper;

import java.util.List;

import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.packaged.service.PackageReviewVO;
import com.trip.finalProject.packaged.service.PackageVO;

public interface PackageMapper {
	//패키지 등록
	public int insertEditor(PackageVO packageVO);

	//패키지 정보
	public PackageVO packageInfo(PackageVO packageVO);
	
	//패키지 리스트
	public List<PackageVO> listPackage();
	
	//지역 리스트
	public List<LocationVO> listArea();
	
	 //리뷰 조회
    List<PackageReviewVO> selectReview(String postId, int page);

    //리뷰 조회 시 총 리뷰 갯수 구하기
    String selectReviewTotalCount(String postId);

    //리뷰 등록
    int insertReviewInfo(PackageReviewVO packageReviewVO);

    //리뷰 삭제
    int deleteReview(String reviewId);
}
