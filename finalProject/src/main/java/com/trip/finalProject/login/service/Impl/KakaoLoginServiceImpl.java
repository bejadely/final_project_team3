package com.trip.finalProject.login.service.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

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
	
	

   public String getAccessToken (String authorize_code) {
      String access_Token = "";
      String refresh_Token = "";
      String reqURL = "https://kauth.kakao.com/oauth/token";

      try {
         URL url = new URL(reqURL);
            
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            
         conn.setRequestMethod("POST");
         conn.setDoOutput(true);
         // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
         StringBuilder sb = new StringBuilder();
         sb.append("grant_type=authorization_code");
            
         sb.append("&client_id=7a2faaf15ad43d8157f12b22e12d694d"); //본인이 발급받은 key
         sb.append("&redirect_uri=http://localhost/member/kakaologin"); // 본인이 설정한 주소
            
         sb.append("&code=" + authorize_code);
         bw.write(sb.toString());
         bw.flush();
            
         // 결과 코드가 200이라면 성공
         int responseCode = conn.getResponseCode();
         System.out.println("responseCode : " + responseCode);
            
         // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = "";
         String result = "";
            
         while ((line = br.readLine()) != null) {
            result += line;
         }
         System.out.println("response body1'토큰' : " + result);
            
         // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
         //JsonParser parser = new JsonParser();
         //JsonElement element = parser.parse(result);
   

         // Gson 라이브러리의 JsonParser와 JsonElement 대신 Jackson 라이브러리의 ObjectMapper와 JsonNode를 사용
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode jsonNode = objectMapper.readTree(result);
            
         access_Token = jsonNode.get("access_token").asText();
         refresh_Token = jsonNode.get("refresh_token").asText();
            
         System.out.println("access_token : " + access_Token);
         System.out.println("refresh_token : " + refresh_Token);
            
         br.close();
         bw.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return access_Token;
   }
   
   
   
	/*
	 * "id": 사용자를 고유하게 식별하는 카카오톡 사용자 ID가 포함됩니다. "properties": 사용자의 프로필 관련 정보가 들어
	 * 있습니다. 예를 들어, 닉네임, 프로필 이미지 URL, 썸네일 이미지 URL 등이 있습니다. "kakao_account": 사용자의 카카오
	 * 계정 정보가 들어 있습니다. 예를 들어, 이메일 정보가 있습니다.
	 */
   
   //public HashMap<String, Object> getUserInfo(String access_Token) 
   public MemberVO getUserInfo(String access_Token) {

      HashMap<String, Object>userInfo = new HashMap<String, Object>();
      //주어진 엑세스 토큰을 사용해서 카카오톡api의 엔드포인트를 호출하여 사용자 정보를 가져온다.
      //가져온 정보는 JSON형식으로 반환되며 Jackson라이브러리를 사용하여 JSON을 파싱하여 원하는 데이터를 추출한다.
      //추출한 사용자 정보를 userInfo라는 HashMap객체에 담는다. 이 정보를 MemberVO객체에 저장하고 이 객체를 반환한다.
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
         
			/*
			 * String birthday = properties.get("birthday").asText(); String gender =
			 * properties.get("gender").asText();
			 */
         
         Long id = jsonNode.get("id").asLong();
        // userInfo.put("id", id);
         userInfo.put("nickname", nickname);
         userInfo.put("email", email);
         userInfo.put("thumbnailImage", thumbnailImage);
         userInfo.put("profileImage", profileImage);
			/* userInfo.put("birthday", birthday); */
         
         
         
         System.out.println("********");
         System.out.println("###id#### : " + id);
         System.out.println("###프로필### : " + profileImage);
         System.out.println("###닉네임### : " + nickname);
			/* System.out.println("###생일### : " + birthday); */
         
         
         userInfo.put("id", id);
//         userInfo.put("thumbnailImage", thumbnailImage);
//         userInfo.put("profileImage", profileImage);
//         
//         System.out.println("thumbnailImage : " + thumbnailImage);
//         System.out.println("profileImage : " + profileImage);
         
       //  userInfo.put("profileImage", profileImage);

      } catch (IOException e) {
         e.printStackTrace();
      }
      
      
      //return userInfo;
   // catch 아래 코드 추가.
   		MemberVO result = km.findKakao(userInfo);
   		
   		// 위 코드는 먼저 정보가 저장되있는지 확인하는 코드.
   		System.out.println("S:" + result);
   		
   		if(result==null) {
   		// result가 null이면 정보가 저장이 안되있는 것.-> 정보를 저장.
   			km.insertKakaoLogin(userInfo);
   			// 위 코드가 정보를 저장하기 위해 매퍼로 보내는 코드임.
   			return km.findKakao(userInfo);
   			// 위 코드는 정보 저장 후 컨트롤러에 정보를 보내는 코드임.
   			//  result를 리턴으로 보내면 null이 리턴되므로 위 코드를 사용.
   		} else {
   			return result;
   			// 정보가 이미 있기 때문에 result를 리턴함. 이미 회원가입이 되어있으므로 S가  null이 아님. insert실행 안함.
   		}
           
   }
}