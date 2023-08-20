package com.trip.finalProject.sms;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor 
@Service
public class SmsService {

	
	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;
	
	@Value("${naver-cloud-sms.secretKey}")
	private String secretKey;
	
	@Value("${naver-cloud-sms.serviceId}")
	private String serviceId;
 
	@Value("${naver-cloud-sms.senderPhone}")
	private String phone;
	
    private static int randomNumber;

    public static void createNumber(){
    	randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }
	
	//네이버 클라우드 플랫폼의 SMS 서비스에서 요청의 보안을 위해 서명을 생성하는 기능을 담당하는 메서드. 요청의 안전성과 무결성을 보장
	
	public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;
 
        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();
 
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
 
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
 
        return encodeBase64String;
        
	}
        
   public SmsResponseDTO sendSms(MessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
	   		createNumber(); 
	   
	   		Long time = System.currentTimeMillis();
    		
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.APPLICATION_JSON);
    		headers.set("x-ncp-apigw-timestamp", time.toString());
    		headers.set("x-ncp-iam-access-key", accessKey);
    											//위에서 정의한 메서드 실행. message 를 header에 설정했음
    		headers.set("x-ncp-apigw-signature-v2", makeSignature(time));
    		
    		//난수 생성
			/*
			 * int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);//난수 생성
			 */    		
    		
    		List<MessageDTO> messages = new ArrayList<>();
    		//messages라는 리스트에 messageDto라는 객체를 추가. -> ex)한 번의 요청으로 여러 개의 메시지를 보낼 수 있음
    		messages.add(messageDto);
    		
    		SmsRequestDTO request = SmsRequestDTO.builder()
    				.type("SMS")
    				.contentType("COMM")
    				.countryCode("82")
    				.from(phone)
					//messageDto 파라미터로부터 메시지의 내용을 추출하여 SMS 요청 객체인 SmsRequestDTO에 설정
    				.content( "[TEST] 인증번호는" + randomNumber + "입니다.")
    				.messages(messages)
    				.build();
			/*
			 * System.out.println(randomNumber); request.getRandomNumber();
			 */
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		String body = objectMapper.writeValueAsString(request);
    		HttpEntity<String> httpBody = new HttpEntity<>(body, headers);  		
    		RestTemplate restTemplate = new RestTemplate();
    	    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    	    SmsResponseDTO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDTO.class);
     
    	    return response;	
    	}
   public int getRandomNumber() {
       return randomNumber;
   }

	}
