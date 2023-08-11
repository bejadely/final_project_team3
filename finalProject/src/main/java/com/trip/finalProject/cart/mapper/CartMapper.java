package com.trip.finalProject.cart.mapper;

import java.util.List;

import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.PagingVO;

public interface CartMapper {
	
	public int getTotalCount();
	
	//전체조회
	public List<CartVO> selectAllCart(PagingVO pagingVO);
	//등록
	public int insertCartInfo(CartVO cartVO);
	
	//삭제
	public int deleteCartInfo(int cartId);

}
