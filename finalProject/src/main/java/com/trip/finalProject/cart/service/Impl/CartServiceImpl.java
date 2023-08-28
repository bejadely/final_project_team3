package com.trip.finalProject.cart.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.cart.mapper.CartMapper;
import com.trip.finalProject.cart.service.CartService;
import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.PagingVO;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartMapper cartMapper;

	@Override
	public int postIdCount(String postId) {
		return cartMapper.getPostIdCount(postId);
	}

	@Override
	public List<CartVO> getAjaxCart(CartVO cartVO, PagingVO pagingVO) {
		return cartMapper.ajaxCart(cartVO, pagingVO);
	}

	@Override
	public int insertCartInfo(CartVO cartVO) {
		return cartMapper.insertCartInfo(cartVO);
	}

	@Override
	public int deleteCartInfo(String postId) {
		return cartMapper.deleteCartInfo(postId);
	}

}
