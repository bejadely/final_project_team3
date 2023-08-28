package com.trip.finalProject.like.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface LikeService {
	//패키지 조회(카운트)
	public int paCountInfo(String memberId);
	
	//특산물 조회(카운트)
	public int spCountInfo(String memberId);
	
	//여행기록 조회(카운트)
	public int trCountInfo(String memberId);
	
	//패키지 조회
	public List<LikeVO> paAllLikeInfo(PagingVO pagingVO);
	
	//특산물 조회
	public List<LikeVO> spAllLikeInfo(PagingVO pagingVO);
	
	//여행기록 조회
	public List<LikeVO> trAllLikeInfo(PagingVO pagingVO);


}
