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
	public int paCountInfo(String memberId) {
		return likeMapper.paCount(memberId);
	}

	@Override
	public int spCountInfo(String memberId) {
		return likeMapper.spCount(memberId);
	}

	@Override
	public int trCountInfo(String memberId) {
		return likeMapper.trCount(memberId);
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
