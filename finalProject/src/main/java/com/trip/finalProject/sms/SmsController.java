package com.trip.finalProject.sms;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
 
@Controller
@RequiredArgsConstructor
public class SmsController {
	
	private final SmsService smsService;
	
	@GetMapping("/send")
	public String getSmsPage() {
		return "sms/sendSms";
	}
	
	@PostMapping("/sms/send")
	public String sendSms(MessageDTO messageDto, Model model) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		SmsResponseDTO response = smsService.sendSms(messageDto);
		model.addAttribute("response", response);
		return "sms/result";
	}
 
	
}
