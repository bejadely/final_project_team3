package com.trip.finalProject.festival.service.Impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trip.finalProject.festival.service.FestivalInfoVO;
import com.trip.finalProject.festival.service.FestivalService;

@Service
public class FestivalServiceImpl implements FestivalService {

	
	@Value("${tourInfoApi.auth.key}")
    private String API_KEY;
	
	//areaCode까지 인 지역들은 sigunguCode는 없음
	private final int SIGUNGU_CODE_ZERO = 0; 
	
	//오늘 날짜
	Date now = new Date(); //현재시간 부르기
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); //시간 포맷팅하기
	String fomatedNow = formatter.format(now); //현재시간에 포맷팅 적용
	
	//각 지역별 축제정보를 api요청 후 정보를 가져와서 map에 넣어줌
	@Override
	public Map<String, Object> getfestivalInfoApi() {
		
		Map<String, Object> festivalInfoMap = new HashMap<>();
		
		festivalInfoMap.put("deagu", getDeaguInfo());
		festivalInfoMap.put("busan", getBusanInfo());
		festivalInfoMap.put("ulsan", getUlsanInfo());
		festivalInfoMap.put("andong", getAndongInfo());
		festivalInfoMap.put("gyeonju", getGyeonjuInfo());
		festivalInfoMap.put("pohang", getPohangInfo());
		festivalInfoMap.put("geoje", getGeojeInfo());
		festivalInfoMap.put("tongyeong", getTongyeongInfo());
		
		return festivalInfoMap;
	}
	

	private List<FestivalInfoVO> getDeaguInfo() {
		final int AREA_CODE = 4;
		
		List<FestivalInfoVO> deaguInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE_ZERO);
		
		return deaguInfoList;
	}

	private List<FestivalInfoVO> getBusanInfo() {
		final int AREA_CODE = 6;
		
		List<FestivalInfoVO> busanInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE_ZERO);
		
		return busanInfoList;
	}	

	private List<FestivalInfoVO> getUlsanInfo() {
		final int AREA_CODE = 7;
		
		List<FestivalInfoVO> ulsanInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE_ZERO);
		
		return ulsanInfoList;
	}
	
	private List<FestivalInfoVO> getAndongInfo() {
		final int AREA_CODE = 35;
		final int SIGUNGU_CODE = 11;
		
		List<FestivalInfoVO> andongInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE);
		
		return andongInfoList;
	}

	private List<FestivalInfoVO> getGyeonjuInfo() {
		final int AREA_CODE = 35;
		final int SIGUNGU_CODE = 2;
		
		List<FestivalInfoVO> gyeonjuInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE);
		
		return gyeonjuInfoList;
	}

	private List<FestivalInfoVO> getPohangInfo() {
		final int AREA_CODE = 35;
		final int SIGUNGU_CODE = 23;
		
		List<FestivalInfoVO> pohangInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE);
		
		return pohangInfoList;
	}

	private List<FestivalInfoVO> getGeojeInfo() {
		final int AREA_CODE = 36;
		final int SIGUNGU_CODE = 1;
		
		List<FestivalInfoVO> geojeInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE);
		
		return geojeInfoList;
	}
	
	private List<FestivalInfoVO> getTongyeongInfo() {
		final int AREA_CODE = 36;
		final int SIGUNGU_CODE = 17;
		
		List<FestivalInfoVO> tongyeongInfoList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE);
		
		return tongyeongInfoList;
	}
	
	private List<FestivalInfoVO> festivalInfoApi(int areaCode, int sigunguCode) {
		
		 StringBuilder stringBuilder = new StringBuilder();
		 stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/searchFestival1");
		 stringBuilder.append("?serviceKey=" + API_KEY);
		 stringBuilder.append("&MobileApp=" + "AppTest");
		 stringBuilder.append("&MobileOS=" + "ETC");
		 stringBuilder.append("&_type=" + "json");
		 stringBuilder.append("&eventStartDate=" + fomatedNow);
		 stringBuilder.append("&areaCode=" + areaCode);
		 if(sigunguCode!=0) {
			 stringBuilder.append("&sigunguCode=" + sigunguCode);
		 }
		
		 String apiUrl = stringBuilder.toString();
		 System.out.println(apiUrl);
		 
		 try {
			 URL url = new URL(apiUrl);
			 HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
			 httpsURLConnection.setRequestMethod("GET");
			 BufferedReader bufferedReader;
			 bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			 String inputLine;
			 stringBuilder.setLength(0);
			 while ((inputLine = bufferedReader.readLine()) != null) {
	                stringBuilder.append(inputLine);
	            }
			 bufferedReader.close();
			 
			 String jsonString = stringBuilder.toString();
			 
			 Gson gson = new Gson();
			 
			 JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
			 
			 JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
			 JsonArray itemArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");
			 
			 List<FestivalInfoVO> itemList = new ArrayList<>();
			 
			 for(JsonElement itemElement :itemArray) {
				 JsonObject itemObject = itemElement.getAsJsonObject();
				 String festivalName=itemObject.get("title").getAsString();
				 String content=itemObject.get("").getAsString();
				 String festivalUrl;
				 String startDate;
				 String endDate;
				 String festivalImg;
				 String contentId;
			 }
			 			 
			 return null;
		 }catch(Exception e){
			 e.printStackTrace();
			 
			 return null;
		 }
		
	}

}
