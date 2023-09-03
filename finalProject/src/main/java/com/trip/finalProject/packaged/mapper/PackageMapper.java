package com.trip.finalProject.packaged.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.packaged.service.PackageVO;

public interface PackageMapper {
	//패키지 등록
	public int insertEditor(PackageVO packageVO);

	//패키지 정보
	public PackageVO packageInfo(PackageVO packageVO);
	
	//패키지 리스트
	public List<PackageVO> listPackage();
	
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
}
