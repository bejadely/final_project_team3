package com.trip.finalProject.sms;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
 
@Controller
@RequiredArgsConstructor
public class SmsController {
	
	private final SmsService smsService;
	
		
	   @GetMapping("/send1")  
	   public String getSmsPage() { 
		   
		   return"/sms/sendSms"; 
	   }
	
	   //주소로 POST 요청
	@PostMapping("/sms/send")
	@ResponseBody	
		public HashMap<String, Object> sendSms(MessageDTO messageDto, Model model) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {		
		HashMap<String,Object> smsMap = new HashMap<String,Object>();		
		SmsResponseDTO response = smsService.sendSms(messageDto);

		int num = smsService.getRandomNumber();
		model.addAttribute("num", num); 
		System.out.println(num);
		model.addAttribute("response", response);
		smsMap.put("num", num);
		smsMap.put("reponse", response);
	
		return smsMap;
	}
 
	
}