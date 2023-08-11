package com.trip.finalProject.cart.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface CartService {
	
	public int cartCount();
	//전체조회
	public List<CartVO> getCartAll(PagingVO pagingVO);
	//등록
	public int insertCartInfo(CartVO cartVO);
	//삭제
	public int deleteCartInfo(int cartId);

}
