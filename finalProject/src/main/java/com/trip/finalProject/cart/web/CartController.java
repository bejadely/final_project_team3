package com.trip.finalProject.cart.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.cart.service.CartService;
import com.trip.finalProject.cart.service.CartVO;
import com.trip.finalProject.common.PagingVO;

@Controller
@RequestMapping("/")
public class CartController {
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
		
		model.addAttribute("cartList", cartList);
		model.addAttribute("paging", pagingVO);
		
		return "cart/cartList";
	}
	
	//삭제
	@GetMapping("cartDelete")
	public String cartDelete(int cartId) {
		cartService.deleteCartInfo(cartId);
		return"redirect:/cartList";
	}
	
}
