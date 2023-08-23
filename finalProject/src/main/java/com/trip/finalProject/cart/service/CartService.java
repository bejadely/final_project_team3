package com.trip.finalProject.cart.service;

import java.util.List;

import com.trip.finalProject.common.ButtonVO;
import com.trip.finalProject.common.PagingVO;

public interface CartService {
	
	public int cartCount();
	//부분 데이터 가져오기
	public int postIdCount(String postId);
	//전체조회
	public List<CartVO> getCartAll(PagingVO pagingVO);
	//버튼조회
	public List<CartVO> getAjaxCart(ButtonVO buttonVO);
	//등록
	public int insertCartInfo(CartVO cartVO);
	//삭제
	public int deleteCartInfo(int cartId);
	//

}
