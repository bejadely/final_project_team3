package com.trip.finalProject.cart.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.cart.service.CartService;
import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.trip.service.TripVO;
//재운 장바구니 시스템
@Controller
@RequestMapping("/")
public class CartController {
	
	
	@Autowired
	CartService cartService;

	//전체조회
	@GetMapping("cartList")
	public String cartList(Model model){
	
		
		return "myPage/cart/cartList";
	};
	
	
	@PostMapping("ajaxCartList")
	@ResponseBody
	public Map<String, Object> ajaxCart(CartVO cartVO
			  ,@RequestParam(value="cntPerPage", defaultValue="10") Integer cntPerPage
			  ,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			  ) {
		
		//처리중
		int total = cartService.postIdCount(cartVO.getPostId());
		
		
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<CartVO> cartList = cartService.getAjaxCart(cartVO, pagingVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("paging", pagingVO);
		map.put("cartList", cartList);

	    
		return map;
	}
	
	//삭제
	@GetMapping("cartDelete")
	@ResponseBody
	public Map<String, Object> cartDelete(String postId) {

		System.out.println("postId : " + postId);
		int r = cartService.deleteCartInfo(postId);
		return Collections.singletonMap("result", r==1?true:false);
	};
	
	@PostMapping("/updateQuantity")
	@ResponseBody
	public Map<String, Object> quanUpdate(CartVO cartVO){
		cartVO.getPostId();
		cartVO.getQuantity();
	    Map<String, Object> map = cartService.getQuanUpdate(cartVO);
	    
	    return map;
	}
	@PostMapping("/cartInsert")
	@ResponseBody
	public String cartInsert(CartVO cartVO) {
		
		return cartService.insertCartInfo(cartVO);
	}
	
}
