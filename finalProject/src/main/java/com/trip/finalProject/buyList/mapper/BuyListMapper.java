package com.trip.finalProject.buyList.mapper;

import java.util.List;

import com.trip.finalProject.buyList.service.BuyListVO;
import com.trip.finalProject.common.PagingVO;

public interface BuyListMapper {
	
	//패키지 조회(카운트)
	public int pkCount(String memberId);
	
	//특산물 조회(카운트)
	public int spCount(String memberId);
	
	//패키지 조회
	public List<BuyListVO> pkAllLike(PagingVO pagingVO);
	
	//특산물 조회
	public List<BuyListVO> spAllLike(PagingVO pagingVO);

}
