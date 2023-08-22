package com.trip.finalProject.login.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.login.service.KakaoLoginService;
import com.trip.finalProject.login.service.MemberVO;



@Controller
//@RequestMapping(value="/member")
public class KakaoLoginController {

   @Autowired
   private KakaoLoginService ks;
   
   @Autowired
   private HttpSession session;
   
//사용자가 로그인을 성공적으로 수행한 후에 리디렉션될 URL
   @GetMapping("/member/kakaologin") 
   public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
      System.out.println("#########" + code);
      //접근 토큰
      String access_Token = ks.getAccessToken(code);
        
         
      MemberVO userInfo = ks.getUserInfo(access_Token); //getUserInfo 메서드 호출해서 시행함.
     System.out.println("================================ ");
      System.out.println("###access_Token#### : " + access_Token);
      System.out.println("###nickname#### : " + userInfo.getMemberName());
    System.out.println("###email#### : " + userInfo.getEmail());
    System.out.println("================================ ");

    
    
  //session객체에 담긴 정보를 초기화
    session.invalidate(); 	
    
  	session.setAttribute("kakaoN", userInfo.getMemberName());
  	session.setAttribute("kakaoE", userInfo.getEmail());
  	session.setAttribute("kakaoProfile", userInfo.getSavedProfileImg()); 	
  	System.out.println("프로필사진:" + userInfo.getSavedProfileImg() );
  	
      
      return "redirect:/";
       } 

}