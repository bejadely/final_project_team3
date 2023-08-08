package com.trip.finalProject.naverlogin.web;

import java.io.IOException;


import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.trip.finalProject.member.service.MemberVO;
import com.trip.finalProject.naverlogin.mapper.NaverLoginMapper;
import com.trip.finalProject.naverlogin.service.NaverLoginVO;
 
/**
 * Handles requests for the application home page.
 */
@Controller
public class NaverLoginController {
 
	/* NaverLoginVO */
	private NaverLoginVO naverLoginVO;
	private String apiResult = null;
	
	@Autowired
	NaverLoginMapper nm;
	
	/*
	 * @Autowired private void setNaverLoginVO(NaverLoginVO naverLoginVO) {
	 * this.naverLoginVO = naverLoginVO; }
	 */
 
	//로그인 첫 화면 요청 메소드
	@RequestMapping(value = "/naverlogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session) {
		
		
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginVO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginVO.getAuthorizationUrl(session);
		
		//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		//redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		System.out.println("네이버:" + naverAuthUrl);
		
		//네이버 
		model.addAttribute("url", naverAuthUrl);
 
		return "naverlogin";  //여기를 home으로 하면 System.out.println("네이버:" + naverAuthUrl);이거 콘솔에 띄우고 home화면은 보이면서 url은 http://localhost/web/naverlogin
	}
 
	//네이버 로그인 성공시 callback호출 메소드 -> 왜??
	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException {
		
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginVO.getAccessToken(session, code, state);
 
        //1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginVO.getUserProfile(oauthToken);  //String형식의 json데이터
		
		/** apiResult json 구조
		{"resultcode":"00",
		 "message":"success",
		 "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
		**/
		
		//2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		
		//3. 데이터 파싱 
		//Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject)jsonObj.get("response");
		//response의 nickname값 파싱
		String nickname = (String)response_obj.get("nickname");
		String id = (String)response_obj.get("id");
		System.out.println(nickname);
		System.out.println(id);
		
	
		JSONObject responseMap = response_obj;
        
        MemberVO result = nm.findNaver(responseMap);
        System.out.println("S:" + result);

        if (result == null) {
            nm.insertNaverLogin(responseMap);
            result = nm.findNaver(responseMap);
        }
           
		
		
		//4.파싱 닉네임 세션으로 저장
		session.setAttribute("sessionId",nickname); //세션 생성
		session.setAttribute("id",id);
		model.addAttribute("result", apiResult);
	
		return "naverlogin";  //naverlogin -> 로그아웃 가능 화면 나옴 or home
	}
	
	
	
	//로그아웃
	@RequestMapping(value = "/naverlogout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session)throws IOException {
			System.out.println("여기는 logout");
			session.invalidate();
 
	        
			return "home";// "redirect:index.jsp";
		}
		
	//art shift a 하면 같은 열 삭제 가능
	
	// 네이버 Login Form에서 하단의 '홈' 클릭했을 때 세션은 그대로인 상태로 홈으로 가게됨
		@RequestMapping(value = "/home", method = { RequestMethod.GET,
		RequestMethod.POST }) public String gotohome(HttpSession session)throws IOException { 
		System.out.println("고투홈!");
		return "home";// "redirect:index.jsp"; 
		}
		
	
}