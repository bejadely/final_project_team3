package com.trip.finalProject.like.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.like.mapper.LikeMapper;
import com.trip.finalProject.like.service.LikeService;
import com.trip.finalProject.like.service.LikeVO;

@Service
public class LikeServiceImpl implements LikeService {
	@Autowired
	LikeMapper likeMapper;

	@Override
	public int mtCountInfo(String memberId) {
		return likeMapper.mtCount(memberId);
	}


	@Override
	public int trCountInfo(String memberId) {
		return likeMapper.trCount(memberId);
	}

	@Override
	public List<LikeVO> mtAllLikeInfo(LikeVO likeVO,PagingVO pagingVO) {
		return likeMapper.mtAllLike(likeVO, pagingVO);
	}

	@Override
	public List<LikeVO> trAllLikeInfo(LikeVO likeVO,PagingVO pagingVO) {
		return likeMapper.trAllLike(likeVO, pagingVO);
	}

}
