package com.trip.finalProject.cart.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.cart.mapper.CartMapper;
import com.trip.finalProject.cart.service.CartService;
import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.ButtonVO;
import com.trip.finalProject.common.PagingVO;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartMapper cartMapper;

	@Override
	public int cartCount() {
		return cartMapper.getTotalCount();
	}
	

	@Override
	public int postIdCount(String postId) {
		return cartMapper.getPostIdCount(postId);
	}


	@Override
	public List<CartVO> getCartAll(PagingVO pagingVO) {
		return cartMapper.selectAllCart(pagingVO);
	}

	@Override
	public List<CartVO> getAjaxCart(ButtonVO buttonVO) {
		return cartMapper.ajaxCart(buttonVO);
	}

	@Override
	public int insertCartInfo(CartVO cartVO) {
		return cartMapper.insertCartInfo(cartVO);
	}

	@Override
	public int deleteCartInfo(int cartId) {
		return cartMapper.deleteCartInfo(cartId);
	}

}
