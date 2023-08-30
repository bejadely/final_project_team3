package com.trip.finalProject.buyList.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.buyList.mapper.BuyListMapper;
import com.trip.finalProject.buyList.service.BuyListService;
import com.trip.finalProject.buyList.service.BuyListVO;
import com.trip.finalProject.common.PagingVO;

@Service
public class BuyListServiceImpl implements BuyListService {
	

	@Autowired
	BuyListMapper buyMapper;	

	@Override
	public int pkCountInfo(String memberId) {
		return buyMapper.pkCount(memberId);
	}

	@Override
	public int spCountInfo(String memberId) {
		return buyMapper.spCount(memberId);
	}

	@Override
	public List<BuyListVO> pkAllLikeInfo(PagingVO pagingVO) {
		return buyMapper.pkAllLike(pagingVO);
	}

	@Override
	public List<BuyListVO> spAllLikeInfo(PagingVO pagingVO) {
		return buyMapper.spAllLike(pagingVO);
	}

	@Override
	public BuyListVO selectPkInfo(BuyListVO buyVO) {
		return buyMapper.selectPk(buyVO);
	}
	
	@Override
	public BuyListVO selectSpInfo(BuyListVO buyVO) {
		return buyMapper.selectPk(buyVO);
	}
}
