package com.trip.finalProject.buyList.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface BuyListService {
	//패키지 조회(카운트)
	public int pkCountInfo(String memberId);
	
	//특산물 조회(카운트)
	public int spCountInfo(String memberId);

	//패키지 조회
	public List<BuyListVO> pkAllLikeInfo(PagingVO pagingVO);
	
	//특산물 조회
	public List<BuyListVO> spAllLikeInfo(PagingVO pagingVO);
	
	//패키지 상품 상세조회
	public BuyListVO selectPkInfo(BuyListVO buyVO);
	
	//특산물 상품 상세조회
	public BuyListVO selectSpInfo(BuyListVO buyVO);

}
