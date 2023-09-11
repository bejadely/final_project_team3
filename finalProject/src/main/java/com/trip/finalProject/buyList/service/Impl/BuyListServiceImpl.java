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
	public int pkCountInfo(BuyListVO buyVO) {
		return buyMapper.pkCount(buyVO);
	}

	@Override
	public int spCountInfo(BuyListVO buyVO) {
		return buyMapper.spCount(buyVO);
	}

	@Override
	public List<BuyListVO> pkAllLikeInfo(BuyListVO buyVO, PagingVO pagingVO) {
		return buyMapper.pkAllLike(buyVO, pagingVO);
	}

	@Override
	public List<BuyListVO> spAllLikeInfo(BuyListVO buyVO, PagingVO pagingVO) {
		return buyMapper.spAllLike(buyVO, pagingVO);
	}

	@Override
	public BuyListVO selectPkInfo(BuyListVO buyVO) {
		return buyMapper.selectPk(buyVO);
	}
	
	@Override
	public BuyListVO selectSpInfo(BuyListVO buyVO) {
		return buyMapper.selectSp(buyVO);
	}
}
