package com.trip.finalProject.login.service.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.finalProject.login.mapper.KakaoLoginMapper;
import com.trip.finalProject.login.service.KakaoLoginService;
import com.trip.finalProject.login.service.MemberVO;


@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {
	
	@Autowired
	KakaoLoginMapper km;
	
	
	//로그인 성공하면 authorize_code 받아옴
   public Map<String, String>  getAccessToken (String authorize_code) {
      String access_Token = "";
      String refresh_Token = "";
      String reqURL = "https://kauth.kakao.com/oauth/token";
      

      try {
    	  //java.net.URL 클래스
         URL url = new URL(reqURL);
            
         
         //주어진 URL에 대한 HttpURLConnection 객체를 생성하고 연결
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();              

         conn.setRequestMethod("POST");
         conn.setDoOutput(true);
         
         //POST 요청. 서버로 데이터를 전송 -> HttpURLConnection의 출력 스트림을 얻어와서-> 문자열로 조작 
         //출력 스트림
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
         StringBuilder sb = new StringBuilder();
         sb.append("grant_type=authorization_code");        
         sb.append("&client_id=7a2faaf15ad43d8157f12b22e12d694d"); //본인이 발급받은 key
         sb.append("&redirect_uri=http://localhost:8787/member/kakaologin"); // 본인이 설정한 주소
            
         sb.append("&code=" + authorize_code);
         bw.write(sb.toString());
         
         //버퍼의 내용을 출력 스트림으로 플러시하여 전송
         bw.flush();
            
         // 요청이 정상 도달-> 200
         int responseCode = conn.getResponseCode();
         System.out.println("responseCode : " + responseCode);
            
         // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = "";
         String result = "";
            
         while ((line = br.readLine()) != null) {
            result += line;
         }
         System.out.println("response body'토큰.' : " + result);
            
 
        
       //JSON파싱 객체 생성
         // Gson 라이브러리의 JsonParser와 JsonElement 대신 Jackson 라이브러리의 ObjectMapper와 JsonNode를 사용
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode jsonNode = objectMapper.readTree(result);
           
         //access 토큰은 일정 기간 동안 유효
         access_Token = jsonNode.get("access_token").asText();
         
         //refresh 토큰은 access 토큰 만료후 자동으로 접근 토큰을 갱신
         refresh_Token = jsonNode.get("refresh_token").asText();
            
         System.out.println("access_token : " + access_Token);
         System.out.println("refresh_token : " + refresh_Token);
            
         
    
         //메모리 누수를 방지
         br.close();
         bw.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      Map<String, String> token = new HashMap<>();
      token.put("access_token", access_Token);
      token.put("refresh_token", refresh_Token);
      return token;
   }
   
   
  
   public MemberVO getUserInfo(String access_Token, String refresh_Token) {

      HashMap<String, Object>userInfo = new HashMap<String, Object>();

      String reqURL = "https://kapi.kakao.com/v2/user/me";
      try {
         URL url = new URL(reqURL);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Authorization", "Bearer " + access_Token);
         int responseCode = conn.getResponseCode();
         System.out.println("responseCode : " + responseCode);
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = "";
         String result = "";
         while ((line = br.readLine()) != null) {
            result += line;
         }
         
         // JSON 데이터를 파싱하고 필요한 정보를 추출. JSON데이터 조작하기 위해 jsonNode 객체화
         System.out.println("response body 회원정보 : " + result);
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode jsonNode = objectMapper.readTree(result);
         JsonNode properties = jsonNode.get("properties");
         JsonNode kakao_account = jsonNode.get("kakao_account");
        

         String nickname = properties.get("nickname").asText();
         String email = kakao_account.get("email").asText();
         String thumbnailImage = properties.get("thumbnail_image").asText();
         String profileImage = properties.get("profile_image").asText();
         //String birthday = properties.get("birthday").asText();
         
	       
         Long id = jsonNode.get("id").asLong();
         userInfo.put("nickname", nickname);
         userInfo.put("email", email);
         userInfo.put("thumbnailImage", thumbnailImage);
         userInfo.put("profileImage", profileImage);
         userInfo.put("profileImage", profileImage);
         userInfo.put("access_Token", access_Token);
         userInfo.put("refresh_Token", refresh_Token);
         userInfo.put("refresh_Token", profileImage);
		/* userInfo.put("birthday", birthday); */
         
         
         
         System.out.println("********");
         System.out.println("###id#### : " + id);
         System.out.println("###프로필### : " + profileImage);
         System.out.println("###닉네임### : " + nickname);
			                
         userInfo.put("id", id);

      } catch (IOException e) {
         e.printStackTrace();
      }
      
      
      //return userInfo;
   // catch 아래 코드 추가. 여기에 토큰도 같이 넣자
   		MemberVO result = km.findKakao(userInfo);
   		
   		// 위 코드는 먼저 정보가 저장되있는지 확인하는 코드.
   		System.out.println("S:" + result);
   		
   		if(result==null) {
   		// result가 null이면 DB에 정보가 없다는 것-> 정보를 저장.
   			km.insertKakaoLogin(userInfo);
   			
   			return km.findKakao(userInfo);
   		
   		} else {
   			return result;
   			// 정보가 이미 있기 때문에 result를 리턴함. 이미 회원가입이 되어있으므로 S가  null이 아님. insert실행 안함.
   		}
           
   }
}