package com.trip.finalProject.like.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.like.service.LikeVO;

public interface LikeMapper {
	//패키지 조회(카운트)
	public int paCount(String memberId);
	
	//특산물 조회(카운트)
	public int spCount(String memberId);
	
	//여행기록 조회(카운트)
	public int trCount(String memberId);
	
	//패키지 조회
	public List<LikeVO> paAllLike(PagingVO pagingVO);
	
	//특산물 조회
	public List<LikeVO> spAllLike(PagingVO pagingVO);
	
	//여행기록 조회
	public List<LikeVO> trAllLike(PagingVO pagingVO);

}
