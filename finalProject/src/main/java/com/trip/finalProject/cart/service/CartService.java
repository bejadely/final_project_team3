package com.trip.finalProject.cart.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;

public interface CartService {
	
	//부분 데이터 가져오기
	public int postIdCount(String postId);
	//버튼조회
	public List<CartVO> getAjaxCart(CartVO cartVO, PagingVO pagingVO);
	//등록
	public String insertCartInfo(CartVO cartVO);
	//삭제
	public int deleteCartInfo(String postId);
	//수량 업데이트
	public Map<String, Object> getQuanUpdate(CartVO cartVO);
	

}
