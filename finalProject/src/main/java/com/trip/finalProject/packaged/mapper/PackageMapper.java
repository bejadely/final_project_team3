package com.trip.finalProject.packaged.mapper;

import java.util.List;


import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.packaged.service.PackageReviewVO;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.packaged.service.PackageVO;

public interface PackageMapper {
	//패키지 등록
	public int insertEditor(PackageVO packageVO);

	//패키지 정보
	public PackageVO packageInfo(PackageVO packageVO);
	
	//패키지 리스트
	public List<PackageVO> listPackage(PagingVO pagingvo);
	
	//지역 리스트
	public List<LocationVO> listArea();
	
	//패키지 수 카운트
	public int getPackageCount();
	
	//패키지 명 검색 결과 카운트
	public int packageCountTitle(String keyword);


	//패키지 명 검색
	public List<PackageVO> searchPackageByTitle(PackageVO packageVO, PagingVO pagingVO);

	
	 //리뷰 조회
    List<PackageReviewVO> selectReview(String postId, int page);

    //리뷰 조회 시 총 리뷰 갯수 구하기
    String selectReviewTotalCount(String postId);

    //리뷰 등록
    int insertReviewInfo(PackageReviewVO packageReviewVO);

    //리뷰 삭제
    int deleteReview(String reviewId);

	//가이드 마이페이지 (재운) ===================================================================
	//전체 리스트 페이징
	public int guiListCount(PackageVO pacVO);
	//전체 리스트(모집)
	public List<PackageVO> guiListPackage(PackageVO pacVO, PagingVO pagingVO);
	//판매완료 리스트 페이징
	public int guiListComCount(PackageVO pacVO);
	//전체 리스트(판매완료)
	public List<PackageVO> guiListComPackage(PackageVO pacVO, PagingVO pagingVO);
	//패키지 데이터
	public PackageVO guidePacInfo(PackageVO pacVO);
	//결제 회원 정보
	public List<PackageVO> pacMember(PackageVO pacVO);
	//패키지 삭제
	public int deletePackage(String postId);

	

	

}
