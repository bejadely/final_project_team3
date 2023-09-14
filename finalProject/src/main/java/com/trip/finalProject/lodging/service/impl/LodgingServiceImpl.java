package com.trip.finalProject.lodging.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.lodging.mapper.LodgingMapper;
import com.trip.finalProject.lodging.service.LodgingService;
import com.trip.finalProject.lodging.service.LodgingVO;
import com.trip.finalProject.tourInfo.service.SpotDetailReviewVO;

@Service
public class LodgingServiceImpl implements LodgingService {
	 @Autowired
	 LodgingMapper lodgingMapper;	 
	 
	 @Value("${lodgingInfoApi.auth.key}")
	 private String API_KEY;
	 
	 @Override
	 public int insertLodgingInfo(LodgingVO lodginVO) {
		 return lodgingMapper.insertLodging(lodginVO);
	 }
	 
	 @Override
	 public List<LodgingVO> getLodgingList(PagingVO pagingVO){
		 return lodgingMapper.listLodging(pagingVO);
	 }
	 
	 @Override
	 public int lodgingCount() {
		// TODO Auto-generated method stub
		return lodgingMapper.lodgingCount();
	 }
	 
	 @Override
	 public int lodgingCountTitle(String keyword) {
		// TODO Auto-generated method stub
		return lodgingMapper.lodgingCountTitle(keyword);
	 }

	 @Override
	 public List<LodgingVO> searchPackageByTitle(LodgingVO lodgingVO, PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return lodgingMapper.searchPackageByTitle(lodgingVO,pagingVO);
	 } 
	 
	@Override
	public List<LocationVO> getLocationList() {
		// TODO Auto-generated method stub
		return lodgingMapper.listArea();
	}

	@Override
	public List<LodgingVO> getAreaList(LodgingVO lodgingVO) {
		// TODO Auto-generated method stub
		return lodgingMapper.listAreaLodging(lodgingVO);
	}

	@Override
	public Map<String, Object> getDetailInfoReviewList(String contentid) {
		// TODO Auto-generated method stub
		Map<String, Object> detailInfoReviewMap = new HashMap<>();

        //  장소 정보
        Map<String, String> detailInfoMap = new HashMap<>();
        detailInfoMap.putAll(getCommonApiMap(Integer.parseInt(contentid)));
        detailInfoMap.putAll(getIntroApiMap(Integer.parseInt(contentid)));
        detailInfoReviewMap.put("detailInfoMap", detailInfoMap);

        return detailInfoReviewMap;
    }

	 //공통 정보 API
    private Map<String, String> getCommonApiMap(int contentid) {
        Map<String, String> commonApiMap = getCommonApiData(contentid);

        return commonApiMap;
    }

    //소개 정보 API
    private Map<String, String> getIntroApiMap(int contentid) {
        Map<String, String> introApiMap = getIntroApiData(contentid);

        return introApiMap;
    }

    //공통 정보 API
    private Map<String, String> getCommonApiData(int contentid) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://apis.data.go.kr/B551011/KorWithService1/detailCommon1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&contentId=" + contentid);
        stringBuilder.append("&defaultYN=" + "Y");
        stringBuilder.append("&_type=" + "json");

        String apiUrl = stringBuilder.toString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();

            // Gson 객체 생성
            Gson gson = new Gson();

            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            // 필요한 데이터 추출
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonArray itemsArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");

            //  각 데이터를 담을 맵 생성
            Map<String, String> itemMap = new HashMap<>();

            // 아이템별로 데이터 추출
            JsonObject itemObject = itemsArray.get(0).getAsJsonObject();
            String homepage = itemObject.get("homepage").getAsString();

            itemMap.put("homepage", homepage);

            return itemMap;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    //소개 정보 API
    private Map<String, String> getIntroApiData(int contentid) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://apis.data.go.kr/B551011/KorWithService1/detailIntro1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&contentId=" + contentid);
        stringBuilder.append("&contentTypeId=" + 32);
        stringBuilder.append("&_type=" + "json");
        String apiUrl = stringBuilder.toString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();
            // Gson 객체 생성
            Gson gson = new Gson();
            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            // 필요한 데이터 추출
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonArray itemsArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");
            //  각 데이터를 담을 맵 생성
            Map<String, String> itemMap = new HashMap<>();
            // 아이템별로 데이터 추출
            JsonObject itemObject = itemsArray.get(0).getAsJsonObject();
            String subfacility = "";
            String foodplace="";
            foodplace=itemObject.get("foodplace").getAsString();
            subfacility = itemObject.get("subfacility").getAsString();
            itemMap.put("subfacility", subfacility);
            itemMap.put("foodplace", foodplace);

            return itemMap;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

	

	

	

}
