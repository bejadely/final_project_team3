package com.trip.finalProject.login.web;

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
import com.trip.finalProject.login.mapper.NaverLoginMapper;
import com.trip.finalProject.login.service.MemberVO;
import com.trip.finalProject.login.service.NaverLoginVO;
 
@Controller
public class NaverLoginController {
 
	
	@Autowired
	NaverLoginMapper nm;
	
	/* NaverLoginVO */
	private NaverLoginVO naverLoginVO;
	private String apiResult = null;
	
	@Autowired
	private void setNaverLoginVO(NaverLoginVO naverLoginVO) {
		this.naverLoginVO = naverLoginVO;
	}
	
	//로그인 첫 화면 요청 메소드(1)
	@RequestMapping(value ="/member/naverlogin1", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session) {
		
		
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginVO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginVO.getAuthorizationUrl(session);
		
		
		// System.out.println("네이버:" + naverAuthUrl);
		
		//네이버 
		model.addAttribute("url", naverAuthUrl);
		
 
		return "member/naverlogin";  //실제로 보여주는 파일 경로
	}
 
	//네이버 로그인 성공시 callback호출 메소드(2)
	@RequestMapping(value ="/member/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException {
		
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginVO클래스의 getAuthorizationUrl메소드 호출 */
		
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginVO.getAccessToken(session, code, state);
        System.out.println(oauthToken);
        //1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginVO.getUserProfile(oauthToken);  //String형식의 json데이터
		
		String accessToken = oauthToken.getAccessToken();

		// refresh_token 가져오기
		String refreshToken = oauthToken.getRefreshToken();

		System.out.println("access_token: " + accessToken);
		System.out.println("refresh_token: " + refreshToken);
		
		// json 구조
	
		System.out.println("json구조");
		//2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(apiResult);
		
		JSONObject jsonObj = (JSONObject) obj;
		System.out.println("되는건가?");
		//System.out.println(jsonObj);
		
		//3. 데이터 파싱 
		//Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject)jsonObj.get("response");
		//response의 nickname값 파싱
		System.out.println(response_obj);
		
		String gender = (String)response_obj.get("gender");
		String nickname = (String)response_obj.get("nickname");
		String id = (String)response_obj.get("id");
		
		/*
		 * String accessToken= (String)response_obj.get("access_token"); String
		 * refreshToken = (String)response_obj.get("refresh_token");
		 */
		
		System.out.println(nickname);
		System.out.println(id);
		System.out.println(gender);
	
		JSONObject responseMap = response_obj;
        
        MemberVO result = nm.findNaver(responseMap);
        System.out.println("S:" + result);

        if (result == null) {
        	
            nm.insertNaverLogin(responseMap, oauthToken);
            result = nm.findNaver(responseMap);
        }
           
		
		
		//4.파싱 닉네임 세션으로 저장
		session.setAttribute("sessionId",nickname); //세션 생성
		session.setAttribute("id",id);
		model.addAttribute("result", apiResult);
	
		return "redirect:/";  //naverlogin -> 로그아웃 가능 화면 나옴 or home
	}
	
	
	
	//로그아웃
	@RequestMapping(value = "/naverlogout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session)throws IOException {
			System.out.println("여기는 logout");
			session.invalidate();
 
	        
			return "redirect:/";// "redirect:index.jsp";
		}
		
	//art shift a 하면 같은 열 삭제 가능
	
	// 네이버 Login Form에서 하단의 '홈' 클릭했을 때 세션은 그대로인 상태로 홈으로 가게됨
		@RequestMapping(value = "member/naver", method = { RequestMethod.GET,
		RequestMethod.POST }) public String gotohome(HttpSession session)throws IOException { 
		System.out.println("고투홈!");
		return "redirect:/";// "redirect:index.jsp"; 
		}
		
	
}