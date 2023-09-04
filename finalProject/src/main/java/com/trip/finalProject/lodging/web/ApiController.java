package com.trip.finalProject.lodging.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.finalProject.api.ReadJson;
import com.trip.finalProject.lodging.service.LodgingService;
import com.trip.finalProject.lodging.service.LodgingVO;

@RestController
public class ApiController {
	
	@Autowired
	LodgingService lodgingService;
	
	@Value("${lodgingInfoApi.auth.key}")
	private String API_KEY;
	
	@GetMapping("api")
	public String callApi() throws IOException{
		StringBuilder result = null;
		
		int[] areaCode = {4,6,7,35,36};
		
		
		for(int i : areaCode) {
			
			result = new StringBuilder();
			
			String urlStr = "http://apis.data.go.kr/B551011/KorWithService1/areaBasedList1?"+
							"serviceKey="+API_KEY+
							"&numOfRows=1000"+
							"&pageNo=1"+			
							"&MobileOS=ETC"+
							"&MobileApp=AppTest"+
							"&_type=json"+
							"&contentTypeId=32"+
							"&areaCode=" + String.valueOf(i);
	
			URL url = new URL(urlStr);
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			
			BufferedReader br;
			
			Charset charset = Charset.forName("UTF-8");
			
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));
			
			String returnLine;
			
			while((returnLine=br.readLine()) != null) {
				result.append(returnLine+"\n");
			}
			
			urlConnection.disconnect();
			List<LodgingVO> list  = ReadJson.readJson(result.toString());
			
			for(LodgingVO lodgingVO : list) {
				lodgingService.insertLodgingInfo(lodgingVO);
				
			}
		}	
		
		return result.toString();
	}
	
	
}
