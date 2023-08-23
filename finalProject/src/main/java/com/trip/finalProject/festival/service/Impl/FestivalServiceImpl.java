package com.trip.finalProject.festival.service.Impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trip.finalProject.festival.mapper.FestivalMapper;
import com.trip.finalProject.festival.service.FestivalInfoVO;
import com.trip.finalProject.festival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FestivalServiceImpl implements FestivalService {

	@Autowired
	FestivalMapper festivalMapper;

	
	@Value("${tourInfoApi.auth.key}")
    private String API_KEY;

	private final int MANY_NUM_OF_ROWS = 999;
	
	//각 지역별 축제정보를 api요청 후 정보를 가져와서 map에 넣어줌
	@Override
	public void getFestivalInfoAndSave() throws Exception {

		List<FestivalInfoVO> allLocationFestivalInfoList = new ArrayList<>();

		//	각 8개 지역별로 축제 정보를 가져오되, API 실패의 경우를 위해 재귀적으로 API 요청을 보냄
		String[] locationArray = {"daegu", "busan", "ulsan", "andong", "gyeongju", "pohang", "geoje", "tongyeong"};
		for(int i = 0; i < locationArray.length; i++) {
			List<FestivalInfoVO> tempInfoList = new ArrayList<>();

			//	getFestivalDataRecursive() : 재귀 API 요청이 담긴 메서드
			tempInfoList = getFestivalDataRecursive(locationArray[i]);

			//	현재 날짜를 기준으로 축제 데이터를 받아오므로 축제 정보가 없는 지역이 있을 수도 있어서 축제 정보가 1개 이상일 때만 insert 하기 위한 List에 addAll 해줌
			if(tempInfoList.size() > 0) {
				allLocationFestivalInfoList.addAll(tempInfoList);
			}
		}

		festivalMapper.getFestivalInfoAndSave(allLocationFestivalInfoList);
	}

	//	기존에 있던 지역별로 나뉘어져 있던 축제 정보 획득 api 요청 함수를 1개의 메서드로 통합
	private List<FestivalInfoVO> getFestivalInfo(String location) {
		int AREA_CODE = 0;
		int SIGUNGU_CODE = 0;

		switch(location) {
			case "daegu":
				AREA_CODE = 4;
				break;
			case "busan":
				AREA_CODE = 6;
				break;
			case "ulsan":
				AREA_CODE = 7;
				break;
			case "andong":
				AREA_CODE = 35;
				SIGUNGU_CODE = 11;
				break;
			case "gyeongju":
				AREA_CODE = 35;
				SIGUNGU_CODE = 2;
				break;
			case "pohang":
				AREA_CODE = 35;
				SIGUNGU_CODE = 23;
				break;
			case "geoje":
				AREA_CODE = 36;
				SIGUNGU_CODE = 1;
				break;
			case "tongyeong":
				AREA_CODE = 36;
				SIGUNGU_CODE = 17;
				break;
		}

		//	정보가 상황에 따라 0개, 여러개, null로 담겨져 옴
		List<FestivalInfoVO> festivalInfoVOList = festivalInfoApi(AREA_CODE, SIGUNGU_CODE);

		return festivalInfoVOList;
	}
	
	private List<FestivalInfoVO> festivalInfoApi(int areaCode, int sigunguCode) {
		//오늘 날짜
		Date now = new Date(); //현재시간 부르기
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); //시간 포맷팅하기
		String fomattedNow = formatter.format(now); //현재시간에 포맷팅 적용
		
		 StringBuilder stringBuilder = new StringBuilder();
		 stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/searchFestival1");
		 stringBuilder.append("?serviceKey=" + API_KEY);
		 stringBuilder.append("&MobileApp=" + "AppTest");
		 stringBuilder.append("&MobileOS=" + "ETC");
		 stringBuilder.append("&_type=" + "json");
		 stringBuilder.append("&eventStartDate=" + fomattedNow);
		 stringBuilder.append("&areaCode=" + areaCode);
		 stringBuilder.append("&numOfRows=" + MANY_NUM_OF_ROWS);
		 if(sigunguCode!=0) {
			 stringBuilder.append("&sigunguCode=" + sigunguCode);
		 }
		
		 String apiUrl = stringBuilder.toString();
		 
		 try {
			 URL url = new URL(apiUrl);
			 HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			 httpURLConnection.setRequestMethod("GET");
			 BufferedReader bufferedReader;
			 bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			 String inputLine;
			 stringBuilder.setLength(0);
			 while ((inputLine = bufferedReader.readLine()) != null) {
	                stringBuilder.append(inputLine);
	            }
			 bufferedReader.close();
			 
			 String jsonString = stringBuilder.toString();
			 System.out.println("jsonString = " + jsonString);
			 
			 Gson gson = new Gson();
			 
			 JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

			 List<FestivalInfoVO> itemList = new ArrayList<>();

			 JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
			 String totalCount = responseBody.get("totalCount").getAsString();

			 //	totalCount가 0일 때는 실제로 축제가 없다는 뜻이므로 빈 itemList를 그대로 return 해줌
			 if(totalCount.equals("0")) {
				 return itemList;
			 }

			 JsonArray itemArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");

			 for(JsonElement itemElement : itemArray) {
				 JsonObject itemObject = itemElement.getAsJsonObject();
				 String festivalName = itemObject.get("title").getAsString();
				 String startDate = itemObject.get("eventstartdate").getAsString();
				 String endDate = itemObject.get("eventenddate").getAsString();
				 String festivalImg = itemObject.get("firstimage").getAsString();
				 String contentId = itemObject.get("contentid").getAsString();
				 String areaCodeData = itemObject.get("areacode").getAsString();
				 String sigunguCodeData = itemObject.get("sigungucode").getAsString();
				 String festivalId = "FES" + contentId;

				 FestivalInfoVO festivalInfoVO = new FestivalInfoVO();
				 festivalInfoVO.setFestivalName(festivalName);
				 festivalInfoVO.setStartDate(startDate);
				 festivalInfoVO.setEndDate(endDate);
				 festivalInfoVO.setFestivalImg(festivalImg);
				 festivalInfoVO.setContentId(contentId);
				 festivalInfoVO.setAreaCode(areaCodeData);
				 festivalInfoVO.setSigunguCode(sigunguCodeData);
				 festivalInfoVO.setFestivalId(festivalId);

				 itemList.add(festivalInfoVO);
			 }
			 			 
			 return itemList;
		 }catch(Exception e){
			 e.printStackTrace();
			 
			 return null;
		 }
		
	}

	// API 실패의 경우를 위해 재귀적으로 API 요청을 보냄
	private List<FestivalInfoVO> getFestivalDataRecursive(String location) {
		List<FestivalInfoVO> festivalInfoVOList = new ArrayList<>();

		boolean isRequestSucceed = false;	//	재귀 반복문 flag : API 요청 성공 시 flag
		int recursiveCount = 3;				//	재귀 반복문 flag : API 요청 실패 시 flag
		while(!isRequestSucceed) {
			List<FestivalInfoVO> tempList = new ArrayList<>();

			tempList = getFestivalInfo(location);


			if(tempList.size() >= 0) {		//	tempList가 0보다 크거나 같으면 api 요청이 정상적이었다는 뜻
				if(tempList.size() > 0) {
					System.out.println("tempList = " + tempList);
					festivalInfoVOList.addAll(tempList);
				}
				isRequestSucceed = true;
			} else {						//	tempList가 0보다 크거나 같지 않다면 null이라는 뜻이고 api 요청이 정상적이지 않았다는 뜻이기에 무한 반복 되지 않도록 최대 3번까지만 다시 API를 요청
				recursiveCount--;
				if(recursiveCount <= 0) {
					isRequestSucceed = true;
				}
			}
		}

		return festivalInfoVOList;
	}

	//페이지 내 캘린더에 정보 조회
	@Override
	public List<FestivalInfoVO> getFestivalCalendarInfo() {
		
		return festivalMapper.getFestivalCalendarInfo();
	}

}
