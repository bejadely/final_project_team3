package com.trip.finalProject.sms;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
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
	 
	@PostMapping("/sms/send")
	@ResponseBody	
		public String sendSms(MessageDTO messageDto, Model model,HttpServletRequest request) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		// System.out.println(messageDto.to);
		SmsResponseDTO response = smsService.sendSms(messageDto);
		//System.out.println(SmsRequestDTO.builder().randomNumber(getSmsPage()));
		int num = smsService.getRandomNumber();
	
		/* model.addAttribute("num", num); */
		System.out.println(num);
		model.addAttribute("response", response);
		
		
		/*
		 * HttpSession session = request.getSession(); session.invalidate();
		 * session.setAttribute("num", num);
		 */
		 
		 
		
		

		
		return "Success";
	}
 
	
}