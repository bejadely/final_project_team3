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
      System.out.println("##Controller#######" + code);
      //접근 토큰을 받아옴 
      String access_Token = ks.getAccessToken(code);
        
       //다시 서비스에 있는 메서드 실행함 그리고 DB에 정보 없으면 insert .xml실행 
      MemberVO userInfo = ks.getUserInfo(access_Token); //getUserInfo 메서드 호출해서 시행함.
     System.out.println("====Controller================================ ");
      System.out.println("###access_Token#### : " + access_Token);
      System.out.println("###nickname#### : " + userInfo.getMemberName());
    System.out.println("###email#### : " + userInfo.getEmail());
    System.out.println("================================ ");

    
    
  //session객체에 담긴 정보를 초기화
    session.invalidate(); 	
    session.setAttribute("sessionId", userInfo.getMemberId());
  	session.setAttribute("kakaoN", userInfo.getMemberName());
  	session.setAttribute("kakaoE", userInfo.getEmail());
  	session.setAttribute("kakaoProfile", userInfo.getSavedProfileImg()); 
  	session.setAttribute("access_Token", userInfo.getAccessToken());
  	System.out.println("프로필사진:" + userInfo.getSavedProfileImg() );
  	
      
      return "redirect:/";
       } 

}