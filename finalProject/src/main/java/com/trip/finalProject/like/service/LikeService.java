package com.trip.finalProject.like.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface LikeService {
	//패키지 조회(카운트)
	public int mtCountInfo(String memberId);
	
	//여행기록 조회(카운트)
	public int trCountInfo(String memberId);

	//특산물 조회
	public List<LikeVO> mtAllLikeInfo(LikeVO likeVO, PagingVO pagingVO);
	
	//여행기록 조회
	public List<LikeVO> trAllLikeInfo(LikeVO likeVO, PagingVO pagingVO);


}
