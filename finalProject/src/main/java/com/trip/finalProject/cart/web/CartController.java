package com.trip.finalProject.cart.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.cart.service.CartService;
import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.ButtonVO;
import com.trip.finalProject.common.PagingVO;
//재운 장바구니 시스템
@Controller
@RequestMapping("/")
public class CartController {
	
	Map<String, String> map = new HashMap<String, String>();
	
	@Autowired
	CartService cartService;

	//전체조회
	@GetMapping("cartList")
	public String cartList(Model model
			  ,@RequestParam(value="nowPage", defaultValue="1") Integer nowPage
			  ,@RequestParam(value="cntPerPage", defaultValue="10") Integer cntPerPage) {
		int total = cartService.cartCount();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		List<CartVO> cartList = cartService.getCartAll(pagingVO);
		System.out.println("kkk"+cartList);
		
		model.addAttribute("cartList", cartList);
		model.addAttribute("paging", pagingVO);
		
		return "cart/cartList";
	};
	
	/*
	 * @GetMapping("ajaxCartList")
	 * 
	 * @ResponseBody public List<CartVO> ajaxCart(@RequestParam(name = "postId")
	 * String postId) { System.out.println(postId);
	 * 
	 * CartVO cartVO = new CartVO();
	 * 
	 * cartVO.setPostId(postId); List<CartVO> list =
	 * cartService.getAjaxCart(cartVO); return list; };
	 */
	
	@GetMapping("ajaxCartList")
	@ResponseBody
	public List<CartVO> ajaxCart(CartVO cartVO, ButtonVO buttonVO) {
		
		System.out.println(cartVO.getPostId());
		System.out.println(buttonVO.getCntPerPage());
		System.out.println(buttonVO.getNowPage());
		int total = cartService.postIdCount(cartVO.getPostId());
		
		ButtonVO calButtonVO = new ButtonVO(total, buttonVO.getNowPage(), buttonVO.getCntPerPage(), cartVO.getPostId());
		
//		System.out.println(map.get("postId"));
		
		//	    System.out.println("asd"+postId);
//
	    
//	    ButtonVO buttonVO = new ButtonVO(total, nowPage, cntPerPage, postId);
//
//	    CartVO cartVO = new CartVO();
//	    cartVO.setPostId(postId);

		List<CartVO> list = cartService.getAjaxCart(calButtonVO);
		System.out.println("lll"+list);
	    return list;
	}
	
	
	
	//삭제
	@GetMapping("cartDelete")
	@ResponseBody
	public Map<String, Object> cartDelete(int cartId) {
		int r = cartService.deleteCartInfo(cartId);
		return Collections.singletonMap("result", r==1?true:false);
	};
	
}
