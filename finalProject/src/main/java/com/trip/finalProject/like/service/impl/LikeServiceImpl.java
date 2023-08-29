package com.trip.finalProject.like.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.like.mapper.LikeMapper;
import com.trip.finalProject.like.service.LikeService;
import com.trip.finalProject.like.service.LikeVO;

public class LikeServiceImpl implements LikeService {
	@Autowired
	LikeMapper likeMapper;

	@Override
	public int paCountInfo(String postId) {
		return likeMapper.paCount(postId);
	}

	@Override
	public int spCountInfo(String postId) {
		return likeMapper.spCount(postId);
	}

	@Override
	public int trCountInfo(String postId) {
		return likeMapper.trCount(postId);
	}

	@Override
	public List<LikeVO> paAllLikeInfo(PagingVO pagingVO) {
		return likeMapper.paAllLike(pagingVO);
	}

	@Override
	public List<LikeVO> spAllLikeInfo(PagingVO pagingVO) {
		return likeMapper.spAllLike(pagingVO);
	}

	@Override
	public List<LikeVO> trAllLikeInfo(PagingVO pagingVO) {
		return likeMapper.trAllLike(pagingVO);
	}

}
