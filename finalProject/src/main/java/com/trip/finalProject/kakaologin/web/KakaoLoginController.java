package com.trip.finalProject.kakaologin.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.finalProject.kakaologin.service.KakaoLoginService;
import com.trip.finalProject.member.service.MemberVO;



@Controller
//@RequestMapping(value="/member")
public class KakaoLoginController {

   @Autowired
   private KakaoLoginService ks;
   
   @Autowired
   private HttpSession session;
   
   //@RequestMapping(value="/kakaologin", method=RequestMethod.GET)
   @GetMapping("/member/kakaologin") 
   public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
      System.out.println("#########" + code);
      String access_Token = ks.getAccessToken(code);
        
      
      
      // 위에서 만든 코드 아래에 코드 추가
      //KakaoLoginServiceImpl에 있는 메서드의 sysout먼저 실행하고 컨트롤러 안에있는 sysout실행한다
      MemberVO userInfo = ks.getUserInfo(access_Token); //getUserInfo 메서드 호출해서 시행함.
     System.out.println("================================ ");
      System.out.println("###access_Token#### : " + access_Token);
      System.out.println("###nickname#### : " + userInfo.getMemberName());
    System.out.println("###email#### : " + userInfo.getMemberEmail());
    System.out.println("================================ ");
//      System.out.println("###nickname#### : " + userInfo.get("nickname"));
//      System.out.println("###email#### : " + userInfo.get("email"));
    
    session.invalidate();
  	// 위 코드는 session객체에 담긴 정보를 초기화 하는 코드.
  	session.setAttribute("kakaoN", userInfo.getMemberName());
  	session.setAttribute("kakaoE", userInfo.getMemberEmail());
  	// 위 2개의 코드는 닉네임과 이메일을 session객체에 담는 코드
  	// jsp에서 ${sessionScope.kakaoN} 이런 형식으로 사용할 수 있다.
  	session.setAttribute("kakaoProfile", userInfo.getProfileImage());
  	
	
  	System.out.println("프로필사진:" + userInfo.getProfileImage() );
  	
      
//      return "member/testPage";
      return "/index";
       } 

}