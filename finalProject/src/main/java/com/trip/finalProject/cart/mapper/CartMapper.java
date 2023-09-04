package com.trip.finalProject.cart.mapper;

import java.util.List;

import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.PagingVO;

public interface CartMapper {
	
	
	public int getPostIdCount(String postId);
	
	//전체조회
	public List<CartVO> selectAllCart(PagingVO pagingVO);
	
	//버튼조회
	public List<CartVO> ajaxCart(CartVO cartVO, PagingVO pagingVO);
	//등록
	public int insertCartInfo(CartVO cartVO);
	
	//수량 업데이트
	public int quanUpdate(CartVO cartVO);
	
	//삭제
	public int deleteCartInfo(String postId);

}
