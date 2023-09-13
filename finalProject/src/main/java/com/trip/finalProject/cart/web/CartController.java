package com.trip.finalProject.cart.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
//재운 장바구니 시스템
@Controller
@RequestMapping("/")
public class CartController {
	
	
	@Autowired
	CartService cartService;
	@Autowired
	HttpSession session;

	//전체조회
	@GetMapping("/common/cartList")
	public String cartList(Model model){
	
		
		return "myPage/cart/cartList";
	};
	
	
	@PostMapping("/common/ajaxCartList")
	@ResponseBody
	public Map<String, Object> ajaxCart(CartVO cartVO
			  ,@RequestParam(value="cntPerPage", defaultValue="10") Integer cntPerPage
			  ,@RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage
			  ) {
		cartVO.setMemberId(session.getAttribute("sessionId").toString());
		//처리중
		int total = cartService.postIdCount(cartVO);
		
		
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<CartVO> cartList = cartService.getAjaxCart(cartVO, pagingVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("cartcart:" + cartList);
		System.out.println();
		
		map.put("paging", pagingVO);
		map.put("cartList", cartList);

	    
		return map;
	}
	
	//삭제
	@GetMapping("/common/cartDelete")
	@ResponseBody
	public Map<String, Object> cartDelete(String cartId) {
		int r = cartService.deleteCartInfo(cartId);
		return Collections.singletonMap("result", r==1?true:false);
	};
	
	@PostMapping("/common/updateQuantity")
	@ResponseBody
	public Map<String, Object> quanUpdate(CartVO cartVO){
		cartVO.getPostId();
		cartVO.getQuantity();
	    Map<String, Object> map = cartService.getQuanUpdate(cartVO);
	    
	    return map;
	}
	@PostMapping("/common/cartInsert")
	@ResponseBody
	public String cartInsert(CartVO cartVO) throws Exception {
		String sessionId = "";
		if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().replaceAll(" ", "").equals("")) {
			sessionId =  session.getAttribute("sessionId").toString();
        } else {
            throw new Exception("no login");
        }	
		return cartService.insertCartInfo(cartVO);
	}
	
}
