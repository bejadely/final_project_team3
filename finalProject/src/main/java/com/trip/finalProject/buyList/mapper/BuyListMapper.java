package com.trip.finalProject.buyList.mapper;

import java.util.List;

import com.trip.finalProject.buyList.service.BuyListVO;
import com.trip.finalProject.common.PagingVO;

public interface BuyListMapper {
	
	//패키지 조회(카운트)
	public int pkCount(BuyListVO buyVO);
	
	//특산물 조회(카운트)
	public int spCount(BuyListVO buyVO);
	
	//패키지 조회
	public List<BuyListVO> pkAllLike(BuyListVO buyVO, PagingVO pagingVO);
	
	//특산물 조회
	public List<BuyListVO> spAllLike(BuyListVO buyVO, PagingVO pagingVO);
	
	//데이터 불러오기
	public BuyListVO selectPk(BuyListVO buyVO);
	
	//데이터 불러오기
	public BuyListVO selectSp(BuyListVO buyVO);

}
